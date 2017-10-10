package com.fanmila.model.cps;


import com.fanmila.model.ClickHandlerInfo;
import com.fanmila.model.Union;
import com.fanmila.model.mongo.Entity;

/**
 * 广告被点击到跳转过程信息
 * 
 * @author Larry Lang
 * @email larry.lang@b5m.com
 * @date Jan 21, 2013
 * 
 */
public class CpsClick extends Entity {

	/**
	 * 用户ip
	 */
	private String ip;

	/**
	 * 商品url，来自于请求参数。
	 */
	private String turl;

	/**
	 * 商品url域名，来自于商品url
	 */
	private String tdomain;

	/**
	 * 重定向url，由系统根据商品url生成，并响应给客户端。
	 */
	private String durl;

	/**
	 * 原始请求的url
	 */
	private String ourl;

	/**
	 * 当前页面url
	 */
	private String curl;

	/**
	 * 当前页面的referrer url
	 */
	private String rurl;

	/**
	 * 产品标识
	 */
	private String sid;

	/**
	 * 产品反馈信息（支持不同产品对自己的数据进行挖掘，比如plugin可以传uuid过来，可以区分每个uuid的click,目前用于标识产品子产品或模块）
	 */
	private String sfb;
	
	private String feedback;

	private String docid;

	private String uuid;

	private Union union;
	//产品安装时，推广的渠道
	private String source;
	//产品安装时，推广的渠道
	private String ssub;

	private String datatype;
	private ClickHandlerInfo handlerInfo;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getTurl() {
		return turl;
	}

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public void setTurl(String turl) {
		this.turl = turl;
	}

	public String getDurl() {
		return durl;
	}

	public void setDurl(String durl) {
		this.durl = durl;
	}

	public String getOurl() {
		return ourl;
	}

	public void setOurl(String ourl) {
		this.ourl = ourl;
	}

	public String getCurl() {
		return curl;
	}

	public void setCurl(String curl) {
		this.curl = curl;
	}

	public String getRurl() {
		return rurl;
	}

	public void setRurl(String rurl) {
		this.rurl = rurl;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getSfb() {
		return sfb;
	}

	public void setSfb(String sfb) {
		this.sfb = sfb;
	}

	public String getDocid() {
		return docid;
	}

	public void setDocid(String docid) {
		this.docid = docid;
	}

	public String getTdomain() {
		return tdomain;
	}

	public void setTdomain(String tdomain) {
		this.tdomain = tdomain;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Union getUnion() {
		return union;
	}

	public void setUnion(Union union) {
		this.union = union;
	}

	public ClickHandlerInfo getHandlerInfo() {
		return handlerInfo;
	}

	public void setHandlerInfo(ClickHandlerInfo handlerInfo) {
		this.handlerInfo = handlerInfo;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSsub() {
		return ssub;
	}

	public void setSsub(String ssub) {
		this.ssub = ssub;
	}
}
