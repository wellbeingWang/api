package com.fanmila.handlers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fanmila.model.ClickHandlerInfo;
import com.fanmila.model.URLHandlerContext;
import com.fanmila.service.impl.BaseRedisServiceImpl;
import com.fanmila.util.analyzer.URLAnalyzerManager;
import com.fanmila.util.http.HttpTookit;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author zhenyuanzi
 *
 */
@Component
public class PluginGetGoodsHandler extends CltApiURLHandler{
	
	@Autowired
	private BaseRedisServiceImpl baseRedisService;// = ContextUtils.getBean(BaseRedisServiceImpl.class);

	
	@Autowired
	private URLAnalyzerManager urlAnalyzerManager;

	private String goodsKey  = "tb_dtk_top100_";
	
	@Override
	public String getRedirectURL(URLHandlerContext context, ClickHandlerInfo handlerInfo) {
		

		String durl = "aaaaa";
		JSONObject responseInfo = new JSONObject();
		try {
			//版本信息
			String cid = context.getRequestData().containsKey("cid")?context.getRequestData().getString("cid"):"";
			String goodFromDb =baseRedisService.getString(goodsKey + cid);
			JSONArray taobaoGoods = new JSONArray();
			String d = goodsKey + cid;

			if(StringUtils.isBlank(goodFromDb)){
				taobaoGoods = getTaobaoGoods(cid);
				if(!taobaoGoods.isEmpty())
				    baseRedisService.setValueWithExpire(goodsKey + cid, 3600*12, taobaoGoods.toJSONString());
			}else{
				taobaoGoods =JSONArray.parseArray(goodFromDb);
			}
			/*JSONObject taobaoGoodsJson = JSON.parseObject(taobaoGoods);
			JSONArray goodsResultFromApi = taobaoGoodsJson.getJSONArray("result");
			JSONArray goodsResultResponse = new JSONArray();
			for(Object good: goodsResultFromApi){
				JSONObject goods = (JSONObject)good;
				String cidFromApi = goods.getString("Cid");
				if(cidFromApi.equals(cid)){
					goodsResultResponse.add(goods);
				}
			}*/
			responseInfo.put("succ", 1);
			responseInfo.put("result", taobaoGoods);

		} catch (Exception e) {
			responseInfo.put("succ", 0);
			//responseInfo.put("isSuccess", false);
			//responseInfo.put("msg", "Error!");
			e.printStackTrace();
			return null;
		}finally{
			context.setInfo(responseInfo);
		}
		
		return durl;
	}
	
	/**
	 * 
	* @Title: isHave 
	* @Description: 判断某字符串数组中是否包含指定字符串
	* @param @param strs
	* @param @param s
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws
	 */
/*	public static boolean isHave(String[] strs, String s) {	
		if(StringUtils.isBlank(strs[0])&&strs.length==1) return true;
		for(int i=0;i<strs.length;i++) {		
			//循环查找字符串数组中的每个字符串中是否包含所有查找的内容		
			if(strs[i].equals(s)) {
				return true;
			}
		}
		return false;
     }
	*/

	/**
	 * http://api.dataoke.com/index.php?r=goodsLink/www&type=www_quan&appkey=8ywzjhx97j&v=2
	 * @param cid
     * @return
     */
	public JSONArray getTaobaoGoods(String cid){
		String url = "http://api.dataoke.com/index.php";
		Map<String, String> params = new HashMap<String,String>();
		params.put("r","goodsLink/www");
		params.put("type","www_quan");
		params.put("appkey","0vp4gbhi34");
		params.put("v","2");
		String goods = HttpTookit.doGet(url, params);
		JSONObject taobaoGoodsJson = JSON.parseObject(goods);
		taobaoGoodsJson = taobaoGoodsJson.getJSONObject("data");
		JSONArray goodsResultFromApi = taobaoGoodsJson.getJSONArray("result");
		JSONArray goodsResultResponse = new JSONArray();
		for(Object good: goodsResultFromApi){
			JSONObject goods2 = (JSONObject)good;
			String cidFromApi = goods2.getString("Cid");
			String aliClick = goods2.getString("ali_click");
			goods2.remove("Commission_jihua");
			goods2.remove("Commission_queqiao");
			goods2.remove("D_title");
			goods2.remove("Dsr");
			goods2.remove("Introduce");
			goods2.remove("IsTmall");
			goods2.remove("Jihua_link");
			goods2.remove("Quan_condition");
			goods2.remove("Quan_id");
			goods2.remove("Quan_m_link");
			goods2.remove("Quan_receive");
			goods2.remove("Quan_surplus");
			goods2.remove("Sales_num");
			goods2.remove("SellerID");
			goods2.remove("Quan_time");
			if(StringUtils.isBlank(cid))
				goodsResultResponse.add(goods2);
			if(cidFromApi.equals(cid)){
				goodsResultResponse.add(goods2);
			}
		}
		return goodsResultResponse;

	}

	

	
	public static void main(String[] args) {
		new PluginGetGoodsHandler().getTaobaoGoods("1");

		
	}



}
