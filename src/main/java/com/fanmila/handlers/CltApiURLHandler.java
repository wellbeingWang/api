package com.fanmila.handlers;

import com.fanmila.model.ClickHandlerInfo;
import com.fanmila.model.URLHandlerContext;
import com.fanmila.util.analyzer.URLAnalyzerManager;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class CltApiURLHandler implements IApiHandler {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	

	@Autowired
	protected URLAnalyzerManager urlAnalyzerManager;
	


	@Override
	public final String getRedirectURL(URLHandlerContext context) {
		ClickHandlerInfo handlerInfo = context.getHandlerInfo();
		// 记录当前handler处理过该context
		handlerInfo.recordApiHandler(this);
		String url = getRedirectURL(context, handlerInfo);
		//handlerInfo.setClickType(getClickType());
		return url;
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
	public static boolean isHave(String s, String t) {
		if (StringUtils.isBlank(s)) return true;
		String[] strs = s.split(",");
		for(int i=0;i<strs.length;i++) {
			//循环查找字符串数组中的每个字符串中是否包含所有查找的内容
			if(strs[i].equals(t)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 黑名单，false 不限制， true限制
	 * @param s
	 * @param t
     * @return
     */
	public static boolean isHaveA(String s, String t) {
		if (StringUtils.isBlank(s)) return false;
		String[] strs = s.split(",");
		for(int i=0;i<strs.length;i++) {
			//循环查找字符串数组中的每个字符串中是否包含所有查找的内容
			if(strs[i].equals(t)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isHaveB(String reg, String s, String t) {
		if (StringUtils.isBlank(s)) return true;
		String[] strs = s.split(",");
		String[] strt = t.split(reg);
		for (String t1 : strt) {
			for (int i = 0; i < strs.length; i++) {
				//循环查找字符串数组中的每个字符串中是否包含所有查找的内容
				if (strs[i].equals(t1)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean versionCompareTo(String va, String vb){
        if(StringUtils.isBlank(va) || StringUtils.isBlank(vb)) return false;
		String vac = va.split("_")[0];
		String vbc = vb.split("_")[0];
		String[] vacc = vac.split("\\.");
		String[] vbcc = vbc.split("\\.");
		for(int i =0; i<vacc.length; i++){
			if(StringUtils.isBlank(vbcc[i])) return false;
			try{
				Long vai = Long.valueOf(vacc[i]);
				Long vbi = Long.valueOf(vbcc[i]);
				if(vai.compareTo(vbi)==0) continue;
				if(vai.compareTo(vbi)>0) return false;
				if(vai.compareTo(vbi)<0) return true;
			}catch (Exception e){
				e.printStackTrace();
				return false;
			}
		}

		return false;
	}

	protected abstract String getRedirectURL(URLHandlerContext context, ClickHandlerInfo handlerInfo);

}
