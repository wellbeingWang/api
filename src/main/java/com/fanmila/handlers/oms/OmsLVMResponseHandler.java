package com.fanmila.handlers.oms;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Set;

import com.fanmila.model.URLHandlerContext;
import com.fanmila.cms.model.cps.CPSURLHandlerContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.fanmila.handlers.DmpURLHandler;
import com.fanmila.model.ClickHandlerInfo;
import com.fanmila.model.ClickType;
import com.fanmila.model.common.CacheConstant;
import com.fanmila.model.omslvm.LVMChannelFilterModel;
import com.fanmila.model.omslvm.LVMFilterModel;
import com.fanmila.model.omslvm.LVMMerchantFilterModel;
import com.fanmila.util.IPToolUtil;
import com.fanmila.util.SerializeUtil;
import com.fanmila.util.analyzer.URLAnalyzerManager;
import com.fanmila.util.common.UUIDUtils;
import com.fanmila.service.impl.BaseRedisServiceImpl;
/**
 * 处理
 * @author zhenyuanzi
 *
 */
@Component
public class OmsLVMResponseHandler extends DmpURLHandler{
	

	@Autowired
	private BaseRedisServiceImpl baseRedisService;// = ContextUtils.getBean(BaseRedisServiceImpl.class);
	
	@Autowired
	private URLAnalyzerManager urlAnalyzerManager;

	
	@Override
	public String getRedirectURL(URLHandlerContext context, ClickHandlerInfo handlerInfo) {
		

		JSONObject responseJson = new JSONObject();
		try {
			JSONObject lvmInfoJson = getLVMInfo(context);
			if(lvmInfoJson==null||lvmInfoJson.isEmpty()){
				responseJson.put("succ", false);
			}else{
				responseJson.put("succ", true);
				responseJson.put("lvm", lvmInfoJson);
			}
			
		
		} catch (Exception e) {
			context.getHandlerInfo().setSuccess(false);
			e.printStackTrace();
			responseJson.put("succ", false);;
		}
		//context.setLvmInfo(responseJson);

		return responseJson.toJSONString();
	}
	
	
	private JSONObject getLVMInfo(URLHandlerContext context) {
		
		JSONObject responseJson = new JSONObject();
		
		try {
			String fdomain = "";//urlAnalyzerManager.getDomain(context.getFromurl());
			//context.setFdomain(fdomain);
			String productId = context.getSid();
			
			//按电商域名判断
			Set<byte[]> merchantSet = baseRedisService.getSet((CacheConstant.OMS_LVM + "_" + productId+ "_" + fdomain).getBytes());
			Iterator<byte[]> merchantIt = merchantSet.iterator();
			while (merchantIt.hasNext()){
				byte[] merchant = merchantIt.next();
				LVMMerchantFilterModel lvmMerchant = (LVMMerchantFilterModel) SerializeUtil.unserialize(merchant);
				boolean businessFilter = filter(context, lvmMerchant);
				responseJson.put(lvmMerchant.getBusinessCode(), 
						responseJson.containsKey(lvmMerchant.getBusinessCode())?(Boolean)responseJson.get(lvmMerchant.getBusinessCode())&&businessFilter:businessFilter);
			}
			
			//按渠道判断
			Set<byte[]> channelSet = baseRedisService.getSet((CacheConstant.OMS_LVM + "_" + productId+ "_" + context.getChannel()).getBytes());
			Iterator<byte[]> channelIt = channelSet.iterator();
			while(channelIt.hasNext()){
				byte[] channel = channelIt.next();
				LVMChannelFilterModel lvmChannel = (LVMChannelFilterModel) SerializeUtil.unserialize(channel);
				boolean businessFilter = filter(context, lvmChannel);
				responseJson.put(lvmChannel.getBusinessCode(), 
						responseJson.containsKey(lvmChannel.getBusinessCode())?(Boolean)responseJson.get(lvmChannel.getBusinessCode())&&businessFilter:businessFilter);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return responseJson;
	}
	
	
	
	private boolean filter (URLHandlerContext context, LVMFilterModel lvmFilterModel){
		
		if (lvmFilterModel.getIsOpen().equals("0")) return false;
		
		//地域判断
		String ipString = context.getRequestData().getString("ip");
		JSONObject jsonObject = IPToolUtil.getCityofIp(ipString);
		if (jsonObject.getInteger("code")==200){
			String region = jsonObject.containsKey("city")?jsonObject.getString("city"):null;
			//String regionEn = jsonObject.containsKey("regionEn")?jsonObject.getString("regionEn"):null;
			String adRegion = lvmFilterModel.getRegion();
			if(!StringUtils.isBlank(region) && !StringUtils.isBlank(adRegion)){
				String[] regions = adRegion.split(",");
				for (String regionString : regions){
					if(region.equals(regionString.trim())||region.contains(regionString.trim())){
						return true;
					}
				}
			}else{
				return true;
			}
		} else{
			return true;
		}
		
		//uuid尾号判断
		//判断UUID最后一位是否满足条件
		String uuidLastNo = lvmFilterModel.getuuidLastNo();
		String uuid =  context.getUuid();
		if (!StringUtils.isBlank(uuidLastNo) && uuid.length()>8){
			String lastUuid = uuid.substring(uuid.length()-1, uuid.length());
			String[] uuidLastNoStrings = uuidLastNo.split(",");
			for(String uuidLast : uuidLastNoStrings){
				if(lastUuid.equals(uuidLast.trim())){
					return true;
				}
			}
		} else {
			return true;
		}
		
		///分流取模
		int mod=UUIDUtils.moduuid(context.getUuid(), context.getUuid().length()-4, context.getUuid().length(), 100);
		if(lvmFilterModel.getUuidStart()==null || lvmFilterModel.getUuidEnd()==null ){
			return true;
		}else if (mod>=lvmFilterModel.getUuidStart()&&mod<lvmFilterModel.getUuidEnd()){
			return true;
		}
		
		//投放时间点判断
		String timePoint = lvmFilterModel.getTimePoint();
		Calendar c = Calendar.getInstance(); 
		int hours = c.get(Calendar.HOUR_OF_DAY);
		if(!StringUtils.isBlank(timePoint)){
			String[] filterHours = timePoint.split(",");
			for (String filterHour : filterHours){
				if(hours==Integer.valueOf(filterHour.trim())){
					return true;
				}
			}
		}else{
			return true;
		}
		
		return false;
	}
	


	/**
	 * 获取第二次跳转的主域名部分
	 * 如
	 * @param ourl
	 * @return
	 */
	private static String getRedirectHost(String ourl){
		String host = "";
		try {
			String[] ourlString = ourl.split("\\?")[0].split("/");
			for (int i=0 ; i<ourlString.length-1; i++){
				host += ourlString[i];
				host += "/";
			}
		} catch (Exception e) {
			return "";
		}
		return host;
	}


	@Override
	public String getRedirectURL(CPSURLHandlerContext context) {
		return null;
	}

	@Override
	public String[] getApplyTO() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String[] args) {
		Calendar c = Calendar.getInstance(); 
		int hours = c.get(Calendar.HOUR_OF_DAY);
		System.out.println(hours);
		
	}


	@Override
	protected ClickType getClickType() {
		// TODO Auto-generated method stub
		return null;
	}




}
