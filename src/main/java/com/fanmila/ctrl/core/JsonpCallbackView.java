package com.fanmila.ctrl.core;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lscm on 2016/7/7.
 */
public class JsonpCallbackView {

        private final static Logger LOGGER = LoggerFactory.getLogger(JsonpCallbackView.class);

        /**
         * 将业务处理结果渲染到json/jsonp格式,并输出到response
         * @param obj 业务处理结果
         * @param jsonpCallback jsonp回调函数名,可以为null
         * @param response HTTP响应对象
         * @return spring mvc视图(总是为null)
         */
        public static ModelAndView Render(Object obj, String jsonpCallback, HttpServletResponse response) {
            PrintWriter out = null;
            try {
                StringBuffer jsonp = new StringBuffer();
                if (StringUtils.isBlank(jsonpCallback)) {
                    jsonp.append(JSONObject.toJSON(obj));
                    response.setContentType("application/json");
                } else {
                    jsonp.append(jsonpCallback).append("(").append(JSONObject.toJSON(obj)).append(")");
                    response.setContentType("application/javascript");
                }
                response.setCharacterEncoding("utf-8");

                Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
                Matcher m = p.matcher(jsonp.toString());
                StringBuffer res = new StringBuffer();
                while (m.find()) {
                    m.appendReplacement(res, "\\" + toUnicode(m.group()));
                }
                m.appendTail(res);
                out = response.getWriter();
                out.write(res.toString());
            } catch (Exception e) {
                if(LOGGER.isWarnEnabled()){
                    LOGGER.warn("渲染业务处理结果到JSON/JSONP时出错", e);
                }
            } finally {
                if (null != out)
                    out.close();
            }
            return null;
        }

        public static String toUnicode(String str) {
            char[] arChar = str.toCharArray();
            int iValue = 0;
            String uStr = "";
            for (int i = 0; i < arChar.length; i++) {
                iValue = (int) str.charAt(i);
                if (iValue <= 256) {
                    uStr += "\\" + Integer.toHexString(iValue);
                } else {
                    uStr += "\\u" + Integer.toHexString(iValue);
                }
            }
            return uStr;
        }
}
