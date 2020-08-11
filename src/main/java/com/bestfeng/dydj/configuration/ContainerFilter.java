package com.bestfeng.dydj.configuration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 这个过滤器的作用是将自定义的HttpServletRequestWrapper传递下去，便于controller能获取@RequestBody注解的参数
 * 
 * @author lianghe
 */
@WebFilter(urlPatterns = "/*")
public class ContainerFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		BodyReaderHttpServletRequestWrapper requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) request);
		chain.doFilter(requestWrapper, response);

	}

	@Override
	public void destroy() {

	}
}