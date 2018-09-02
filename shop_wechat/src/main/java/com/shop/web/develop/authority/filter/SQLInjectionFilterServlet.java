package com.shop.web.develop.authority.filter;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 防止SQL注入
 * @Copyright 北京瑞友科技股份有限公司
 * @author xiaolei
 * @date 2014年9月28日
 * =================Modify Record=================
 * @Modifier			@date			@Content
 *
 */
//@WebFilter(urlPatterns = "/*", filterName = "SQLInjection", initParams = { @WebInitParam(name = "regularExpression", value = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"
//		+ "(\\b(select|update|and|or|delete|insert|truncate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)"), 
//		@WebInitParam(name = "ignores", value ="/js,/css,/images,/400,/403,/404,/500")})
public class SQLInjectionFilterServlet implements Filter {
    private String regularExpression;
    private Pattern sqlPattern;
    private String[] ignores;
    private static final Logger log = LoggerFactory.getLogger(SQLInjectionFilterServlet.class);

    public SQLInjectionFilterServlet() {

    }

    public void init(FilterConfig filterConfig) throws ServletException {
        regularExpression = filterConfig.getInitParameter("regularExpression");
        sqlPattern = Pattern.compile(regularExpression, Pattern.CASE_INSENSITIVE); 
        ignores = filterConfig.getInitParameter("ignores").split(",");
    }

    /*
     * 如果需要输入“'”、“;”、“--”这些字符 可以考虑将这些字符转义为html转义字符 “'”转义字符为：&#39; “;”转义字符为：&#59;
     * “--”转义字符为：&#45;&#45;
     */
    @SuppressWarnings("rawtypes")
	public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String requestUrl = req.getRequestURL().toString();
        String contextPath = req.getContextPath();
        requestUrl = requestUrl.substring(requestUrl.indexOf(contextPath)
                + contextPath.length());// 获取剥离contextPath的url路径

        if(canIgnore(requestUrl)){
        	chain.doFilter(request, response);
        	return ;
        }
        Map parametersMap = request.getParameterMap();
        Iterator it = parametersMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String[] value = (String[]) entry.getValue();
            for (int i = 0; i < value.length; i++) {
            	if (null!=value[i]&&sqlPattern.matcher(value[i]).find()) {
                    log.info("#疑似SQL注入攻击！参数名称：{}；录入信息:{}", entry.getKey(), value[i]);
                    request.setAttribute("err", "您输入的参数有非法字符，请输入正确的参数！");
                    request.setAttribute("pageUrl",req.getRequestURI());
                    request.getRequestDispatcher("/403").forward(request, response);
                    return;
                  
                }
            }
        }
        chain.doFilter(request, response);
    }

    public void destroy() {

    }
    
    private boolean canIgnore(String requestUrl) {  
        for (String ignore : ignores) {  
            if (requestUrl.indexOf(ignore) >= 0) {  
                return true;  
            }  
        }  
        return false;  
    }

}