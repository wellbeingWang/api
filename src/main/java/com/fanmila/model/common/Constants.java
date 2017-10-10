package com.fanmila.model.common;

import org.bson.types.ObjectId;



/**
 * 框架一来的常量
 * 
 * @author Larry
 */
public class Constants {
	
	/** 请求或发送数据时的默认编码,UTF-8 */
	public static final String DEFAULT_ENCODING="UTF-8";
	
	/** HTML类型文档的ContentType,text/html;charset=$default_encoding */
	public static final String HTML_CONTENT_TYPE="text/html;charset="+DEFAULT_ENCODING;
	                    
	public static final String XML_CONTENT_TYPE="text/xml;charset="+DEFAULT_ENCODING;

	// 是否成功标志
	public static final String APP_REQUEST_SUCCESSFUL = "succ";

	// 成功标志
	public static final int APP_REQUEST_SUCCESS = 1;

	// 失败标志
	public static final int APP_REQUEST_FAIL = 0;

	// 响应消息
	public static final String APP_REQUEST_MSG = "msg";
	
	//LOG
	public static final String LOG_NAME="mobile.fanmila.log";
	public static final String MSG_FORMAT_START="[";
	public static final String MSG_FORMAT_END="]";
	public static final String MSG_FORMAT_SPLIT=" ";
	
	 public final static String IMG_SERVER="http://cdn.bang5mai.com/b5m_img/";
	    public final static String IMG_ICON_SERVER="http://cdn.bang5mai.com/upload/plugin/siteicons/";
	    
	    public final static String SF1_FIELD_DOCID="DOCID";
	    public final static String SF1_FIELD_TITLE="TITLE";
	    public final static String SF1_FIELD_PICTURE="PICTURE";
	    public final static String SF1_FIELD_PRICE="PRICE";
	    public final static String SF1_FIELD_ORIGINALPICTURE="ORIGINALPICTURE";
	    public final static String SF1_FIELD_SOURCE="SOURCE";
	    public final static String SF1_FIELD_SUBSOURCE="SUBSOURCE";
	    public final static String SF1_FIELD_BRAND="BRAND";
	    public final static String SF1_FIELD_URL="URL";
	    public final static String SF1_FIELD_TARGETCATEGORY="TARGETCATEGORY";
	    public final static String SF1_FIELD_CATEGORY = "CATEGORY";
	    public final static String SF1_FIELD_UUID="UUID";
	    public final static String SF1_FIELD_COMMENTCOUNT="COMMENTCOUNT";
	    public final static String SF1_FIELD_ITEMCOUNT = "ITEMCOUNT";
	    public final static String SF1_FIELD_SALESAMOUNT = "SALESAMOUNT";
	    
	    //旅游字段 add by yuxiaolong 2013-11-04
	    public final static String SF1_TOUR_NAME = "Name";
	    public final static String SF1_TOUR_RECOMMENDATION = "Recommendation";
	    public final static String SF1_TOUR_IMGURLS = "ImgUrls";
	    public final static String SF1_TOUR_IMG = "Img";
	    public final static String SF1_TOUR_SOURCEIMGURLS = "SourceImgUrls";
	    public final static String SF1_TOUR_FORMCITY = "FromCity";
	    public final static String SF1_TOUR_TOCITY = "ToCity";
	    public final static String SF1_TOUR_TIMEPLAN = "TimePlan";
	    public final static String SF1_TOUR_MODIFIEDTIME = "ModifiedTime";
	    public final static String SF1_TOUR_ADDTIME = "AddTime";
	    public final static String SF1_TOUR_LISTTIME = "ListTime";
	    public final static String SF1_TOUR_DELISTTIME = "DelistTime";
	    public final static String SF1_TOUR_SOURCEPRICE = "SourcePrice";
	    public final static String SF1_TOUR_SALESPRICE = "SalesPrice";
	    public final static String SF1_TOUR_HOTELTRANSFORM = "HotelTransform";
	    public final static String SF1_TOUR_INITCLICK = "InitClick";
	    public final static String SF1_TOUR_TOTALCLICK = "TotalClick";
	    public final static String SF1_TOUR_SOURCE = "Source";
	    public final static String SF1_TOUR_YOUTYPE3 = "YouType3";
	    public final static String SF1_TOUR_YOUTYPE = "YouType";
	    public final static String SF1_TOUR_DOCID = "DOCID";
	    public final static String SF1_TOUR_UUID = "uuid";
	    public final static String SF1_TOUR_URL = "Url";
	    public final static String SF1_TOUR_TIME_PLAN_MAX = "TimePlanMax";
	    public final static String SF1_TOUR_ID = "ID";
	    
