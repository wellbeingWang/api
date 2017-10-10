package com.fanmila.handlers.taoke;

import com.alibaba.fastjson.JSONObject;
import com.fanmila.cms.model.cps.CPSURLHandlerContext;
import com.fanmila.cms.model.taoke.TaokeKeyDomain;
import com.fanmila.cms.model.taoke.TaokeTagRule;
import com.fanmila.model.ClickHandlerInfo;
import com.fanmila.model.ClickType;
import com.fanmila.model.URLHandlerContext;
import com.fanmila.service.impl.BaseRedisServiceImpl;
import com.fanmila.util.IPToolUtil;
import com.fanmila.util.analyzer.URLAnalyzerManager;
import com.fanmila.util.common.UUIDUtils;
import org.ansj.app.keyword.Keyword;
import org.ansj.app.summary.TagContent;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据库配置的淘宝客处理规则解析流程
 * @author zhenyuanzi
 *
 */
@Component
public class TaokeURLDbHandler extends TaokeURLHandler {
	
	@Autowired
	private URLAnalyzerManager urlAnalyzerManager;

	@Autowired
	private BaseRedisServiceImpl baseRedisService;

	private String preKey = "fml_taoke_all_rule";

	@SuppressWarnings("unchecked")
	@Override
	public String getRedirectURL(CPSURLHandlerContext context, ClickHandlerInfo handlerInfo) {
		TaokeKeyDomain keyDomain = new TaokeKeyDomain();
		String tuString = context.getRequestData().getString("tu");
		try {
			String durl =null;

			boolean hasin = context.getRequestData().getBoolean("hasin");
			context.setSsub(hasin? "login" : "click");
			
			String reffer = context.getCurl();
			Map<String, String> tagMap = urlAnalyzerManager.getAllTag(reffer);
			String commonReffer = tagMap.get("domain");
			//String tuString = context.getRequestData().getString("tu");

			List<String> cmurlList = (List<String>)baseRedisService.hgetObject(preKey, "commonUrl");
			List<String> firstUrlList = (List<String>)baseRedisService.hgetObject(preKey, "firstUrl");//第一步的导航站地址
			//System.out.println(reffer+"  "+commonReffer+" "+hasin+"  "+cmurlList.contains(commonReffer));
			
			//refer 判断
			if(!cmurlList.contains(commonReffer)){
				logger.info("reffer is not in rules, the reffer is " +commonReffer);
				context.setSsub("01");
				return hasin?null:tuString;
			}
			
			
			String ipString = context.getRequestData().getString("ips");
			JSONObject jsonObject = IPToolUtil.getCityofIp(ipString);
			String city = jsonObject.getString("city");
			String cityEn = jsonObject.getString("cityEn");
			context.setSource(jsonObject.getString("countryEn")+", "+jsonObject.getString("regionEn")+", "+cityEn);
			//IP 判断
			if ((null != city && "杭州".equals(city))||(null!=cityEn && "Hangzhou".equals(cityEn))||(null != city && city.contains("阿里"))){
				logger.info("IP is not in rules, the ip is "+ipString);
				context.setSsub("02");
				return hasin?null:tuString;

			}
			
			String tag = context.getRequestData().getString("tag");
			//System.out.println(tag);
			
			keyDomain = findCpsRule(context);
			
			if(null == keyDomain || !keyDomain.isEnable() ){
				logger.info("未找到任何规则，返回原链接："+tuString);
				return hasin?null:tuString;
			}
			///分流取模
			int mod= UUIDUtils.moduuid(context.getUuid(), context.getUuid().length()-4, context.getUuid().length(), 100);
			if("1234567890".equals(context.getUuid())) mod =100;
            logger.info("*******************tag uuid:  = "+context.getUuid()+" "+mod+" **************************************************************");	
            context.setDocid(""+mod);
			//获取当前所有的分流策略 找到该区间的分流过程
			List<TaokeTagRule> taokeTagRule = keyDomain.getTaokeTagRuleList();
			
			for(TaokeTagRule tdr : taokeTagRule ){
				 int start_range = tdr.getTaokeAccount().getStart_range();
				 int end_range = tdr.getTaokeAccount().getEnd_range();
				 int taoStart_range = tdr.getStart_range();
				 int taoEnd_range = tdr.getEnd_range();
				 if(mod>=start_range && mod< end_range){
					 String shortTag = tdr.getTaokeAccount().getShort_tag();
					 String website = tdr.getTaokeAccount().getWebsite();
					 if(hasin){//若是加载过来的，即包含in标签
						 //System.out.println("commonReffer"+"  "+commonReffer+"  "+reffer);
						 if(firstUrlList.contains(commonReffer)){//若reffer为gw736，为第一步
							 durl = website+"?in&uuid="+context.getUuid();
							 logger.info("加载过程的第一步，返回："+durl);
							 context.setSsub("11");
						 }else if(tagMap.containsKey(shortTag)){//若reffer包含账号的出口tag，为第三步??????
							 logger.info("加载过程第三步，返回： null");
							 context.setSsub("13");
							 return null;
						 }else{//若reffer为360kuku.com?in&uuid=xxx为第二步
							 durl = website+"?"+shortTag;
							 logger.info("加载过程第二步，返回："+durl);
							 context.setSsub("12");
						 }
					 }else{//若是点击过来的，不包含in标签
						 int taoMod=new Random().nextInt(100);
						 if(start_range<100)
						     taoMod = (mod-start_range)*100/(end_range-start_range);
						 if(!context.getRequestData().getBoolean("hastag")){//若无tag，即是第一步，reffer为gw736.com等第一个导航站
							 if (taoMod>=taoStart_range && taoMod< taoEnd_range){//对于账号分流之外的，针对taobao.com和tmall.com的分流
								
								durl = website+"?tag="+tdr.getTag_uuid()+"&tt="+context.getRequestData().getString("tt")
										+"&tu="+context.getRequestData().getString("tu")+"&uuid="+context.getUuid();
								logger.info("点击过程第一步，返回："+durl);
								context.setSsub("21");

							 }else{
								 continue;
							 }
						 }else{
							 if(tagMap.containsKey(shortTag)){//????含有shortTag标签，标识第三步
								 if(tag.equals(tdr.getTag_uuid())){//若tag标签不为空，并通过验证，则返回CPS标签
									 if (taoMod>=taoStart_range && taoMod< taoEnd_range){
										 durl = tdr.getCps_url();
										 logger.info("点击过程第三步，返回："+durl);
										 context.setSsub("23");
									 }else{
										 continue;
									 }
								 }else{
									 continue;
								 }
							 }else{//无shortTag标签，第二步
								 durl = website+"?"+shortTag;
								 logger.info("点击过程第二步，返回："+durl);
								 context.setSsub("22");
							 }
						 }
					 }
					 
					 break;
				 }
			}
			if (null==durl){
				context.setSsub("00");
				durl = keyDomain.getTdomain();
			}
			//context.setDurl(durl);
			return durl;
			
			
		} catch (Exception e) {
			context.setSsub("-1");
			logger.error("程序出错！", e);
			String durl = StringUtils.isBlank(keyDomain.getTdomain())?tuString:keyDomain.getTdomain();
			//context.setDurl(durl);
			return durl;
		}
	}

