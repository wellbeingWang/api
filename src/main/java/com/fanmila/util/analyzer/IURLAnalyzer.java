package com.fanmila.util.analyzer;

/**
 * URL分析器
 * 
 * @author lscm
 * 
 */
public interface IURLAnalyzer {

	public AnalysisResult analysis(String url);

	public String getCleanUrl(String url);

	public String getGoodsId(String url);

}