	    public final static String SF1_TUAN_MERCHANTAREA = "MerchantArea";
	    public final static String SF1_TUAN_CITY = "City";
	    public final static String SF1_TUAN_SALES = "Sales";
	    public final static String SF1_TUAN_PRICE = "Price";
	    public final static String SF1_TUAN_DOCID = "DOCID";
	    public final static String SF1_TUAN_TITLE = "Title";
	    public final static String SF1_TUAN_SOURCE = "Source";
	    public final static String SF1_TUAN_PICTURE = "OriginalPicture";
	    public final static String SF1_TUAN_URL = "Url";
	    public final static String SF1_TUAN_TIMEBEGIN = "TimeBegin";
	    public final static String SF1_TUAN_TIMEEND = "TimeEnd";
	    public final static String SF1_TUAN_CATEGORY = "Category";
	    
	    public final static int RSP_SUCCESS=1;
	    public final static int RSP_FAILE=0;
	    
	    public final static String RSP_MSG_SUCCESS="操作成功！";
	    
	    public final static String SEARCH_MODE="wand";//wand或suffix
	    public final static String DEF_SEARCH_MODE_THRESHOLD="0.8";//配合wand使用
	    /**
	     * 用户中心加密头字串
	     */
	    public static final String USER_CENTER_ENCODE_HEAD = "b5t";
	    public static final int USER_CENTER_APP_TYPE = 5;
	    
	    /**
	     * 服务器标识符
	     */
	    public static final String GEN_MACHINE_ID = String.valueOf(ObjectId.getGenMachineId());
	    
	    public static final String PROPERTY_METAQ_TOPIC_SPIDER = "metaq.topic.spider";
	    public static final String PROPERTY_METAQ_TOPIC_ALARM = "metaq.topic.alarm";
	    public static final String PROPERTY_METAQ_TOPIC_PRICE_USER_SUBSCRIBE = "metaq.topic.price.user.subscribe";
		public static final String PROPERTY_URL_TAOAPI = "url.taoapi";
		public static final String PROPERTY_URL_METAQ = "metaq.server.url";
		
		public static final String PROPERTY_URL_CENTER_ADD = "url.user.center.add";
		public static final String PROPERTY_URL_CENTER_DELETE = "url.user.center.delete";
		public static final String PROPERTY_URL_CENTER_LIST = "url.user.center.list";
	    
/*	    public static Object getProperty(String key){
	    	return CustomizedPropertyPlaceholderConfigurer.getContextProperty(key);
	    }
	    
	    public static <T> T getProperty(String key, Class<T> clazz){
	    	return clazz.cast( CustomizedPropertyPlaceholderConfigurer.getContextProperty(key));
	    }
*/
	    
	  

		/** application/x-json */
		public final static String JSON_CONTENT_TYPE = "application/x-json";

		//js广告回流的参数替换
		public static final String REQPARAM_SITECODE = "!!siteCode!!";
		public static final String REQPARAM_ADCODE = "!!adcode!!";
		public static final String REQPARAM_ADID = "!!adId!!";
		public static final String REQPARAM_TOKEN = "!!token!!";

	

}
