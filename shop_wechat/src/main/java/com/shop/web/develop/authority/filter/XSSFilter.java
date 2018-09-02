/**  
* @Project: poly_ticket
* @Title: XSSFilter.java
* @Package: com.poly.ticket.filter
* @Description: 
* @author:admin 
* @date: 2016-7-4 23:14:53
* @Copyright: 
* @version: V1.0  
*/
package com.shop.web.develop.authority.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 *  
 *
 * @ClassName: XSSFilter
 * @Description: 
 * @author:admin
 * @date: 2016-7-4 23:14:53
 */
public class XSSFilter implements Filter {
	
	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) request), response);
	}
}
