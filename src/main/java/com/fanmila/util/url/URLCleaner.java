package com.fanmila.util.url;

import com.alibaba.fastjson.JSONObject;
import com.fanmila.util.framework.MD5Utils;
import com.fanmila.util.http.HttpTookit;

public class URLCleaner{
	public String evaluate(final String s) {
		if(s == null) {
			return null;
		}
		return getString(s);
	}

	public static String getString(String s) {
		String decodeString = "";
		
		try {
//			if(s.indexOf("%25") > 0){
//				decodeString = URLDecoder.decode(URLDecoder.decode(s,"UTF-8"),"UTF-8");
//			} else {
//				decodeString = URLDecoder.decode(s,"UTF-8");
//			}
			
//			decodeString = URLDecoder.decode(s,"UTF-8");
			//decodeString = FilterUtils.getRefineUrl(URLDecoder.decode(s, "UTF-8"));
			decodeString = FilterUtils.getRefineUrl(s);
			
		} catch (Exception e) {
			
			decodeString = s;

		}  		
		return decodeString;
	}
	
	public static void main2(String[] args) throws Exception {
		
		System.out.println(getString("https://item.taobao.com/item.htm?id=522170802062&ali_refid=a3_430582_1006:1121517066&ali_trackid=1_cba5ffc9cd4a120b8086524018e67dda&spm=a230r.1.14.6.rCq2Sc#detail"));
		System.out.println(getString("http://item.taobao.com/item.htm?id=522170802062&ali_refid=a3_430582_1006:1121517066"));
		System.out.println(Escape.unescape("http://s.taobao.com/search?q=%E6%8F%92%E5%BA%A7&commend=all&ssid=s5-e&search_type=item&sourceId=tb.index&spm=1.7274553.1997520841.1&initiative_id=tbindexz_20150610"));
		JSONObject jsonObject = new JSONObject();
		String urlString = getString("http://item.jd.com/1743191.html");
		System.out.println(urlString);
		jsonObject.put("DOCID", MD5Utils.md5(urlString));
		//jsonObject.put("DOCID", md5.md5(urlString));
		jsonObject.put("Title", "小米 红米2A 增强版 白色 移动4G手机 双卡双待");
		jsonObject.put("Price", 499);
		jsonObject.put("Source", "京东商城");
		//jsonObject.put("Originalcategory", "手机数码>手机>");
		//String aString = HttpTookit.doPost("http://10.30.96.199:18801", jsonObject.toJSONString());
		//System.out.println(aString);
		
	}
	
	public static void main(String[] args) throws Exception {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("uid", "aaaa");
		jsonObject.put("sid", "2900");
		//jsonObject.put("url", "http://item.jd.com/19171967.html?a=b");
		//jsonObject.put("note", "tinghaode a!!!");
		
		String aa = HttpTookit.doPost("http://172.16.100.10:8080/hydra/barrage/get.do", jsonObject.toJSONString());
		System.out.println("111111111111");
		System.out.println(aa);
		
	}
}
