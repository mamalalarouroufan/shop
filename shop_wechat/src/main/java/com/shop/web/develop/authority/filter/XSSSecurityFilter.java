package com.shop.web.develop.authority.filter;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shop.web.develop.authority.service.xss.XSSHttpRequestWrapper;
import com.shop.web.develop.authority.service.xss.XSSSecurityConfig;
import com.shop.web.develop.authority.service.xss.XSSSecurityConstants;
import com.shop.web.develop.authority.service.xss.XSSSecurityManager;

/**
 * 
 * xss攻击脚本过滤器
 * 
 * @Copyright 北京瑞友科技股份有限公司
 * @author xiaolei
 * @date 2014年9月28日 =================Modify Record=================
 * @Modifier @date @Content
 *
 */
@WebFilter(urlPatterns = "/*", filterName = "XSS", initParams = { @WebInitParam(name = "securityconfig", value = "classpath:config/xss_security_config.xml"),
		@WebInitParam(name = "regularExpression", value = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"
		+ "(\\b(select|update|and|or|delete|insert|truncate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)")})
public class XSSSecurityFilter implements Filter {

    private static final Logger log = LoggerFactory.getLogger(XSSSecurityFilter.class);
    private String[] ignores;

    /**
     * 销毁操作
     */
    public void destroy() {

        log.debug("XSSSecurityFilter destroy() begin");
        XSSSecurityManager.destroy();
        log.debug("XSSSecurityFilter destroy() end");
    }

    /**
     * 安全审核 读取配置信息
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 判断是否使用HTTP
        checkRequestResponse(request, response);

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        String requestUrl = httpRequest.getRequestURL().toString();
        String contextPath = httpRequest.getContextPath();
        requestUrl = requestUrl.substring(requestUrl.indexOf(contextPath)
                + contextPath.length());// 获取剥离contextPath的url路径

        if(canIgnore(requestUrl)){
        	chain.doFilter(request, response);
        	return ;
        }
        
        // http信息封装类
        XSSHttpRequestWrapper xssRequest = new XSSHttpRequestWrapper(httpRequest);

        // 对request信息进行封装并进行校验工作，若校验失败（含非法字符），根据配置信息进行日志记录和请求中断处理
        if (xssRequest.validateParameter(httpResponse)) {
            if (XSSSecurityConfig.IS_LOG) {
                String paramStr = "";
                Map<String, String[]> submitParams = httpRequest.getParameterMap();
                Set<String> submitNames = submitParams.keySet();
                for (String submitName : submitNames) {
                    String[] submitValues = submitParams.get(submitName);

                    for (String submitValue : (String[]) submitValues) {
                        paramStr = paramStr + submitValue;
                    }
                }

                log.info("XSS Security Filter RequestURL:" + httpRequest.getRequestURL().toString());
                log.info("param:" + paramStr);
                log.info("XSS Security Filter RequestParameter:" + httpRequest.getParameterMap().toString());
            }
            // 是否中断操作
            if (XSSSecurityConfig.IS_CHAIN) {
            	log.info("中断操作开启，中断当前请求url={}",requestUrl);
                RequestDispatcher rd = request.getRequestDispatcher(XSSSecurityConstants.FILTER_ERROR_PAGE);
                rd.forward(request, response);
                return;
            }

        }
        chain.doFilter(request, response);
    }

    /**
     * 初始化操作
     */
    public void init(FilterConfig filterConfig) throws ServletException {
        XSSSecurityManager.init(filterConfig);
        ignores = XSSSecurityConfig.INGORE_URL.split(",");
    }

    /**
     * 判断Request ,Response 类型
     *
     * @param request
     *            ServletRequest
     * @param response
     *            ServletResponse
     * @throws javax.servlet.ServletException
     */
    private void checkRequestResponse(ServletRequest request, ServletResponse response) throws ServletException {
        if (!(request instanceof HttpServletRequest)) {
            throw new ServletException("Can only process HttpServletRequest");

        }
        if (!(response instanceof HttpServletResponse)) {
            throw new ServletException("Can only process HttpServletResponse");
        }
    }
    
    /**
     * @describe: 过滤不处理的url
     * @author zhangzubing
     * @date 2017年9月7日
     * @param requestUrl
     * @return
     * @throws 
     * @Version 1.0
     */ 
    private boolean canIgnore(String requestUrl) {  
        for (String ignore : ignores) {  
            if (requestUrl.indexOf(ignore) >= 0) {  
                return true;  
            }  
        }  
        return false;  
    }
}