	/**
	 * 获取规则；采用优先级判断
	 * 首先根据 关键字、域名来
	 * 然后根据关键字来
	 * 最后根据域名来
	 * @param context
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected TaokeKeyDomain findCpsRule(CPSURLHandlerContext context) throws UnsupportedEncodingException {
		String text = context.getRequestData().getString("tt").replace(" ", "");

		String turl = context.getRequestData().getString("tu");
		//String tag = context.getRequestData().getString("tag"); 
	    
		if("".equals(text)||null==text){
			text="NONE";
		}
		//类型+取全域名
		String tdomain = urlAnalyzerManager.getFullDomain(turl);//取全域名
		String mdomain = urlAnalyzerManager.getDomain(turl);//取主域名
		//System.out.println(text+"_"+tdomain);
		//////////首选根据 text和全域名 来确定详细的规则/start//////////////////////////
		String aaa = text+"_"+tdomain;
		TaokeKeyDomain cpdr = (TaokeKeyDomain)baseRedisService.hgetObject(preKey,text+"_"+tdomain);
		//取出text的关键字，根据数据库中记录的关键字来提取
		if(null == cpdr){
			Set<String> keywordsSet = (Set<String>)baseRedisService.hgetObject(preKey,"keyword");
			//List<String> keywordsList = TaokeDBTool.findFromCache("keyword", ArrayList.class);
			List<Keyword> keyWords = null;
			TagContent tw = null;
			try {
				keyWords = new ArrayList<Keyword>();
				tw = new TagContent("<begin>", "<end>");
			} catch (Exception e) {
				e.printStackTrace();
			}
			for(String keyword: keywordsSet){
				keyWords.add(new Keyword(keyword,1.0));//后期可以增加关键字权重配置属性
				
			}
			String keys = tw.tagContent(keyWords, text);
			Pattern pattern = Pattern.compile("<begin>(.*?)<end>");
			Matcher matcher = pattern.matcher(keys); 
			while(matcher.find()){
				logger.info("key word found: "+matcher.group(1));
				//根据关键字和全域名查找
				cpdr = (TaokeKeyDomain)baseRedisService.hgetObject(preKey,matcher.group(1)+"_"+tdomain);
				//根据关键字和主域名查找
				if(null==cpdr){
					cpdr = (TaokeKeyDomain)baseRedisService.hgetObject(preKey,matcher.group(1)+"_"+mdomain);
				}
				//根据关键字查找
				if(null==cpdr){
					cpdr = (TaokeKeyDomain)baseRedisService.hgetObject(preKey,matcher.group(1)+"_NONE");
				}
				if (null!=cpdr){
					break;
				}
			}
		}
		//根据全域名提取规则
		if(null == cpdr){
			cpdr = (TaokeKeyDomain)baseRedisService.hgetObject(preKey,"NONE_"+tdomain);

		}
		//根据主域名提取规则
		if(null == cpdr){
			cpdr = (TaokeKeyDomain)baseRedisService.hgetObject(preKey,"NONE_"+mdomain);
		}
		
		
		if(null == cpdr&&context.getRequestData().getBoolean("hasin")){
			cpdr = (TaokeKeyDomain)baseRedisService.hgetObject(preKey,"in_in");
		}
		if(null == cpdr){
			logger.warn("nothing rule found:"+text+", "+turl);
		}else{
			logger.info("rule found："+cpdr.toString());
		}

		return cpdr;
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

	public static void main(String[] args) {
		String refeString = "http://www.360kuku.com/tk2tb2&uuid=&sh=";
		
		
	}

	@Override
	protected ClickType getClickType() {
		// TODO Auto-generated method stub
		return ClickType.CPS;
	}

}
