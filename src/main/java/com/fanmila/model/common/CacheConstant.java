package com.fanmila.model.common;


/**
 * 缓存常量
 * @author lscm
 *
 */
public final class CacheConstant {

	//
	public static final String ZHIKE_AD_INFO_POSITION = "dmp_zhike_ad_info_position_";
	
	public static final String ZHIKE_AD_POSITION_TEMPLATE = "dmp_zhike_ad_position_template_";
	
	//获取某一个广告位下的所有广告代码
	public static final String GET_AD_BY_POSITION = "dmp_get_ad_by_position_";
	
	
	//
	public static final String OMS_AUTH_BY_CHANNEL = "oms_outh_by_channel";
	
	public static final String OMS_AUTH_BY_SITE = "oms_outh_by_site";
	
	public static final String OMS_LVM = "oms_lvm";
	
	public static final String OMS_LV1_DAOHANG_CONFIG = "oms_lv1_daohang_config_";
	//某个投放广告总的实时数据
	public static final String DMP_REALTIME = "dmp_realtime_";
	//某个广告当天实时数据
	public static final String DMP_TODAY_REALTIME = "dmp_today_";
	
	//sf1查询商品缓存
	public static final String DMP_SF1_GOODS = "dmp_sf1_goods_";
	
	public static final String DMP_DIM_PARENT_PRODUCT = "dmp_zhike_dim_parent_";
	
    
	public static final String DMP_UNION_AD_TEMPLATE = "dmp_zhike_union_ad_template_";
	
	//用户登录前缀码
	public static final String USER_PREFIX = "user_prefix_";
	
	public static final String RBAC_ROLE = "rbac_role_";
	

	private CacheConstant() {
	}
	
	public static String CACHENAME = "redis";

}
