package com.fanmila.util.http;
import com.alibaba.fastjson.JSONObject;
import com.fanmila.util.common.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionParams;*/

/**
 * 基于 httpclient 4.3.1版本的 http工具类
 * @author mcSui
 *
 */
@SuppressWarnings("deprecation")
public class HttpTookit {
	private static Logger log = LoggerFactory.getLogger("NewCpsController");

    private static final CloseableHttpClient httpClient;
    public static final String CHARSET = "UTF-8";
 
    static {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(15000).build();
        httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    }
    
    private static PoolingClientConnectionManager pccm = null;  
    private static HttpParams params =null;
    static{
    	// 设置组件参数, HTTP协议的版本,1.1/1.0/0.9 
    			params = new BasicHttpParams(); 
    		    HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
    		    HttpProtocolParams.setUserAgent(params, "HttpComponents/1.1"); 
    		    HttpProtocolParams.setUseExpectContinue(params, true); 	  

    		    //设置连接超时时间 
    		    int REQUEST_TIMEOUT = 10*1000;	//设置请求超时10秒钟 
    			int SO_TIMEOUT = 10*1000; 		//设置等待数据超时时间10秒钟 
    			//HttpConnectionParams.setConnectionTimeout(params, REQUEST_TIMEOUT);
    			//HttpConnectionParams.setSoTimeout(params, SO_TIMEOUT);
    		    params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, REQUEST_TIMEOUT);  
    		    params.setParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT); 
    		  
    			//设置访问协议 
    			SchemeRegistry schreg = new SchemeRegistry();  
    			schreg.register(new Scheme("http",PlainSocketFactory.getSocketFactory(),80)); 
    			schreg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443)); 	  
    			
    			//多连接的线程安全的管理器 
    			pccm = new PoolingClientConnectionManager(schreg);
    			pccm.setDefaultMaxPerRoute(20);	//每个主机的最大并行链接数 
    			pccm.setMaxTotal(100);			//客户端总并行链接最大数    
    }
	/**
	 * 适合多线程的HttpClient,用httpClient4.2.1实现
	 * @return DefaultHttpClient
	 */
	public static DefaultHttpClient getHttpClient()
	{  		
		DefaultHttpClient httpClient = new DefaultHttpClient(pccm, params);  
		return httpClient;
	}
 
    public static String doGet(String url, Map<String, String> params){
        return doGet(url, params,CHARSET);
    }
    
    public static String doGet(String url, JSONObject params){
        return doGet(url, params,CHARSET);
    }
    
    
    public static String doPost(String url, Map<String, String> params) throws Exception{
        return doPost(url, params,CHARSET);
    }
    
    public static String doGetYouDao(String url,JSONObject params) throws Exception {
    	return doGetYouDao(url, params, CHARSET);
    }
    
    /**
     * HTTP Get 获取内容
     * @param url  请求的url地址 ?之前的地址
     * @param params 请求的参数
     * @param charset    编码格式
     * @return    页面内容
     */
    public static String doGet(String url,Map<String,String> params,String charset){
        if(StringUtils.isBlank(url)){
            return null;
        }
        try {
            if(params != null && !params.isEmpty()){
                List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
                for(Map.Entry<String,String> entry : params.entrySet()){
                    String value = entry.getValue();
                    if(value != null){
                        pairs.add(new BasicNameValuePair(entry.getKey(),value));
                    }
                }
                url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
            }
            System.out.println("get URL：" + url);
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpGet.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null){
                result = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * HTTP Get 获取内容
     * @param url  请求的url地址 ?之前的地址
     * @param params 请求的参数
     * @param charset    编码格式
     * @return    页面内容
     */
    public static String doGet(String url,JSONObject params,String charset){
        if(StringUtils.isBlank(url)){
            return null;
        }
        try {
            if(params != null && !params.isEmpty()){
                List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
                for(String key : params.keySet()){
                    String value = params.getString(key);
                    if(value != null){
                        pairs.add(new BasicNameValuePair(key,value));
                    }
                }
                url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
            }
            //System.out.println("get URL：" + url);
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpGet.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null){
                result = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
	/** 
	 * @Title: doGetYouDao 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param api
	 * @param @param adRequest
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @throws 
	 */
	public static String doGetYouDao(String url, JSONObject params, String charset) {
		if(StringUtils.isBlank(url)){
            return null;
        }
        try {
            if(params != null && !params.isEmpty()){
                List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
                for(String key : params.keySet()){
                    String value = params.getString(key);
                    if(value != null){
                        pairs.add(new BasicNameValuePair(key,value));
                    }
                }
                String ss = EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
                String base64 = String.valueOf(Base64.encode(ss.getBytes()));
                String s = StringUtils.reverse(base64);
            
                url += "?" + "s=" + s + "&yedt=2";
            }
            //System.out.println("get URL：" + url);
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpGet.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null){
                result = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
	}
     
    /**
     * HTTP Post 获取内容
     * @param url  请求的url地址 ?之前的地址
     * @param params 请求的参数
     * @param charset    编码格式
     * @return    页面内容
     * @throws Exception 
     */
    public static String doPost(String url,Map<String,String> params,String charset) throws Exception{
        if(StringUtils.isBlank(url)){
            return null;
        }
        try {
            List<NameValuePair> pairs = null;
            if(params != null && !params.isEmpty()){
                pairs = new ArrayList<NameValuePair>(params.size());
                for(Map.Entry<String,String> entry : params.entrySet()){
                    String value = entry.getValue();
                    if(value != null){
                        pairs.add(new BasicNameValuePair(entry.getKey(),value));
                    }
                }
            }
            HttpPost httpPost = new HttpPost(url);
            if(pairs != null && pairs.size() > 0){
                httpPost.setEntity(new UrlEncodedFormEntity(pairs,CHARSET));
            }
            //c测试1
//            HttpResponse response = getHttpClient().execute(httpPost);
            //测试2
            CloseableHttpResponse response = httpClient.execute(httpPost);
            
//            CloseableHttpResponse response = getHttpClient().execute(httpPost);
            
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null){
                result = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        } catch (Exception e) {
        	log.error(e.getMessage(),e);
        	throw e;
        }
    }
    
    public static HttpGet getHttpGet(String url, Map<String, String> params, String encode) {  
        StringBuffer buf = new StringBuffer(url);  
        if (params != null) {  
            // 地址增加?或者&  
            String flag = (url.indexOf('?') == -1) ? "?" : "&";  
            // 添加参数  
            for (String name : params.keySet()) {  
                buf.append(flag);  
                buf.append(name);  
                buf.append("=");  
                try {  
                    String param = params.get(name);  
                    if (param == null) {  
                        param = "";  
                    }  
                    buf.append(URLEncoder.encode(param, encode));  
                } catch (UnsupportedEncodingException e) {  
                    log.error("URLEncoder Error,encode=" + encode + ",param="  
                            + params.get(name), e);  
                }  
                flag = "&";  
            }  
        }  
        HttpGet httpGet = new HttpGet(buf.toString());  
        return httpGet;  
    }  
    
    /**
     * HTTP Post 获取内容
     * @param url  请求的url地址 ?之前的地址
     * @param params 请求的参数
     * @return    页面内容
     * @throws Exception 
     */
    public static String doPost(String url,String params) throws Exception{
        if(StringUtils.isBlank(url)){
            return null;
        }
        try {
            
            HttpPost httpPost = new HttpPost(url);
            if(params != null ){
            	StringEntity se = new StringEntity(params);
                httpPost.setEntity(se);
            }
            //c测试1
//            HttpResponse response = getHttpClient().execute(httpPost);
            //测试2
            CloseableHttpResponse response = httpClient.execute(httpPost);
            
//            CloseableHttpResponse response = getHttpClient().execute(httpPost);
            
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null){
                result = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        } catch (Exception e) {
        	log.error(e.getMessage(),e);
        	throw e;
        }
    }
    
    
    /** 
	 * @Title: doPost 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param api
	 * @param @param adRequest
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @throws 
	 */
	public static String doPost(String url, JSONObject jsonObj) throws Exception{
		if(StringUtils.isBlank(url)){
            return null;
        }
		
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_KEEP_ALIVE);
			httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json");

			if (jsonObj != null) {
				StringEntity se = new StringEntity(jsonObj.toString(), "utf-8");// 解决中文乱码问题
				se.setContentEncoding("UTF-8");
				se.setContentType("application/json");
				httpPost.setEntity(se);
			}
			
			CloseableHttpResponse response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200 && statusCode != 204) {
			    httpPost.abort();
			    throw new RuntimeException("HttpClient,error status code :" + statusCode);
			}	
			
			HttpEntity entity = response.getEntity();
			String result = null;
			if (entity != null){
			    result = EntityUtils.toString(entity, "utf-8");
			}
			EntityUtils.consume(entity);
			response.close();
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
        	throw e;
		}
	}
    
    /**
     * HTTP Post 获取内容
     * @param url  请求的url地址 ?之前的地址
     * @param params 请求的参数, headers 请求头参数
     * @return    页面内容
     * @throws Exception 
     */
    public static String doPost(String url,String params, Map<String, String> headers) throws Exception{
        if(StringUtils.isBlank(url)){
            return null;
        }
        try {
            
            HttpPost httpPost = new HttpPost(url);
            if(params != null ){
            	StringEntity se = new StringEntity(params);
                httpPost.setEntity(se);
            }
            for (String headerKey : headers.keySet()){
            	httpPost.setHeader(headerKey, headers.get(headerKey));
            }
            //c测试1
//            HttpResponse response = getHttpClient().execute(httpPost);
            //测试2
            CloseableHttpResponse response = httpClient.execute(httpPost);
            
//            CloseableHttpResponse response = getHttpClient().execute(httpPost);
            
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null){
                result = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        } catch (Exception e) {
        	log.error(e.getMessage(),e);
        	throw e;
        }
    }
  
    /** 
     * 获得HttpPost对象 
     *  
     * @param url 
     *            请求地址 
     * @param params 
     *            请求参数 
     * @param encode 
     *            编码方式 
     * @return HttpPost对象 
     */  
    public static HttpPost getHttpPost(String url, Map<String, String> params, String encode) {  
        HttpPost httpPost = new HttpPost(url);  
        if (params != null) {  
            List<NameValuePair> form = new ArrayList<NameValuePair>();  
            for (String name : params.keySet()) {  
                form.add(new BasicNameValuePair(name, params.get(name)));  
            }  
            try {  
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(form,  
                        encode);  
                httpPost.setEntity(entity);  
            } catch (UnsupportedEncodingException e) {  
                log.error("UrlEncodedFormEntity Error,encode=" + encode  
                        + ",form=" + form, e);  
            }  
        }  
        return httpPost;  
    } 
    public static void main(String []args) throws Exception{
        System.out.println("----------------------分割线-----------------------");
        Map<String, String> a = new HashMap<String, String>();
        //a.put("ip2", "114.83.50.76");
        String postData = doGet("http://ip.taobao.com/service/getIpInfo.php?ip=114.83.50.76",a);
        System.out.println(postData);
    }

	/**
	 * @Title: doPostHuiXuan
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param api
	 * @param @param adRequest
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @throws 
	 */
	public static String doPostHuiXuan(String url, JSONObject jsonObj) throws Exception {
		if(StringUtils.isBlank(url)){
            return null;
        }
		
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader("X-protocol-ver", "2.0");
			httpPost.setHeader("X-encryption", "none");

			//测试用的
			jsonObj = new JSONObject();
			
			jsonObj.put("aaid", "111");
			jsonObj.put("adh", 200);
			jsonObj.put("adid", "3986f2ea1de278b9");
			jsonObj.put("adunitid", "100053");
			jsonObj.put("adw", 640);
			jsonObj.put("appid", "172");
			jsonObj.put("appname", "rtbs sdk Demo");
			jsonObj.put("brk", 0);
			jsonObj.put("density", 240);
			jsonObj.put("devicetype", 1);
			jsonObj.put("duid", "");
			jsonObj.put("dvh", 960);
			jsonObj.put("dvw", 540);
			jsonObj.put("geo", "");
			jsonObj.put("idfa", "");
			jsonObj.put("imei", "359614044458144");
			jsonObj.put("ip", "101.81.241.190");
			jsonObj.put("isboot", 0);
			jsonObj.put("lan", "zh-CN");
			jsonObj.put("mac", "1C:B0:94:43:16:2B");
			jsonObj.put("mkt", 0);
			jsonObj.put("mkt_cat", "");
			jsonObj.put("mkt_sn", 0);
			jsonObj.put("mkt_tag", "");
			jsonObj.put("model", "HTC Sensation Z710e");
			jsonObj.put("net", "2");
			jsonObj.put("openudid", "mobile");
			jsonObj.put("operator", "");
			jsonObj.put("orientation", "0");
			jsonObj.put("os", "Android");
			jsonObj.put("osv", "2.3.5");
			jsonObj.put("pkgname", "com.iflytek.voiceadsdemo");
			jsonObj.put("ssid", "");
			jsonObj.put("ts", "1463130068");
			jsonObj.put("pkgname", "Mozilla/5.0 (Linux; U; Android 2.3.5; zh-cn; HTC Sensation Z710e Build/GRJ90) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");
			jsonObj.put("ssid", "HTC");
			jsonObj.put("ts", "2.0");
			
			if (jsonObj != null) {
				StringEntity se = new StringEntity(jsonObj.toString(), "utf-8");// 解决中文乱码问题
				se.setContentEncoding("UTF-8");
				se.setContentType("application/json");
				httpPost.setEntity(se);
			}
			
			CloseableHttpResponse response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
			    httpPost.abort();
			    throw new RuntimeException("HttpClient,error status code :" + statusCode);
			}

			HttpEntity entity = response.getEntity();
			String result = null;
			if (entity != null){
			    result = EntityUtils.toString(entity, "utf-8");
			}
			EntityUtils.consume(entity);
			response.close();
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
        	throw e;
		}
	}

	/**
	 * @throws Exception  
	 * @Title: doPostYouXiao 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param api
	 * @param @param adRequest
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @throws 
	 */
	public static String doPostYouXiao(String url, JSONObject jsonObj) throws Exception {
		if(StringUtils.isBlank(url)){
            return null;
        }
		
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader(HTTP.CONTENT_TYPE, "application/json;charset=utf-8");
			httpPost.setHeader("Accept", "application/json;charset=utf-8");

			if (jsonObj != null) {
				StringEntity se = new StringEntity(jsonObj.toString(), "utf-8");// 解决中文乱码问题
				se.setContentEncoding("UTF-8");
				se.setContentType("application/json");
				httpPost.setEntity(se);
			}
			
			CloseableHttpResponse response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200 && statusCode != 204) {
			    httpPost.abort();
			    throw new RuntimeException("HttpClient,error status code :" + statusCode);
			}	
			
			HttpEntity entity = response.getEntity();
			String result = null;
			if (entity != null){
			    result = EntityUtils.toString(entity, "utf-8");
			}
			EntityUtils.consume(entity);
			response.close();
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
        	throw e;
		}
	}

	/**
	 * @throws Exception  
	 * @Title: doPostLiMei 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param api
	 * @param @param adRequest
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @throws 
	 */
	public static String doPostLiMei(String url, JSONObject params) throws Exception {
		if(StringUtils.isBlank(url)){
            return null;
        }
		
		try {

			if (params != null && !params.isEmpty()) {
				List<NameValuePair> pairs = new ArrayList<NameValuePair>(
						params.size());
				for (String key : params.keySet()) {
					String value = params.getString(key);
					if (value != null) {
						pairs.add(new BasicNameValuePair(key, value));
					}
				}			
				url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, CHARSET));
			}
			
			System.out.println("力美的URL：" + url);
			
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader(HTTP.CONTENT_TYPE, "Application/x-www-form-urlencode");
			httpPost.setHeader("Forwarding", "x-forwarded-for");
			
			CloseableHttpResponse response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200 && statusCode != 204) {
			    httpPost.abort();
			    throw new RuntimeException("HttpClient,error status code :" + statusCode);
			}	
			
			HttpEntity entity = response.getEntity();
			String result = null;
			if (entity != null){
			    result = EntityUtils.toString(entity, "utf-8");
			}
			EntityUtils.consume(entity);
			response.close();
			return result;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
        	throw e;
		}
	}

	
     
}