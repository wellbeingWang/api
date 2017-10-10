package com.fanmila.handlers.cps.selfUnion.jd;

import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.request.cps.ServicePromotionGetcodeRequest;
import com.jd.open.api.sdk.response.cps.ServicePromotionGetcodeResponse;

public class TestJdJos {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		/*String codeurl = "https://oauth.jd.com/oauth/authorize?response_type=code&client_id=5A9A3DAE445FE72FDEF45B05F757C287&redirect_uri=http://www.b5m.com&state=success";
		String rr = HttpTookit.doGet(codeurl, null);
		URL uri = new URL(codeurl);
		HttpURLConnection conn =(HttpURLConnection) uri.openConnection();
		conn.setRequestProperty("Accept-Charset","utf-8");
		conn.setRequestMethod("POST");
		conn.getURL();*/
		openApi();
		//System.out.println(rr+" "+conn.getURL()+" "+code+" ");

	}
	private static final long UNIONID=1000152763;

	private static final String accessToken="8e3e9ed1-9a8f-4ea6-b9ee-baf8a041c14f";
	private static final String appKey="34F21799ED028991FBE37D26B73D2DBA";
	private static final String appSecret="b0beeffa8ca34210a57e9d20606c0539";
	private static final String SERVER_URL="https://api.jd.com/routerjson";
	
	public static void openApi() throws Exception{
		JdClient client=new DefaultJdClient(SERVER_URL,accessToken,appKey,appSecret);

		ServicePromotionGetcodeRequest request=new ServicePromotionGetcodeRequest();

		request.setPromotionType( 7 );
		request.setMaterialId( "https://item.jd.com/2495282.html" );
		request.setUnionId(UNIONID );
		request.setSubUnionId( "jingdongTest" );
		request.setChannel( "PC" );
		request.setWebId("860758847");
		request.setExtendId( "jingdong" );
		request.setExt1( "jingdong" );
		request.setAdttype("6");

		ServicePromotionGetcodeResponse response=client.execute(request);
//		System.out.println(response.getMsg());
//		System.out.println(response.getQueryjsResult());
		JdCpsResult jc = JdCpsResult.createFromJson(response.getQueryjsResult());
		System.out.println(jc);
	}

}
