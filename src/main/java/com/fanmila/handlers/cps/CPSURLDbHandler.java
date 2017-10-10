package com.fanmila.handlers.cps;

import com.alibaba.fastjson.JSONObject;
import com.fanmila.cms.model.cps.*;
import com.fanmila.handlers.IUnionHandler;
import com.fanmila.model.ClickHandlerInfo;
import com.fanmila.model.ClickType;
import com.fanmila.model.URLHandlerContext;
import com.fanmila.model.Union;
import com.fanmila.model.cache.CacheConstant;
import com.fanmila.service.impl.BaseRedisServiceImpl;
import com.fanmila.util.IPToolUtil;
import com.fanmila.util.analyzer.URLAnalyzerManager;
import com.fanmila.util.common.URLUtils;
import com.fanmila.util.common.UUIDUtils;
import com.fanmila.util.framework.ContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 * 数据库配置的cps处理规则解析流程
 * @author guixinshu
 *
 */
@Component
public class CPSURLDbHandler extends CPSURLHandler implements IDbHandler {
	@Autowired
	private URLAnalyzerManager urlAnalyzerManager;
	@Autowired
	private BaseRedisServiceImpl baseRedisService;
	@Override
	public String getRedirectURL(CPSURLHandlerContext context, ClickHandlerInfo handlerInfo) {
		try {
			String durl =null;
			String statUrl = null;
			
			CpsPdtDomainRule cpdr = findCpsRule(context);

			
			
			if(null == cpdr || !cpdr.isEnable() ){
				return null;
			}
			///分流取模
			int mod= UUIDUtils.moduuid(context.getUuid(), context.getUuid().length()-4, context.getUuid().length(), 100);
			//获取当前所有的分流策略 找到该区间的分流过程
			List<CpsRuleRange> crrList = cpdr.getRuleRangeList();
			
			for(CpsRuleRange crr : crrList ){
				 int start_range = crr.getStart_range();
				 int end_range = crr.getEnd_range();
				 if(mod>=start_range && mod< end_range){
//logger.error("*******************MOD = "+mod+" **************************************************************");	
					 durl = getRedirectURL(crr,context);
					 statUrl = crr.getStat_url();
					 break;
				 }
				 
			}
		//	https://click.linktech.cn/?m=360buy&a=A100230121&l=99999&l_cd1=0&l_cd2=1&tu=https%3A%2F%2Fwww.jd.com
			/*if(urlAnalyzerManager.getDomain(context.getTurl()).equals("jd.com")){
				durl = "https://click.linktech.cn/?m=360buy&a=A100230228&l=99999&l_cd1=0&l_cd2=1&tu=";
				durl += URLEncoder.encode(context.getTurl(),"utf-8");
				durl = durl + "&u_id=" + getFeedback(context);
			}else{
				return null;
			}*/
			context.setDurl(durl);
			context.setStatUrl(statUrl);
			
			try {
//				logger.error(" "+new Gson().toJson(context));
			} catch (Exception e) {
				logger.error("", e);
			}
			return durl;
			
			
		} catch (Exception e) {
			logger.error("", e);
			return null;
		}
	}

