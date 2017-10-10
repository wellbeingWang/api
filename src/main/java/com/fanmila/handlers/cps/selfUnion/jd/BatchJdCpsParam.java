package com.fanmila.handlers.cps.selfUnion.jd;


import com.fanmila.util.http.HttpTookit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 批量转换cps
 * @author lscm
 *
 */
public class BatchJdCpsParam extends BaseJdCpsParam {
	public static final String URL="http://api.cps.jd.com/v2/promotion/batch/getcode";
	
	private List<BatchJdCpsUrl> urlList = new ArrayList<BatchJdCpsUrl>();
	private  static final String PIN="jd_7c93628581053";
	private static final long UNIONID=36339;
	
	public static void main(String[] args) {
	 String turl ="http://item.jd.com/11224855.html";
		
		String json=""; 
		BatchJdCpsParam sjd = new BatchJdCpsParam(UNIONID);
		sjd.setPin(PIN);
		sjd.setSubUnionId("feekbakc");
		sjd.addUrl(turl);
		
		sjd.addUrl("http://item.jd.com/1148504263.html");
		try {
			json =sjd.toJsonString();
		} catch (Exception e) {
			json = "{\"urlList\":[{\"id\":1,\"url\":\""+turl+"\"}],\"pin\":\""+PIN+"\",\"unionId\":"+UNIONID+",\"subUnionId\":\"feedback\",\"channel\":\"PC\",\"webId\":\"\",\"ext1\":\"\"}";
		}
		
		try {
			String rr =null;
			String k = JdCpsDES.getInstance().encrypt(json);
			
			Map<String,String> params =new HashMap<String, String>();
			params.put("pin","jd_7c93628581053");	
			params.put("json",k);
			rr= HttpTookit.doPost(BatchJdCpsParam.URL, params);
			
			System.out.println(rr);
			/*k = java.net.URLEncoder.encode(k,"UTF-8");
			String par ="pin="+PIN+"&k="+k ;//+"&pin="+pin;
			rr =  PostSendTool.sendMessage(SingleJdCpsParam.URL, par);*/
			
			
			JdCpsResult jc = JdCpsResult.createFromJson(rr);
			if("0".equals(jc.getResultCode())){
				System.out.println(jc.getUrlList().get(0).getUrl());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
}
	
	
	public BatchJdCpsParam() {
		super();
	}
	public BatchJdCpsParam(long unionId, String channel) {
		super(unionId, channel);
	}
	public BatchJdCpsParam(long unionId) {
		super(unionId);
	}
	public BatchJdCpsParam(long unionId, String subUnionId, String channel,
			String webId, String ext1) {
		super(unionId, subUnionId, channel, webId, ext1);
	}
	public boolean addUrl(String url){
		int id =urlList.size();
		if(id== 500){
			return false;
		}
		return urlList.add(new BatchJdCpsUrl(id+1 , url));
	}
	public List<BatchJdCpsUrl> getUrlList() {
		return urlList;
	}

	public void setUrlList(List<BatchJdCpsUrl> urlList) {
		this.urlList = urlList;
	}
	
	
	

}
