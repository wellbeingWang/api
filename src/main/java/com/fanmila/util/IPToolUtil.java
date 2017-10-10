package com.fanmila.util;

import com.alibaba.fastjson.JSONObject;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.AddressNotFoundException;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.InetAddress;

public class IPToolUtil {
	private static Logger log = LoggerFactory.getLogger(FileUtil.class);
    
	@SuppressWarnings("finally")
	public static boolean downIPFile(){
		
    	boolean success = false;
    	String content = "";
    	try {
			if(FileUtil.downloadFile("./GeoLite222-City.mmdb.gz2", "http://geolite.maxmind.com/download/geoip/database/GeoLite222-City.mmdb.gz2")){
				content = "IP 文件下载成功.....,";
				if(FileUtil.doUncompressFileofGZ("GeoLite222-City.mmdb.gz2")){
					content += "IP文件解压成功。。。";
					success = true;
					if(FileUtil.copyFile("GeoLite222-City.mmdb", "GeoLite3-City2.mmdb")){
						content += "IP文件复制成功。。。";
					}else{
						content += "IP文件复制失败？？？";
					}
				}else{
					content += "IP文件解压失败？？？";
					success = false;
				}
			}else{
				content = "IP文件下载失败？？？？";
				success = false;
			}
		}catch (Exception e) {
			content= "IP文件下载失败???  "+e;
			success = false;
		}finally{
			try {
				mailUtil.sendMail(success, "IP文件下载", content);
				//System.out.println(content);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				log.error(content);
		    	return success;
			}
		}
    }
    
    
    public static JSONObject getCityofIp(String ipAdress,String ipDBInfo) {
    	JSONObject jsonObject = new JSONObject();
        try {
        	File database = new File(ipDBInfo);
        	if(!database.exists()){
        		jsonObject.put("ip", ipAdress);
            	jsonObject.put("code", 404);
        	}
        	DatabaseReader reader = new DatabaseReader.Builder(database).build();
        	InetAddress ipAddress = InetAddress.getByName(ipAdress);
        	CityResponse response = reader.city(ipAddress);

        	jsonObject.put("ip", ipAdress);
        	Country country = response.getCountry();
    		//System.out.println(country.getIsoCode());            // 'US'
    		//System.out.println(country.getName());               // 'United States'
    		//System.out.println(country.getNames().get("zh-CN")); // '美国'
        	String contryString = country.getNames().get("zh-CN");
        	jsonObject.put("country", contryString);
        	jsonObject.put("countryEn", country.getName());

    		Subdivision subdivision = response.getMostSpecificSubdivision();
    		//System.out.println(subdivision.getName());    // 'Minnesota'
    		//System.out.println(subdivision.getIsoCode()); // 'MN'
    		String region = subdivision.getNames().get("zh-CN");
    		jsonObject.put("region", region);
    		jsonObject.put("regionEn", subdivision.getName());
    		

    		City city = response.getCity();
    		//System.out.println(city.getNames().get("zh-CN")); // 'Minneapolis'
    		String cityString = city.getNames().get("zh-CN");
    		jsonObject.put("city", cityString);
    		jsonObject.put("cityEn", city.getName());
    		

    		Postal postal = response.getPostal();
    		//System.out.println(postal.getCode()); // '55455'
    		String postalString = postal.getCode();
    		jsonObject.put("postal", postalString);

    		Location location = response.getLocation();
    		//System.out.println(location.getLatitude());  // 44.9733
    		//System.out.println(location.getLongitude()); // -93.2323
    		jsonObject.put("latitude", location.getLatitude());
    		jsonObject.put("longitude", location.getLongitude());
    		
    		jsonObject.put("code", 200);
    		
    		//return cityMap;

            
        }catch(AddressNotFoundException e){
        	jsonObject.put("ip", ipAdress);
        	jsonObject.put("code", 400);
        }catch(Exception e){
        	jsonObject.put("ip", ipAdress);
        	jsonObject.put("code", 404);
            e.printStackTrace();
        }
		return jsonObject;
    }
    

	public static JSONObject getCityofIp(String ipAdress){
		JSONObject jsonObject = new JSONObject();
		jsonObject = getCityofIp(ipAdress, FileUtil.getPathOfResource("GeoLite2-City.mmdb"));
    	if (404 == jsonObject.getInteger("code")){
    		log.error("get IP from 2 bak file");
    		jsonObject = getCityofIp(ipAdress, FileUtil.getPathOfResource("GeoLite222-City3.mmdb"));
    	}
    	if (404 == jsonObject.getInteger("code")){
    		log.error("get IP from 3 bak file");
    		jsonObject = getCityofIp(ipAdress, "GeoLite222-City.mmdb");
    	}
    	if (404 == jsonObject.getInteger("code")){
    		log.error("get IP from 4 bak file");
    		jsonObject = getCityofIp(ipAdress, "GeoLite222-City3.mmdb");
    	}
    	if  (404 == jsonObject.getInteger("code")){
		    log.error("get nothing from all file");
    	}
    	return jsonObject;
    	
    }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//downIPFile();ste
		System.out.println(Math.log10(10l));

		/*JSONObject jsonObject = getCityofIp("116.226.250.76");
		System.out.println(jsonObject);
		String city = jsonObject.getString("city");
		String cityEn = jsonObject.getString("cityEn");
		if (( "杭州".equals(city))||(null!=cityEn && "Hangzhou".equals(cityEn))||(null != city && city.contains("阿里"))){
			System.out.println("sss");
		}*/

	}

}
