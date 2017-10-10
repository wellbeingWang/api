package com.fanmila.util.url;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public enum UrlRefiner {
	
	Taobao("taobao.com", "(http[s]?://[a-z]+\\.taobao.com/([a-z]+/)*[a-z]+\\.htm\\?).*?((?<=\\?|&)(item_)?id=\\d+)"){
		protected String makeRefineUrl(Matcher m){
			return m.group(1)+m.group(3);
		}
		
	},
	
//	Taobaos("taobao.com", "(https://[a-z]+\\.taobao.com/([a-z]+/)*[a-z]+\\.htm\\?).*?((?<=\\?|&)(item_)?id=\\d+)"){
//		protected String makeRefineUrl(Matcher m){
//			return m.group(1)+m.group(3);
//		}
//		
//	},
	
	Tmall("tmall.com", "(?<=\\?|&)id=\\d+"){
		
		protected String makeRefineUrl(Matcher m){
			return "http://detail.tmall.com/item.htm?"+m.group();
		}
		
	},
	
	Tmalls("tmall.com", "(?<=\\?|&)id=\\d+"){
		
		protected String makeRefineUrl(Matcher m){
			return "https://detail.tmall.com/item.htm?"+m.group();
		}
		
	},
	
	TmallHk("tmall.hk", "(?<=\\?|&)id=\\d+"){

		protected String makeRefineUrl(Matcher m){
			return "http://detail.tmall.hk/hk/item.htm?"+m.group();
		}

	},
	
	Amazon("amazon.cn", "((dp|gp/product)/[0-9a-zA-Z]+)|(mn/detailApp(/)?\\?asin=[0-9A-Za-z]+)"){
		protected String makeRefineUrl(Matcher m){
			return "http://www.amazon.cn/"+m.group();
		}
	},
	
	AmazonCom("amazon.com", "((dp|gp/product)/[0-9a-zA-Z]+)|(mn/detailApp\\?asin=[0-9A-Za-z]+)"){
		
		protected String makeRefineUrl(Matcher m){
			return "http://www.amazon.com/"+m.group();
		}
		
	}, 
	
	AmazonJp("amazon.co.jp", "((dp|gp/product)/[0-9a-zA-Z]+)|(mn/detailApp\\?asin=[0-9A-Za-z]+)"){
		
		protected String makeRefineUrl(Matcher m){
			return "http://www.amazon.co.jp/"+m.group();
		}
		
	},
	
	Gome("gome.com.cn", "([0-9A-Za-z]+-)?[0-9A-Za-z]+\\.html"){
		
		protected String makeRefineUrl(Matcher m){
			return "http://item.gome.com.cn/"+m.group();
		}
		
	},
	Suning("suning.com", "([0-9A-Za-z]+-)?[0-9A-Za-z]+\\.html"){
		protected String makeRefineUrl(Matcher m){
			return "http://product.suning.com/"+m.group();
		}
	},
	
	// 当当网
	Dangdang("dangdang.com", "http://(product|m)\\.dangdang\\.com/(\\d+\\.html|product.aspx\\?product_id=\\d+|product.php\\?pid=\\d+)"),
	
	// 京东
	Jd("jd.com", "http://.+?\\.jd\\.com/((product/)?([a-zA-Z]+-)?\\d+\\.html|act/[0-9A-Za-z]+\\.html)"),
	
	//Suning("suning.com", "http://.+?\\.suning\\.com/((\\d+/)?[0-9a-z]+(-\\d+)?\\.htm(l)?|rps-web/rp/showActivity_\\d+.htm(l)?|(emall|\\d+)/[a-z]+_(\\d+_)+(_)?\\.html)"),
	
	Yixun("yixun.com", "http://[a-z]+.yixun.com/((item-)|event/)[0-9a-zA-Z]+\\.html"),
	
	Yhd("yhd.com", "http://[a-z]+\\.yhd.com/(item|detail)/((lp/)?\\d+_)?\\d+/?"),
	
	// 博库网
	Bookuu("bookuu.com", "http://detail.bookuu.com/\\d+\\.html"),
		
	// 淘书网
	Taoshu("taoshu.com", "http://www.taoshu.com/\\d+\\.html"),
	
	// 文轩网
	Winxuan("winxuan.com", "http://www.winxuan.com/product/\\d+"),
	
	// V+
	Vjia("vjia.com", "http://item.vjia.com/\\d+\\.html"),
	
	// 麦网
	M18("m18.com", "http://list.m18.com/(item/.+?/|g/)\\d+"),
	
	// 蘑菇街
	Mogujie("mogujie.com", "http://shop.mogujie.com/detail/[0-9A-Za-z]+"),
	
	// 美丽说
	Meilishuo("meilishuo.com", "http://www.meilishuo.com/share/item/\\d+"), 
	
	// 丽芙网
	Lifevc("lifevc.com", "http://www.lifevc.com/item/\\d+"),
	
	//拍拍网
	Paipai("paipai.com","http://\\w*\\.paipai\\.com/.*"),
	
	//----------------- 价格爬虫暂时不支持 ----------------
	
	Vancl("vancl.com", "http://item.vancl.com/\\d+\\.html"),
//	
//	// 唯品会
	Vip("vip.com", "http://www.vip.com/detail-\\d+-\\d+\\.html"),
//	
//	
//	// 第五大道
	Fiveux("51ux.com", "http://www.5lux.com/goods\\.php\\?id=\\d+"),
//	
//	// 韩都衣舍
	Handu("handu.com", "http://www.handu.com/goods-\\d+\\.html"),
//	
//	// 为为网
	Homevv("homevv.com", "http://www.homevv.com/vvshopProductView/pid-\\d+\\.jhtml"),
//	
//	// 酒仙网
	Jiuxian("jiuxian.com", "http://www.jiuxian.com/goods-\\d+\\.html"),
//	
//	// 聚美优品
	Jumei("jumei.com", "http://(mall|bj).jumei.com/(product_\\d+|i/deal/bj\\d+p\\d+)\\.html"),
//	
//	// 乐蜂网
	Lefeng("lefeng.com", "http://[a-z]+\\.lefeng.com/((\\d+/)?product/|sh/\\d+/\\d+_)\\d+\\.html"),
//	
//	// 梦芭莎
	Moobasa("moonbasa.com", "http://[a-z]+.moonbasa.com/(p|s)-\\d+\\.html"),
//	
//	// 银泰
	Yintai("yintai.com", "http://item.yintai.com/\\d+-\\d+-[0-9a-zA-Z]+.html"),
	
	
	;
	
	//----------------------
	private static Logger LOG = Logger.getLogger(UrlRefiner.class);
	private String domain;
	private Pattern refPat;
	
	private UrlRefiner(String domain, String pattern){
		this.domain = domain;
		this.refPat = Pattern.compile(pattern);
	}
	
	public String getDomain(){
		return this.domain;
	}
	
	/**
	 * 去除原始url里的杂质，得到纯净的url；
	 * 
	 * @param rawUrl
	 * @return
	 */
	public String refine(String url){
		Matcher m = refPat.matcher(url);

		if (m.find()) {
			return makeRefineUrl(m);
		} else {
//			LOG.warn("不是标准的url, rawUrl[" + url + "]");
		}

		return url;
	}
	
	protected String makeRefineUrl(Matcher m){
		return m.group();
	}

}