	/**
	 * 获取规则；采用优先级判断
	 * 首先根据 类型、域名、产品来
	 * 然后根据域名、产品来
	 * 最后根据域名来
	 * @param context
	 * @return
	 */
	protected CpsPdtDomainRule findCpsRule(CPSURLHandlerContext context) {
		String sid = context.getSid();
		
		String lvT=context.getSsub();
		
		//类型+取全域名
		String tdomain = urlAnalyzerManager.getFullDomain(context.getTurl());//取全域名

		//////////首选根据 类型+域名+产品 来确定详细的规则/start//////////////////////////
		CpsPdtDomainRule cpdr = (CpsPdtDomainRule)baseRedisService.getObject(CacheConstant.CLT_CPS + tdomain+"_"+sid);
		//类型+普通一级域名或特殊的非一级域名
		if(null == cpdr){
			tdomain =urlAnalyzerManager.getCommonDomain(context.getTurl());//取全域名 特殊域名
			String a = CacheConstant.CLT_CPS + tdomain+"_"+sid;
			cpdr = (CpsPdtDomainRule)baseRedisService.getObject(a);
		}
		//类型+取段域名情况
		if(null == cpdr){
			tdomain = urlAnalyzerManager.getDomain(context.getTurl());//去短域名
			cpdr = (CpsPdtDomainRule)baseRedisService.getObject(CacheConstant.CLT_CPS + tdomain+"_"+sid);
		}

		context.setTdomain(tdomain);
		return cpdr;
	}

	
	protected String getRedirectURL(CpsRuleRange crr,CPSURLHandlerContext context){

//		String format = "http://p.yiqifa.com/c?s=%s&w=%s&c=%s&i=%s&l=%s&e=%s&t=%s";
//		urlto = appendParams(urlto, context);
//		// jd.com,28769ae1,589570,254,160,综合商城
		//  s=28769ae1 &c=254 &i=160 &l=综合商城 { "s":"商家ID","c":"模块编码","i":"子模块id","l":"模块名称"}
//		String[] ps = campaign.get(context.getTdomain()+"_"+context.getSid());
//		handlerInfo.setUnion(new Union(ps[1], "亿起发"));
//		handlerInfo.setSuccess(true);
	
		//	28769ae1,589570,254,160,综合商城
		//http://p.yiqifa.com/c?s=28769ae1&w=589570&c=254&i=160&l=综合商城&e=%s&t=%s
//		return String.format(format, ps[0], ps[1], ps[2], ps[3], ps[4], getFeedback(context), urlto);
		CpsAccount cpsAccount = crr.getCpsAccount();
		CpsUrlPattern cpsUrlPattern = crr.getCpsUrlPattern();
		
		JSONObject extraParams =  crr.getExtraParamsMap();
		//String cpsUrl = "";
		//ip过滤

		if (null!=extraParams){
			String ipCity = extraParams.containsKey("ip")?extraParams.getString("ip"):null;
			if(null!=ipCity){
				String[] ipCityList = ipCity.split("\\|");
				JSONObject jsonObject;
				try {
					String ipString = context.getRequestData().getString("ip");
					 jsonObject = IPToolUtil.getCityofIp(ipString);
					 if(null!=jsonObject){
						 String city = jsonObject.getString("city");
						 String cityEn = jsonObject.getString("cityEn");
						 for(String ipPassCity:ipCityList){
							 if(city.equals(ipPassCity.trim())||cityEn.equals(ipPassCity.trim())){
								 logger.error(ipString + " ip is passed; " +"cpsAccount: " +cpsAccount.getAct_desc());
								 return null;
							 }
						 }
					 }
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			 
		 }
		
		String cpsUrl = cpsConvert(context, cpsAccount, cpsUrlPattern);
		
		///处理日志
		/*logger.info("**************************"+crr.getStart_range()+"---"+crr.getEnd_range()+"***************************************************************");
		if(null != cpsAccount){
			logger.error(cpsAccount.toString());
		}
		if(null != cpsUrlPattern){
			logger.error(cpsUrlPattern.toString());
			logger.info(cpsUrlPattern.getUrl_pattern());
		}
		logger.error(cpsUrl);
		logger.info("*****************************************************************************************");*/
		
		return cpsUrl;
	}


	@SuppressWarnings("unused")
	public String cpsConvert(CPSURLHandlerContext context,
							 CpsAccount cpsAccount, CpsUrlPattern cpsUrlPattern) {
		Map<String,String> extParamMap=new LinkedHashMap<String, String>();
		
		String urlto = context.getTurl();
		//目的地址转换、清洗 通过程序中的正则表达式解析
		urlto = getTrimDestUrl(context);
		
		//目的地址拼接额外参数
		if(false){///目前目的地址暂时不拼接额外的参数，因为目前拼接上无任何意义
			urlto = appendParams(urlto, context);
		}
		
		if(null != context.getPid()&&!"".equals(context.getPid().trim())){
			try {
				String paramOther = context.getPid().trim();
				String [] p1= paramOther.split("&");
				for(String p11 :p1){
					if(p11.indexOf("=")>-1){
						String []pp1=p11.split("=");
						extParamMap.put(pp1[0], pp1[1]);
					}
				}
			} catch (Exception e) {
			}
			urlto=URLUtils.appendOrUpdateParams( urlto , extParamMap);
			extParamMap.clear();
		}
		
		// 获取分流策略中的额外参数， 目前暂时未提供对外的配置
		/*Map<String,String> extParamMap =crr.getExtraParamsMap();
		if( null != extParamMap){
			urlto = URLUtils.appendOrUpdateParams(urlto, extParamMap);
		}else{
			extParamMap=new LinkedHashMap<String, String>();
		}*/
		
		
		
		
		
		//地址转换 特殊字符过滤  类似亿起发转换后不能跳转的问题处理
		if(cpsUrlPattern.IsUrlToEncode()){
			try {
				urlto = URLEncoder.encode(urlto, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(), e);
			}
		}
		
		//联盟日志记录
		ClickHandlerInfo handlerInfo = context.getHandlerInfo();
		handlerInfo.setUnion(new Union(cpsUrlPattern.getUnion_id(), cpsUrlPattern.getUnion_name()));
		handlerInfo.setSuccess(true);
		handlerInfo.setClickType(cpsUrlPattern.getClickType());
//		handlerInfo.recordHandler(this);
		
		String url_static_addr = cpsUrlPattern.getUrl_static_addr();// 根据联盟解析类型 可以为url地址、url格式化地址、类的全路径名
		String url_param_fb  = cpsUrlPattern.getUrl_param_fb();//url feedback参数名称
		String url_param_to  = cpsUrlPattern.getUrl_param_to();//url to目标地址参赛名称
		String url_param_1 = cpsUrlPattern.getUrl_param_1();//参数1 标记为联盟参数名称
		if(null == url_static_addr){
			return null;
		}
		
		extParamMap.clear();
		
		//使用帐号模版 解析帐号数据
		Map<String,String> actpm =  cpsUrlPattern.findAccountPattenMap();
		List<String> actKeyList=new ArrayList<String>();//参数的名称集合
		if(null !=actpm && null != cpsAccount && null != cpsAccount.findAccountMap()){
			Map<String,String> actMap = cpsAccount.findAccountMap();
			Iterator<Entry<String, String>> actpmI = actpm.entrySet().iterator();
			while(actpmI.hasNext()){
				Entry<String, String> itemPm = actpmI.next();
				String paramKey = itemPm.getKey().trim();
				actKeyList.add(paramKey);
//				String paramDesc = itemPm.getValue().trim();
				String paramValue = actMap.get(paramKey);
				if(null == paramValue){
					logger.info(actpm +"与"+actMap +"不能完成数据匹配");
					paramValue="";
				}
				extParamMap.put(paramKey, paramValue.trim());
			}
		}
		//ROI合作和CPS的主要不同是反馈标签的不同，对于ROI合作之前RequestData添加了fb参数。
		if(null != url_param_fb && !"".equals(url_param_fb.trim())){
			if (context.getRequestData().containsKey("fb")){//ROI活动，直接赋予反馈标签
				extParamMap.put(url_param_fb.trim(), context.getRequestData().getString("fb"));
			}else{//其他的CPS按照反馈标签的生成规则生成
			    extParamMap.put(url_param_fb.trim(), getFeedback(context));
			}
		}
		if(null != url_param_to && !"".equals(url_param_to.trim())){
			extParamMap.put(url_param_to.trim(), urlto);
		}
		//设置联盟id 
		if(null != url_param_1 && !"".equals(url_param_1.trim())){
			if(null != extParamMap.get(url_param_1.trim())){
				handlerInfo.getUnion().setAccountId(extParamMap.get(url_param_1.trim()));
			}
		}
		
		
		
		
		//填充csp转换请求参数 根据参数模版来解析；
		// 如果参数模版不是一http://开头 那么静态模版参数将直接是参数 需要目的地址拼接一起格式化 
		String  cpsUrl = null;
		if("S".equalsIgnoreCase(cpsUrlPattern.getUnion_type())){//String.format方式
			Object [] actArra = new String[actKeyList.size()];
			for(int i =0;i< actKeyList.size() ; i++ ){
				actArra[i] =extParamMap.get(  actKeyList.get(i) );
			}
			
			try {
				url_static_addr =url_static_addr.trim();
				if(!url_static_addr.startsWith("http://")&&!url_static_addr.startsWith("https://")){//如果参数模版不是一http://开头 那么静态模版参数将直接是参数 需要目的地址拼接一起格式化 
					if(urlto.indexOf("?")>-1){
						url_static_addr=urlto+"&"+url_static_addr;
					}else{
						url_static_addr = urlto+"?"+url_static_addr;
					}
				}
				cpsUrl = String.format(url_static_addr, actArra);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
				try {
					for (Object xx : actArra) {
						url_static_addr= url_static_addr.replaceFirst("%s", xx.toString());
					}
					cpsUrl = url_static_addr;
				} catch (Exception e2) {
					logger.error(e2.getMessage(),e2);
				}
			}
		}else if("MF".equalsIgnoreCase(cpsUrlPattern.getUnion_type())){//MessageFormat.format(方式
			Object [] actArra = new String[actKeyList.size()];
			for(int i =0;i< actKeyList.size() ; i++ ){
				actArra[i] =extParamMap.get(  actKeyList.get(i) );
			}
			
			try {
				url_static_addr =url_static_addr.trim();
				if(!url_static_addr.startsWith("http://")&&!url_static_addr.startsWith("https://")){//如果参数模版不是一http://开头 那么静态模版参数将直接是参数 需要目的地址拼接一起格式化 
					if(urlto.indexOf("?")>-1){
						url_static_addr=urlto+"&"+url_static_addr;
					}else{
						url_static_addr = urlto+"?"+url_static_addr;
					}
				}
				cpsUrl = MessageFormat.format(url_static_addr, actArra);
			} catch (Exception e) {
				try {
					int ii=0;
					for (Object xx : actArra) {
						url_static_addr= url_static_addr.replaceFirst("{"+ii+"}", xx.toString());
						ii++;
					}
					cpsUrl = url_static_addr;
				} catch (Exception e2) {
					logger.error(e2.getMessage(),e2);
				}
			}
		}else if("P".equalsIgnoreCase(cpsUrlPattern.getUnion_type()) ){//参数拼接方式
			 cpsUrl = URLUtils.appendOrUpdateParams(url_static_addr, extParamMap);
		}else if("I".equalsIgnoreCase(cpsUrlPattern.getUnion_type()) ){//接口话实例来生成
			//从容器中获取接口实例来处理
			Map<String, IUnionHandler> map = ContextUtils.getApplicationContext().getBeansOfType(IUnionHandler.class);
			for (Entry<String, IUnionHandler> entry : map.entrySet()) {
				IUnionHandler handler = entry.getValue();
				if(handler.getClass().getName().equals(url_static_addr.trim())){
					cpsUrl=handler.getRedirectURL(context);
					break;
				}
			}
		}else if("D".equalsIgnoreCase(cpsUrlPattern.getUnion_type()) ){//自营类直接跳转 待测试
			try {
				if(null != url_param_to && !"".equals(url_param_to.trim())){
					extParamMap.remove(url_param_to.trim());
				}
				//反编码
				if(cpsUrlPattern.IsUrlToEncode()){
					cpsUrl=URLUtils.appendOrUpdateParams( URLDecoder.decode(urlto, "UTF-8") , extParamMap);
				}else{
					cpsUrl=URLUtils.appendOrUpdateParams( urlto , extParamMap);
				}
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(),e);
			}
		}else{
			cpsUrl = URLUtils.appendOrUpdateParams(url_static_addr, extParamMap);
		}
		return cpsUrl;
	}
	
	/*@PostConstruct
	public void init() {
		CpsDBTool.flash();
		RoiDBTool.flash();
	}*/

	@Override
	protected ClickType getClickType() {
		return ClickType.CPS;
	}


	@Override
	public String getRedirectURL(URLHandlerContext context) {
		return null;
	}

	@Override
	public String[] getApplyTO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFeedback(URLHandlerContext context) {
		return null;
	}


}
