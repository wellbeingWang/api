package com.fanmila.handlers.cps.selfUnion.jd;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.request.cps.UnionServiceQueryImportOrdersRequest;
import com.jd.open.api.sdk.response.cps.UnionServiceQueryImportOrdersResponse;

/**
 * Created by wellbein on 2017/8/11.
 */
public class testGetCpsOrders {

    public String getRedirectURL() throws JdException, Exception{
        long UNIONID = 1000152763;

        String accessToken = "e46329f1-8be6-4a22-b65f-065c744dfb17";
        String appKey = "34F21799ED028991FBE37D26B73D2DBA";
        String appSecret = "b0beeffa8ca34210a57e9d20606c0539";
        String SERVER_URL = "https://api.jd.com/routerjson";
        String time = "2017081120";

        JdClient client1 = new DefaultJdClient(SERVER_URL, accessToken, appKey, appSecret);

        UnionServiceQueryImportOrdersRequest request1 = new UnionServiceQueryImportOrdersRequest();

        request1.setUnionId(UNIONID);
        request1.setTime(time);
        request1.setPageIndex(1);
        request1.setPageSize(100000);

        UnionServiceQueryImportOrdersResponse response2 = null;
        try{
            response2 = client1.execute(request1);
        } catch (JdException e1 )

        {
            e1.printStackTrace();
        }

        JSONObject re = (JSONObject) JSONObject.parse(response2.getQueryImportOrdersResult());
        JSONArray orderList = re.getJSONArray("data");
        //JSONObject re2 = new JSONObject();
        JSONArray skus2 = new JSONArray();
        for ( int i = 0; i < orderList.size(); i++){
            JSONObject jo = (JSONObject) orderList.get(i);
            JSONArray skus = jo.getJSONArray("skus");
            //JSONArray skus2 = new JSONArray();
            jo.put("dayHour",time);
            for(Object a : skus){
                JSONObject sku = (JSONObject)a;
                String u_id = sku.getString("subUnionId");
                sku.put("orderId",jo.getString("orderId"));
                sku.put("dayHour",time);
                String channel = "";
                String ssub = "";
                String product_id = "";
                if (u_id.length() > 10) {
                    channel = u_id.substring(0, 3);
                    ssub = u_id.substring(5, 6);
                    product_id = u_id.substring(6, 8);
                }
                sku.put("cps_product_id", product_id);
                sku.put("cps_channel", channel);
                sku.put("cps_ssub", ssub);
                skus2.add(sku);

            }
            jo.remove("skus");
            //jo.put("skus",skus2);

        }
        return orderList.toJSONString();
    }

    public static void main(String[] args) throws Exception {
        testGetCpsOrders a = new testGetCpsOrders();
        a.getRedirectURL();
    }

}

