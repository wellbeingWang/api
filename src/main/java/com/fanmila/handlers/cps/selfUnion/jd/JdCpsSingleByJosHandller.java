package com.fanmila.handlers.cps.selfUnion.jd;


import com.fanmila.cms.model.cps.CPSURLHandlerContext;
import com.fanmila.handlers.cps.CPSURLHandler;
import com.fanmila.handlers.cps.selfUnion.ISelfUnionHandler;
import com.fanmila.model.ClickHandlerInfo;
import com.fanmila.model.ClickType;
import com.fanmila.model.URLHandlerContext;
import com.fanmila.model.Union;
import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.request.cps.ServicePromotionGetcodeRequest;
import com.jd.open.api.sdk.response.cps.ServicePromotionGetcodeResponse;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class JdCpsSingleByJosHandller extends CPSURLHandler implements ISelfUnionHandler {
	//private static final String PIN="jd_7c93628581053";
	private static final long UNIONID=1000152763;
	
	private static final String accessToken="8e3e9ed1-9a8f-4ea6-b9ee-baf8a041c14f";
	private static final String appKey="34F21799ED028991FBE37D26B73D2DBA";
	private static final String appSecret="b0beeffa8ca34210a57e9d20606c0539";
	private static final String SERVER_URL="https://api.jd.com/routerjson";
	
	
	@Override
	public String getRedirectURL(CPSURLHandlerContext context, ClickHandlerInfo handlerInfo) {
		
		handlerInfo.setUnion(new Union(""+UNIONID, "京东PC联盟"));
		
		String turl =getToUrl(context);
		
		try {
			turl = turl.split("\\?")[0];
		} catch (Exception e1) {
			logger.info(e1+"----  JD链接: "+turl);
		}
		

		try {
			
			JdClient client=new DefaultJdClient(SERVER_URL,accessToken,appKey,appSecret);

			ServicePromotionGetcodeRequest request=new ServicePromotionGetcodeRequest();

			request.setPromotionType( 7 );
			request.setMaterialId( turl );
			request.setUnionId(UNIONID );
			request.setSubUnionId( getFeedback(context) );
			request.setChannel( "PC" );
            request.setWebId("860758847");
			request.setExtendId(getFeedback(context));
			ServicePromotionGetcodeResponse response=client.execute(request);
			String rr = response.getQueryjsResult();
			JdCpsResult jc = JdCpsResult.createFromJson(rr);


			if(null ==jc){//为空需检查参数是否正确
				//不能延迟发送 时间太久 用户不能等待 
				logger.error("。。。。。。。。。。。。。。。。。。。。京东接口参数异常，com.b5m.cps.handlers.selfunion.jd.TestJdJos测试。。。。。。。。。。。。。。。。。。。。。。。。");
			}
			
			/*k = java.net.URLEncoder.encode(k,"UTF-8");
			String par ="pin="+PIN+"&k="+k ;//+"&pin="+pin;
			rr =  PostSendTool.sendMessage(SingleJdCpsParam.URL, par);*/
			
			
			try {
				
				if("0".equals(jc.getResultCode())){
					handlerInfo.setSuccess(true);
					return jc.getUrl();
				}else{
					handlerInfo.setSuccess(false);
					logger.error("京东PC联盟CPS转换失败："+rr);
					return null;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("京东接口返回数据为："+rr);
				throw new Exception("京东接口返回数据解析异常，异常原因为返回数据异常，不符合接口规范；rr="+rr+".");
			}
			
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
			handlerInfo.setSuccess(true);
			return null ;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			handlerInfo.setSuccess(true);
			return null ;
		}
		
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

	@Override
	protected ClickType getClickType() {
		// TODO Auto-generated method stub
		return ClickType.CPS;
	}

	
	
	

}
