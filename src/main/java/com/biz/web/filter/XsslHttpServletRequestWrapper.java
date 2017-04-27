package com.biz.web.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/4/27.
 */
public class XsslHttpServletRequestWrapper extends HttpServletRequestWrapper {

    HttpServletRequest xssRequest = null;

    public XsslHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        xssRequest = request;
    }


    @Override
    public String getParameter(String name) {
        String value = super.getParameter(replaceXSS(name));
        if (value != null) {
            value = replaceXSS(value);
        }
        return value;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(replaceXSS(name));
        if(values != null && values.length > 0){
            for(int i =0; i< values.length ;i++){
                values[i] = replaceXSS(values[i]);
            }
        }
        return values;
    }

    @Override
    public String getHeader(String name) {

        String value = super.getHeader(replaceXSS(name));
        if (value != null) {
            value = replaceXSS(value);
        }
        return value;
    }
    /**
     * 去除待带script、src的语句，转义替换后的value值
     */
    public static String replaceXSS(String value) {
        if (value != null) {
            try{
                value = value.replace("+","%2B");   //'+' replace to '%2B'
                value = URLDecoder.decode(value, "utf-8");
            }catch(UnsupportedEncodingException e){
            }catch(IllegalArgumentException e){
            }

            // Avoid null characters
            value = value.replaceAll("\0", "");

            // Avoid anything between script tags
            Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid anything in a src='...' type of e­xpression
            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            // Remove any lonesome </script> tag
            scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");

            // Remove any lonesome <script ...> tag
            scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid eval(...) e­xpressions
            scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid e­xpression(...) e­xpressions
            scriptPattern = Pattern.compile("e­xpression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid javascript:... e­xpressions
            scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            // Avoid alert:... e­xpressions
            scriptPattern = Pattern.compile("alert", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
            // Avoid onload= e­xpressions
            scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            scriptPattern = Pattern.compile("vbscript[\r\n| | ]*:[\r\n| | ]*", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");
        }
        return filter(value);
    }
    /**
     * 过滤特殊字符
     */
    public static String filter(String value) {
        if (value == null) {
            return null;
        }
        StringBuffer result = new StringBuffer(value.length());
        for (int i=0; i<value.length(); ++i) {
            char c = value.charAt(i);
            switch(c){
                case'>':
                    result.append('＞');// 全角大于号
                    break;
                case'<':
                    result.append('＜');// 全角小于号
                    break;
                case'\'':
                    result.append('\\');
                    result.append('\'');
                    result.append('\\');
                    result.append('\'');
                    break;
                case'\"':
                    result.append('\\');
                    result.append('\"');// 全角双引号
                    break;
                case'&':
                    result.append('＆');// 全角
                    break;
                case'\\':
                    result.append('＼');// 全角斜线
                    break;
                case'#':
                    result.append('＃');// 全角井号
                    break;
                case':':
                    result.append('：');// 全角冒号
                    break;
                case'%':
                    result.append("\\\\%");
                    break;
                default:
                    result.append(c);
                    break;
            }
        }
        return result.toString();
    }
}