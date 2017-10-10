package com.fanmila.handlers.taobao;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fanmila.handlers.CltApiURLHandler;
import com.fanmila.model.ClickHandlerInfo;
import com.fanmila.model.URLHandlerContext;
import com.fanmila.service.impl.BaseRedisServiceImpl;
import com.fanmila.util.analyzer.URLAnalyzerManager;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.TbkDgItemCouponGetRequest;
import com.taobao.api.request.TbkUatmFavoritesItemGetRequest;
import com.taobao.api.response.TbkDgItemCouponGetResponse;
import com.taobao.api.response.TbkUatmFavoritesItemGetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * @author zhenyuanzi
 *
 */
@Component
public class FavoritesItemHandler extends CltApiURLHandler{
	
	@Autowired
	private BaseRedisServiceImpl baseRedisService;// = ContextUtils.getBean(BaseRedisServiceImpl.class);

	
	@Autowired
	private URLAnalyzerManager urlAnalyzerManager;


	@Override
	public String getRedirectURL(URLHandlerContext context, ClickHandlerInfo handlerInfo) {
		

		String durl = "aaaaa";
		Long favoritesId = 7412494L;
		Long pageSize = 200l;
		Long pageNo = 1l;
		Long adzoneId = 115034663L;

		JSONObject responseInfo = new JSONObject();
		try {
			favoritesId = context.getRequestData().containsKey("favoritesId")?context.getRequestData().getLong("favoritesId"):favoritesId;
			pageSize = context.getRequestData().containsKey("pageSize")?context.getRequestData().getLong("pageSize"):pageSize;
			pageNo = context.getRequestData().containsKey("pageNo")?context.getRequestData().getLong("pageNo"):pageNo;
			adzoneId = context.getRequestData().containsKey("adzoneId")?context.getRequestData().getLong("adzoneId"):adzoneId;
			responseInfo = getTaobaoGoods(favoritesId, pageSize, pageNo, adzoneId);

			responseInfo.put("succ", 1);

		} catch (Exception e) {
			responseInfo.put("succ", 0);
			e.printStackTrace();
			return null;
		}finally{
			context.setInfo(responseInfo);
		}
		
		return durl;
	}


	/**
	 * taobao.tbk.uatm.favorites.item.get
	 * http://open.taobao.com/doc2/apiDetail.htm?spm=a219a.7395905.0.0.MELO98&apiId=26619
     * @return
     */
	public JSONObject getTaobaoGoods(Long favoritesId, Long pageSize, Long pageNo, Long adzoneId){
		String url = "http://gw.api.taobao.com/router/rest";
		String appkey = "23825674";
		String secret = "8b0b233f087cdb9ab9d0bf64bf1ce0f0";
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		TbkUatmFavoritesItemGetRequest req = new TbkUatmFavoritesItemGetRequest();
		req.setPlatform(1L);
		req.setPageSize(pageSize);
		req.setAdzoneId(adzoneId);//115034663L
		req.setUnid("3456");
		req.setFavoritesId(favoritesId);//7412494L
		req.setPageNo(pageNo);
		req.setFields("num_iid,title,coupon_click_url,reserve_price,click_url,coupon_info," +
				"zk_final_price,pict_url,event_start_time,event_end_time,category");
		TbkUatmFavoritesItemGetResponse rsp = null;
		try {
			rsp = client.execute(req);
		} catch (ApiException e) {
			e.printStackTrace();
		}
		JSONObject result = JSONObject.parseObject(rsp.getBody());
		JSONObject itemResponse = result.getJSONObject("tbk_uatm_favorites_item_get_response");
		JSONArray results = itemResponse.getJSONObject("results").getJSONArray("uatm_tbk_item");
		JSONArray goodsResultResponse = new JSONArray();
		JSONObject goodsResultResponseJson = new JSONObject();
		goodsResultResponseJson.put("total_results", itemResponse.getInteger("total_results"));
		for(Object good: results){
			JSONObject goods2 = (JSONObject)good;
			JSONObject goodsResult = new JSONObject();
			goodsResult.put("Org_Price", goods2.get("zk_final_price"));
			goodsResult.put("Pic", goods2.get("pict_url"));
			goodsResult.put("reserve_price", goods2.get("reserve_price"));
			goodsResult.put("Title", goods2.get("title"));
			goodsResult.put("event_start_time", goods2.get("event_start_time"));
			goodsResult.put("event_end_time", goods2.get("event_end_time"));
			if(goods2.containsKey("coupon_click_url")){
				goodsResult.put("ali_click", goods2.get("coupon_click_url"));
				goodsResult.put("coupon_info", goods2.get("coupon_info"));
			}else{
				goodsResult.put("ali_click", goods2.get("click_url"));
			}

			goodsResultResponse.add(goodsResult);
		}
		goodsResultResponseJson.put("result", goodsResultResponse);

		return goodsResultResponseJson;

	}

	

	
	public static void main(String[] args) {
		String url = "http://gw.api.taobao.com/router/rest";
		String appkey = "23825674";
		String secret = "8b0b233f087cdb9ab9d0bf64bf1ce0f0";
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		TbkDgItemCouponGetRequest  req = new TbkDgItemCouponGetRequest();
		req.setAdzoneId(115034663L);
		req.setPlatform(1L);
		req.setCat("16,18");
		req.setPageSize(100L);
		req.setQ("女装");
		req.setPageNo(1L);
		TbkDgItemCouponGetResponse rsp = null;
		try {
			rsp = client.execute(req);
		} catch (ApiException e) {
			e.printStackTrace();
		}
		JSONObject result = JSONObject.parseObject(rsp.getBody());
		System.out.println(result);

		
	}



}
