package com.fanmila.util.analyzer;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.lang.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLAnalyzerManager {

	private ArrayListMultimap<String, String> domainSources = ArrayListMultimap
			.create();
	private Multimap<String, IURLAnalyzer> domainFilters = HashMultimap
			.create();
	private Pattern domainPattern = Pattern
			.compile("[a-zA-Z0-9-]+\\.(?:co\\.kr|net\\.cn|com\\.hk|com\\.cn|com|cn|net|org|cc|tv|hk|me|co\\.jp)(?=/|\\?|$|#)");
	private Pattern fullDomainPattern = Pattern
			.compile("[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+\\.?");

	private Multimap<String, String> firstLevelDomainMap = HashMultimap
			.create();

	{
		domainFilters.put("360buy.com", new PatternURLAnalyzer(
				"^(http://www\\.360buy\\.com/product/(\\d+)\\.html).*", "$1",
				"$2"));
		domainFilters.put("360buy.com", new PatternURLAnalyzer(
				"^(http://[^/]+\\.360buy\\.com/(\\d+)\\.html).*", "$1", "$2"));
		domainSources.put("360buy.com", "京东商城");
		domainFilters
				.put("jd.com", new PatternURLAnalyzer(
						"^(http://www\\.jd\\.com/product/(\\d+)\\.html).*",
						"$1", "$2"));
		domainFilters.put("jd.com", new PatternURLAnalyzer(
				"^(http://[^/]+\\.jd\\.com/(\\d+)\\.html).*", "$1", "$2"));
		domainSources.put("jd.com", "京东商城");

		// 亚马逊
		domainFilters.put("amazon.cn", new PatternURLAnalyzer(
				".*/gp/product/([0-9A-Z]+).*",
				"http://www.amazon.cn/gp/product/$1/", "$1"));
		domainFilters.put("amazon.cn", new PatternURLAnalyzer(
				".*/dp/([0-9A-Z]+).*", "http://www.amazon.cn/gp/product/$1/",
				"$1"));
		domainFilters.put("amazon.cn", new PatternURLAnalyzer(
				".*[?&]asin=([0-9A-Z]+).*",
				"http://www.amazon.cn/gp/product/$1/", "$1"));
		domainSources.put("amazon.cn", "亚马逊");

		// 当当
		domainFilters.put("dangdang.com", new PatternURLAnalyzer(
				"^(http://product\\.dangdang\\.com/(\\d+)\\.html).*", "$1",
				"$2"));
		domainFilters
				.put("dangdang.com",
						new PatternURLAnalyzer(
								".*[?&]product_id=(\\d+).*",
								"http://product.dangdang.com/product.aspx?product_id=$1",
								"$1"));
		domainSources.put("dangdang.com", "当当网");

		// 苏宁易购
		domainFilters.put("suning.com", new PatternURLAnalyzer(
				"^(http://www\\.suning\\.com/emall/prd([0-9_\\-]+)\\.html).*",
				"$1", "$2"));
		domainSources.put("suning.com", "苏宁易购");

		// 一号店
		domainFilters.put("yihaodian.com", new PatternURLAnalyzer(
				"^(http://www\\.yihaodian\\.com/product/([0-9_]+).*)", "$1",
				"$2"));
		domainSources.put("yihaodian.com", "一号店");
		domainFilters.put("yhd.com", new PatternURLAnalyzer(
				"^(http://www\\.yhd\\.com/product/([0-9_]+).*)", "$1", "$2"));
		domainSources.put("yhd.com", "一号店");

		// 国美
		domainFilters
				.put("gome.com.cn",
						new PatternURLAnalyzer(
								"^(http://www\\.gome\\.com\\.cn/\\w+/\\w+/jump/product/(\\d+)\\.html).*",
								"$1", "$2"));
		// domainFilters.put("gome.com.cn", new
		// PatternURLAnalyzer("^(http://www\\.gome\\.com\\.cn/product/(\\d+)\\.html).*",
		// "$1", "$2"));
		domainFilters.put("gome.com.cn", new PatternURLAnalyzer(
				"^(http://www\\.gome\\.com\\.cn/product/([^.]+)\\.html).*",
				"$1", "$2"));
		domainSources.put("gome.com.cn", "国美");
		domainSources.put("gome.com.cn", "国美电器官网");

		// 天猫
		domainFilters.put("tmall.com", new PatternURLAnalyzer(
				".*/venus/spu_detail\\.htm\\?(?:.*&)?default_item_id=(\\d+).*",
				"http://detail.tmall.com/item.htm?id=$1", "$1"));
		domainFilters.put("tmall.com", new PatternURLAnalyzer(
				".*/item\\.htm\\?(?:.*&)?id=(\\d+).*$",
				"http://detail.tmall.com/item.htm?id=$1", "$1"));
		domainSources.put("tmall.com", "天猫");
		domainSources.put("tmall.com", "淘宝网");

		// 淘宝
		domainFilters.put("taobao.com", new PatternURLAnalyzer(
				".*/item\\.htm\\?(?:.+&)?id=(\\d+).*",
				"http://item.taobao.com/item.htm?id=$1", "$1"));
		domainSources.put("taobao.com", "淘宝网");
		domainSources.put("taobao.com", "天猫");

		// 库吧
		domainFilters.put("coo8.com", new PatternURLAnalyzer(
				"^(http://www\\.coo8\\.com/product/(\\d+)\\.html).*", "$1",
				"$2"));
		domainSources.put("coo8.com", "库巴购物网官网");
		domainSources.put("coo8.com", "库巴");

		// 麦考林
		domainFilters.put("m18.com", new PatternURLAnalyzer(
				"^(http://product\\.m18\\.com/p-([0-9A-z]+)\\.htm).*", "$1",
				"$2"));
		domainSources.put("m18.com", "麦考林");

		// 梦芭莎
		domainFilters
				.put("moonbasa.com", new PatternURLAnalyzer(
						"^(http://www\\.moonbasa\\.com/p-(\\d+)\\.html).*",
						"$1", "$2"));

		// 易讯
		domainFilters.put("yixun.com",
				new PatternURLAnalyzer(
						"^(http://item\\.yixun\\.com/item-(\\d+)\\.html).*",
						"$1", "$2"));
		domainSources.put("yixun.com", "易迅网");

		// 凡客成品
		domainFilters.put("vancl.com", new PatternURLAnalyzer(
				"^(http://item\\.vancl\\.com/(\\d+)\\.html).*", "$1", "$2"));
		domainSources.put("vancl.com", "凡客诚品商城官网");
		domainSources.put("vancl.com", "凡客诚品");

		// 新蛋
		domainFilters.put("newegg.com.cn", new PatternURLAnalyzer(
				"^(http://(?:.+?)/Product/(.+?)\\.htm).*", "$1", "$2"));
		domainSources.put("newegg.com.cn", "新蛋中国官网");

		// 乐淘
		domainFilters
				.put("letao.com",
						new PatternURLAnalyzer(
								"^(http://www\\.letao\\.com/(?:.*/)*?shoe(?:.*-)*?(\\d+).*?)(?:\\?.*)?",
								"$1", "$2"));
		domainSources.put("letao.com", "乐淘");

		// V+
		domainFilters.put("vjia.com", new PatternURLAnalyzer(
				"^(http://item\\.vjia\\.com/(\\d+).html).*", "$1", "$2"));
		domainSources.put("vjia.com", "V+");

		domainSources.put("mixr.cn", "mixr");
		domainSources.put("mixr.cn", "Mixr");
		domainSources.put("mixr.cn", "MIXR");

		domainFilters.put("wanggou.com", new PatternURLAnalyzer(
				"^(http://item\\.wanggou\\.com/([0-9a-zA-Z]+)).*", "$1", "$2"));
		domainFilters.put("wanggou.com", new PatternURLAnalyzer(
				"^(http://item\\.wanggou\\.com/search/0/([0-9a-zA-Z]+)).*",
				"$1", "$2"));
		domainSources.put("wanggou.com", "网购网");

		domainFilters.put("paipai.com", new PatternURLAnalyzer(
				"^(http://item\\.paipai\\.com/([0-9a-zA-Z]+)).*", "$1", "$2"));
		domainFilters.put("paipai.com", new PatternURLAnalyzer(
				"^(http://item\\.paipai\\.com/search/0/([0-9a-zA-Z]+)).*",
				"$1", "$2"));
		domainFilters.put("paipai.com", new PatternURLAnalyzer(
				"^(http://auction1\\.paipai\\.com/([0-9a-zA-Z]+)).*", "$1",
				"$2"));
		domainSources.put("paipai.com", "拍拍网");

		domainFilters.put("iqiyi.com", new PatternURLAnalyzer(
				"^(http://vip\\.iqiyi\\.com.*)\\?.*", "$1", "$2"));

		domainFilters.put("ctrip.com",
						new PatternURLAnalyzer(
								"^(http://vacations\\.ctrip\\.com/[a-zA-Z]+/([0-9a-zA-Z]+)\\.html).*",
								"$1", "$2"));
		domainSources.put("ctrip.com", "携程网");

		domainFilters.put("mogujie.com", new PatternURLAnalyzer(
				"(^http://shop\\.mogujie\\.com/detail/([0-9a-zA-Z]+))\\?.*",
				"$1", "$2"));
		domainSources.put("mogujie.com", "蘑菇街");
		
		//美丽说
		domainFilters.put("meilishuo.com", new PatternURLAnalyzer("^(http://www.meilishuo.com/share/item/(\\d+)\\?).*", "$1", "$2"));
		domainFilters.put("meilishuo.com", new PatternURLAnalyzer("^(http://www.meilishuo.com/share/item/(\\d+))", "$1", "$2"));
		domainSources.put("meilishuo.com", "美丽说");
		
		//洋码头
		domainSources.put("ymatou.com", "洋码头");
		domainSources.put("vip.com", "唯品会");

		// 特殊二级域名映射
		firstLevelDomainMap.put("qq.com", "gaopeng.qq.com");
		firstLevelDomainMap.put("qq.com", "etg.qq.com");
		firstLevelDomainMap.put("58.com", "t.58.com");
		firstLevelDomainMap.put("tmall.com", "pba.tmall.com");
		firstLevelDomainMap.put("tmall.com", "qianda.tmall.com");
		firstLevelDomainMap.put("tmall.com", "koolaburra.tmall.com,");
		firstLevelDomainMap.put("tmall.com", "bomllunite.tmall.com");
		firstLevelDomainMap.put("tmall.com", "tjfs.tmall.com");
		firstLevelDomainMap.put("tmall.com", "proya.tmall.com");
		firstLevelDomainMap.put("tmall.com", "othermix.tmall.com");
		firstLevelDomainMap.put("tmall.com", "kaixinren.tmall.com");
		firstLevelDomainMap.put("tmall.com", "lehome.tmall.com");
		firstLevelDomainMap.put("tmall.com", "ymall.tmall.com");
		firstLevelDomainMap.put("tmall.com", "yonghengyanse.tmall.com");
		firstLevelDomainMap.put("tmall.com", "qianzibaidai.tmall.com");
		firstLevelDomainMap.put("tmall.com", "seasonwind.tmall.com");
		firstLevelDomainMap.put("tmall.com", "Tonlion.tmall.com");
		firstLevelDomainMap.put("tmall.com", "sportica.tmall.com");
		firstLevelDomainMap.put("tmall.com", "kama.tmall.com");
		firstLevelDomainMap.put("tmall.com", "mirrorfun.tmall.com");
		firstLevelDomainMap.put("tmall.com", "sevlae.tmall.com");
		firstLevelDomainMap.put("tmall.com", "andostoread.tmall.com");
		firstLevelDomainMap.put("tmall.com", "handuyishe.tmall.com");
		firstLevelDomainMap.put("tmall.com", "dongjingzhuyi.tmall.com");
		firstLevelDomainMap.put("tmall.com", "erdos.tmall.com");
		firstLevelDomainMap.put("tmall.com", "aiken.tmall.com");
		firstLevelDomainMap.put("tmall.com", "broadcast.tmall.com");
		firstLevelDomainMap.put("tmall.com", "chuancheng.tmall.com");
		firstLevelDomainMap.put("tmall.com", "anta.tmall.com");
		firstLevelDomainMap.put("tmall.com", "dell.tmall.com");
		firstLevelDomainMap.put("tmall.com", "sacon.tmall.com");
		firstLevelDomainMap.put("tmall.com", "riwa.tmall.com");
		firstLevelDomainMap.put("tmall.com", "birdsj.tmall.com");
		firstLevelDomainMap.put("tmall.com", "xuelingfei.tmall.com");
		firstLevelDomainMap.put("tmall.com", "beely.tmall.com");
		firstLevelDomainMap.put("tmall.com", "marubi.tmall.com");
		firstLevelDomainMap.put("tmall.com", "baifurun.tmall.com");
		firstLevelDomainMap.put("tmall.com", "merryshine.tmall.com");
		firstLevelDomainMap.put("tmall.com", "ruixiwu.tmall.com");
		firstLevelDomainMap.put("tmall.com", "votoro.tmall.com");
		firstLevelDomainMap.put("tmall.com", "xzyd.tmall.com");
		firstLevelDomainMap.put("tmall.com", "abc.tmall.com");
		firstLevelDomainMap.put("tmall.com", "waflow.tmall.com");
		firstLevelDomainMap.put("tmall.com", "lutule.tmall.com");
		firstLevelDomainMap.put("tmall.com", "haizhibao.tmall.com");
		firstLevelDomainMap.put("tmall.com", "tianyushipin.tmall.com");
		firstLevelDomainMap.put("tmall.com", "hpdyf.tmall.com");
		firstLevelDomainMap.put("tmall.com", "mring.tmall.com");
		firstLevelDomainMap.put("tmall.com", "wanqiantang.tmall.com");
		firstLevelDomainMap.put("vip.com", "m.vip.com");
		firstLevelDomainMap.put("mbaobao.com", "m.mbaobao.com");
		firstLevelDomainMap.put("lashou.com", "m.lashou.com");
		firstLevelDomainMap.put("zhiwo.com", "m.zhiwo.com");
		firstLevelDomainMap.put("kadang.com", "m.kadang.com");
		firstLevelDomainMap.put("163.com", "baojian.163.com");
		firstLevelDomainMap.put("163.com", "caipiao.163.com");
		firstLevelDomainMap.put("163.com", "yxp.163.com");
		firstLevelDomainMap.put("yougou.com", "m.yougou.com");
		firstLevelDomainMap.put("shangpin.com", "m.shangpin.com");
		firstLevelDomainMap.put("newegg.cn", "m.newegg.cn");
		firstLevelDomainMap.put("paixie.net", "wap.paixie.net");
		firstLevelDomainMap.put("xiu.com", "tuan.xiu.com");
		firstLevelDomainMap.put("suning.com", "redbaby.suning.com");
		firstLevelDomainMap.put("suning.com", "m.suning.com");
		firstLevelDomainMap.put("moonbasa.com", "t.moonbasa.com");
		firstLevelDomainMap.put("moonbasa.com", "m.moonbasa.com");
		firstLevelDomainMap.put("dangdang.com", "m.dangdang.com");
		firstLevelDomainMap.put("dangdang.com", "fashion.dangdang.com");
		firstLevelDomainMap.put("us.com", "follifollie.us.com");
		firstLevelDomainMap.put("vjia.com", "m.vjia.com");
		firstLevelDomainMap.put("taobao.com", "syncchic.taobao.com");
		firstLevelDomainMap.put("taobao.com", "sugargirl.taobao.com");
		firstLevelDomainMap.put("taobao.com", "xiaoye.taobao.com");
		firstLevelDomainMap.put("masamaso.com", "m.masamaso.com");
		firstLevelDomainMap.put("lenovo.com.cn", "serviceshop.lenovo.com.cn");
		firstLevelDomainMap.put("10086.cn", "service.bj.10086.cn");
		firstLevelDomainMap.put("10086.cn", "12580.10086.cn");
		firstLevelDomainMap.put("aimer.com.cn", "m.aimer.com.cn");
		firstLevelDomainMap.put("jiuxian.com", "m.jiuxian.com");
		firstLevelDomainMap.put("360kad.com", "m.360kad.com");
		firstLevelDomainMap.put("s.cn", "m.s.cn");
		firstLevelDomainMap.put("rax.cn", "m.rax.cn");
		firstLevelDomainMap.put("meijing.com", "m.meijing.com");
		firstLevelDomainMap.put("dianping.com", "t.dianping.com");
		firstLevelDomainMap.put("meituan.com", "m.meituan.com");
		firstLevelDomainMap.put("happigo.com", "m.happigo.com");
		firstLevelDomainMap.put("etpass.com", "hotel.etpass.com");
		firstLevelDomainMap.put("etpass.com", "flight.etpass.com");
		firstLevelDomainMap.put("wozhongla.com", "wapf.wozhongla.com");
		firstLevelDomainMap.put("189.cn", "zj.189.cn");
		firstLevelDomainMap.put("189.cn", "js.189.cn");
		firstLevelDomainMap.put("8791.com", "wap.8791.com");
	}

	public String getDomain(String url) {
		url = StringUtils.trimToEmpty(url);
		Matcher matcher = domainPattern.matcher(url);
		if (matcher.find()) {
			return matcher.group();
		}
		return null;
	}

	public String cleanURL(String url) {
		url = StringUtils.trimToEmpty(url);
		String domain = getDomain(url);
		return cleanURL(url, domain);
	}

	public String cleanURL(String url, String domain) {
		url = StringUtils.trimToEmpty(url);
		Collection<IURLAnalyzer> filters = domainFilters.get(domain);
		String cleanURL = null;
		for (IURLAnalyzer analyzer : filters) {
			if ((cleanURL = analyzer.getCleanUrl(url)) != null) {
				return cleanURL;
			}
		}
		return url;
	}

	public String getGoodsId(String url) {
		url = StringUtils.trimToEmpty(url);
		String domain = getDomain(url);
		return getGoodsId(url, domain);
	}

	public String getGoodsId(String url, String domain) {
		url = StringUtils.trimToEmpty(url);
		Collection<IURLAnalyzer> filters = domainFilters.get(domain);
		String id = null;
		for (IURLAnalyzer analyzer : filters) {
			if ((id = analyzer.getGoodsId(url)) != null) {
				return id;
			}
		}
		return null;
	}

	public List<String> getSources(String domain) {
		return domainSources.get(domain);
	}

	public List<String> getSourcesByURL(String url) {
		return getSources(getDomain(url));
	}

	/**
	 * FIXME 该方法只有在比较同一个网站下的两个url时才准确
	 * 
	 * 判断两个URL是否指向同一个商品
	 * 
	 * @param url1
	 * @param url2
	 * @return
	 */
	public boolean isSameGoods(String url1, String url2) {
		String goodsId1 = getGoodsId(url1);
		String goodsId2 = getGoodsId(url2);
		return goodsId1 != null && goodsId1.equals(goodsId2);
	}

	/**
	 * 是否为淘宝/天猫的域名
	 */
	public boolean isTaoDomain(String domain) {
		return "taobao.com".equals(domain) ? true : "tmall.com".equals(domain);
	}

	/**
	 * 是否为淘宝域名
	 */
	public boolean isTaobaoDomain(String domain) {
		return "taobao.com".equals(domain);
	}

	/**
	 * 是否为天猫域名
	 */
	public boolean isTianmaoDomain(String domain) {
		return "tmall.com".equals(domain);
	}

	/**
	 * 根据domain获取source，目前只支持淘宝和天猫
	 * 
	 * @param url
	 *            商品详情页url
	 * @return source名，如天猫
	 */
	public String getSourceByUrl(String url) {
		String urlDomain = getDomain(url);
		String source = null;
		try {
			source = domainSources.get(urlDomain).get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return source;
	}

	/**
	 * 获取普通一级域名或特殊的非一级域名
	 * 
	 * @param url
	 *            需要解析的URL
	 */
	public String getCommonDomain(String url) {
		url = StringUtils.trimToEmpty(url);
		String domain = getDomain(url);
		for (String newDomain : firstLevelDomainMap.get(domain)) {
			if (null != newDomain) {
				String fullDomain = getFullDomain(url);
				if (newDomain.equals(fullDomain)) {
					return fullDomain;
				}
			}
		}
		return domain;
	}

	/**
	 * 获取指定URL的全域名
	 * 
	 * @create 2013/10/30
	 * @param url
	 * @return null或URL对应的全域名
	 */
	public String getFullDomain(String url) {
		Matcher matcher = fullDomainPattern.matcher(url);
		return matcher.find() ? matcher.group() : null;
	}
	/**
	 * 获取一个连接的domain和所有标签,其中都包含key为domain，值为主域名的键值对
	 * @param url
	 * @return
	 */
	public Map<String, String> getAllTag(String url){
		Map<String, String> urlTag = new HashMap<String, String>();
		urlTag.put("domain", getDomain(url));
		if (null == url) return urlTag;
		String[] urls = url.split("\\?");
		if (urls.length == 1) return urlTag;
		String[] tags = urls[1].split("&");
		for(String tag : tags){
			String[] aTag = tag.split("=");
			String tagKey = aTag[0];
			String tagValue = aTag.length == 1 ? null : aTag[1];
			urlTag.put(tagKey.trim(), tagValue);
		}
		return urlTag;
	}
	/**
	 * 获取第二次跳转的主域名部分
	 * 如
	 * @param ourl
	 * @return
	 */
	public String getRedirectHost(String ourl){
		String host = "";
		try {
			String[] ourlString = ourl.split("\\?")[0].split("/");
			for (int i=0 ; i<ourlString.length-1; i++){
				host += ourlString[i];
				host += "/";
			}
		} catch (Exception e) {
			return "";
		}
		return host;
	}
	
	public static void main(String[] args) {
		URLAnalyzerManager urlAnalyzerManager = new URLAnalyzerManager();
		String urlString = "https://www.kaola.com";
		System.out.println(urlAnalyzerManager.cleanURL(urlString));
		System.out.println(urlAnalyzerManager.getFullDomain(urlString));
		System.out.println(urlAnalyzerManager.getDomain(urlString));
		
	}
}
