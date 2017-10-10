package com.fanmila.util.analyzer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternURLAnalyzer implements IURLAnalyzer {

	private Pattern pattern;
	private String cleanUrlFormat;
	private String goodsIdFormat;

	public PatternURLAnalyzer() {
		super();
	}

	public PatternURLAnalyzer(Pattern pattern, String cleanUrlFormat, String goodsIdFormat) {
		super();
		this.pattern = pattern;
		this.cleanUrlFormat = cleanUrlFormat;
		this.goodsIdFormat = goodsIdFormat;
	}

	public PatternURLAnalyzer(String pattern, String cleanUrlFormat, String goodsIdFormat) {
		this(Pattern.compile(pattern), cleanUrlFormat, goodsIdFormat);
	}

	@Override
	public AnalysisResult analysis(String url) {
		AnalysisResult ar = new AnalysisResult();
		Matcher matcher = pattern.matcher(url);
		if (matcher.matches()) {
			StringBuffer sb = new StringBuffer();
			matcher.appendReplacement(sb, cleanUrlFormat);
			matcher.appendTail(sb);
			ar.setCleanUrl(sb.toString());
			sb = new StringBuffer();
			matcher.reset();
			matcher.matches();
			matcher.appendReplacement(sb, goodsIdFormat);
			matcher.appendTail(sb);
			ar.setId(sb.toString());
		}
		return ar;
	}

	@Override
	public String getCleanUrl(String url) {
		return analysis(url, cleanUrlFormat);
	}

	@Override
	public String getGoodsId(String url) {
		return analysis(url, goodsIdFormat);
	}

	private String analysis(String url, String replacement) {
		Matcher matcher = pattern.matcher(url);
		StringBuffer sb = new StringBuffer();
		if (matcher.matches()) {
			matcher.appendReplacement(sb, replacement);
			matcher.appendTail(sb);
			return sb.toString();
		}
		return null;
	}
}
