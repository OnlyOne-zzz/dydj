package com.bestfeng.dydj.configuration;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {


	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 多个拦截器组成一个拦截器链
		// addPathPatterns 用于添加拦截规则
		// excludePathPatterns 用户排除拦截
		registry.addInterceptor(myInterceptor()).addPathPatterns("/**").excludePathPatterns("/swagger-resources/**").excludePathPatterns("/v2/api-docs/**").excludePathPatterns("/error/**");
		super.addInterceptors(registry);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins(new String[] { "*" }).allowedMethods(new String[] { "POST", "GET", "OPTIONS" }).allowedHeaders(new String[] { "*" });
	}

	@Bean
	public ContainerInterceptor myInterceptor() {
		return new ContainerInterceptor();
	}

	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public FilterRegistrationBean filterRegist() {
		FilterRegistrationBean frBean = new FilterRegistrationBean();
		frBean.setFilter(new ContainerFilter());
		frBean.addUrlPatterns("/*");
		return frBean;
	}
}
