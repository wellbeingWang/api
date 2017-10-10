package com.fanmila.handlers.oms;

import java.util.Calendar;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fanmila.model.URLHandlerContext;
import com.fanmila.cms.model.cps.CPSURLHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.fanmila.handlers.DmpURLHandler;
import com.fanmila.model.ClickHandlerInfo;
import com.fanmila.model.ClickType;
import com.fanmila.model.common.CacheConstant;
import com.fanmila.util.LZStringUtil;
import com.fanmila.util.analyzer.URLAnalyzerManager;
import com.fanmila.service.impl.BaseRedisServiceImpl;
/**
 * 处理
 * @author zhenyuanzi
 *
 */
@Component
public class Lv1DhConfigHandler extends DmpURLHandler{
	

	@Autowired
	private BaseRedisServiceImpl baseRedisService;// = ContextUtils.getBean(BaseRedisServiceImpl.class);
	
	@Autowired
	private URLAnalyzerManager urlAnalyzerManager;

	
	@Override
	public String getRedirectURL(URLHandlerContext context, ClickHandlerInfo handlerInfo) {
		

		JSONObject responseJson = new JSONObject();
		try {
			responseJson = getLVMInfo(context);
			if(responseJson==null||responseJson.isEmpty()){
				responseJson.put("status", -1);
			}
			
		
		} catch (Exception e) {
			context.getHandlerInfo().setSuccess(false);
			e.printStackTrace();
			responseJson.put("status", -1);;
		}
		//context.setLvmInfo(responseJson);

		return responseJson.toJSONString();
	}
	
	
	private JSONObject getLVMInfo(URLHandlerContext context) {
		
		JSONObject responseJson = new JSONObject();
		
		try {
			String fdomain = "";//context.getFdomain();
			
			//按电商域名判断
			Map<String, String> configMap = baseRedisService.getMap((CacheConstant.OMS_LV1_DAOHANG_CONFIG + fdomain));
			if(true//context.getVersion()!=null
					&&configMap.containsKey("v")&&configMap.containsKey("c")){
				if(/*context.getVersion().equals(configMap.get("v"))*/true){
					responseJson.put("status", 0);
					responseJson.put("d", fdomain);
				} else {
					String config = LZStringUtil.compressToEncodedURIComponent(replaceEnter(configMap.get("c")));
					responseJson.put("v", configMap.get("v"));
					responseJson.put("c", config);
					responseJson.put("d", fdomain);
					responseJson.put("status", 1);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return responseJson;
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
	
	public static String replaceEnter(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("(\r\n|\r|\n|\n\r)");
            Matcher m = p.matcher(str);
            dest = m.replaceAll(" ");
        }
        return dest;
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
