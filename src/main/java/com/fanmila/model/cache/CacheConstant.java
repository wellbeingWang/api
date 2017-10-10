package com.fanmila.model.cache;


import java.util.ArrayList;
import java.util.List;

/**
 * 缓存常量
 * @author lscm
 *
 */
public final class CacheConstant {

	
	

	private CacheConstant() {
	}
	
	public static String CACHENAME = "redis";

	public static String CLT_UPDATE_INFO = "fml_clt_update_info_";
	public static String CLT_REASON_INFO = "fml_clt_reason_";
	public static String CLT_ENCYPT_JS = "fml_clt_encypt_js_";
	public static String CLT_BHO_JS = "fml_clt_bho_js_";
	public static String CLT_BK_JSON = "fml_clt_bk_json_";
	public static String CLT_SCJ_URL = "fml_clt_scj_url_";
	public static String CLT_SERVICE_OPEN = "fml_clt_service_open_";
	public static String CLT_CPS = "fml_cps_";
	public static String CLT_CHANNEL_CODE = "fml_channel_code_";
	public static String CPS_RULE_RANGE = "fml_cps_rule_range_";
	public static String CPS_URL_PATTERN = "fml_cps_url_pattern_";
	public static String CPS_GET_RULE = "fml_cps_get_rule_";
	public static String DHSS_GET_RULE = "fml_dhss_get_rule_";
	public static String CPS_GET_REGULAR_RULE = "fml_cps_regular_";
	public static String DHSS_GET_REGULAR_RULE = "fml_dhss_regular_";
	public static String  NEW_MATCH_RULE = "fml_new_match_rule_";
	public static String  SITE_MATCH_RULE = "fml_match_rule_site_";
	public static String PLG_POP_UP = "fml_plg_popup_";
	public static String CLT_POP_UP = "fml_clt_popup_";
	public static String CLT_BOTTOM_RIGHT_POP_UP = "fml_clt_bottom_right_popup_";
	public static String PLG_YHHD = "fml_plg_yhhd_";

	public static List<String> BROWSER_LIST = new ArrayList<String>(){{
		add("IE");
		add("Chrome");
		add("360Chrome");
		add("360se6");
		add("QQ");
		add("Sogou");
		add("2345Chrome");
		add("2345Explorer");
		add("UC");
		add("Baidu");
		add("Firefox");
		add("LieBao");
		add("TaoBao");
		add("Caimao");
		add("Coolnovo");
		add("TheWorld");
		add("Opera");
		add("Chromium");
		add("Maxthon");
		add("Safari");
	}};

	public static List<String> ANTIVIRUS_LIST = new ArrayList<String>(){{
		add("QQ管家");
		add("ksafesvc");
		add("zhudongfangyu");
		add("kxescore");
		add("360rp");
	}};



}
