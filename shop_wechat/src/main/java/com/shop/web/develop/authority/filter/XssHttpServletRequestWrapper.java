/**  
* @Project: poly_ticket
* @Title: XssHttpServletRequestWrapper.java
* @Package: com.poly.ticket.filter
* @Description: 
* @author:admin 
* @date: 2016-7-4 23:14:56
* @Copyright: 
* @version: V1.0  
*/
package com.shop.web.develop.authority.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 *  
 *
 * @ClassName: XssHttpServletRequestWrapper
 * @Description: 
 * @author:admin
 * @date: 2016-7-4 23:14:56
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    /**
     * Instantiates a new xss http servlet request wrapper.
     *
     * @param servletRequest the servlet request
     */
    public XssHttpServletRequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletRequestWrapper#getParameterValues(java.lang.String)
     */
    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);

        if (values == null) {
            return null;
        }

        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            // 第一种方法
            // encodedValues[i] = xssEncode(values[i]);

            // 第二种方法 （org.apache.commons.lang）
            encodedValues[i] = StringEscapeUtils.escapeHtml4(values[i]);// 转换为HTML转义字符表示
            // encodedValues[i]=StringEscapeUtils.escapeEcmaScript(values[i]);//过滤js
            // value.replaceAll("('.+--)|(--)|(\\|)|(%7C)", "") 剥离SQL注入部分代码

            // 第三种方法（ Spring 的优秀工具类盘点）
            // encodedValues[i] = HtmlUtils.htmlEscape(values[i]); //转换为HTML转义字符表示
            // encodedValues[i] = JavaScriptUtils.javaScriptEscape(values[i]);//过滤js

        }

        return encodedValues;
    }

    /* (non-Javadoc)
     * @see javax.servlet.ServletRequestWrapper#getParameter(java.lang.String)
     */
    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        return StringEscapeUtils.escapeHtml4(value);
        // return stripXSS(value);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServletRequestWrapper#getHeader(java.lang.String)
     */
    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        return StringEscapeUtils.escapeHtml4(value);
        // return stripXSS(value);
    }

    // private String stripXSS(String value) {
    // if (value != null) {
    // // NOTE: It's highly recommended to use the ESAPI library and
    // // uncomment the following line to
    // // avoid encoded attacks.
    // // value = ESAPI.encoder().canonicalize(value);
    //
    // // Avoid null characters
    // value = value.replaceAll("", "");
    //
    // // Avoid anything between script tags
    // Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
    // value = scriptPattern.matcher(value).replaceAll("");
    //
    // // Avoid anything in a src='...' type of e­xpression
    // scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'",
    // Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    // value = scriptPattern.matcher(value).replaceAll("");
    //
    // scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"",
    // Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    // value = scriptPattern.matcher(value).replaceAll("");
    //
    // // Remove any lonesome </script> tag
    // scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
    // value = scriptPattern.matcher(value).replaceAll("");
    //
    // // Remove any lonesome <script ...> tag
    // scriptPattern = Pattern.compile("<script(.*?)>",
    // Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    // value = scriptPattern.matcher(value).replaceAll("");
    //
    // // Avoid eval(...) e­xpressions
    // scriptPattern = Pattern.compile("eval\\((.*?)\\)",
    // Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    // value = scriptPattern.matcher(value).replaceAll("");
    //
    // // Avoid e­xpression(...) e­xpressions
    // scriptPattern = Pattern.compile("e­xpression\\((.*?)\\)",
    // Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    // value = scriptPattern.matcher(value).replaceAll("");
    //
    // // Avoid javascript:... e­xpressions
    // scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
    // value = scriptPattern.matcher(value).replaceAll("");
    //
    // // Avoid vbscript:... e­xpressions
    // scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
    // value = scriptPattern.matcher(value).replaceAll("");
    //
    // // Avoid onload= e­xpressions
    // scriptPattern = Pattern.compile("onload(.*?)=",
    // Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    // value = scriptPattern.matcher(value).replaceAll("");
    // }
    // return value;
    // }
}