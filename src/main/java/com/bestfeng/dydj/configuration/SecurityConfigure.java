package com.bestfeng.dydj.configuration;

import org.aurochsframework.boot.authorization.configuration.DynamicPermissionEvaluator;
import org.aurochsframework.boot.authorization.security.JwtTokenUtil;
import org.aurochsframework.boot.authorization.security.UserKeepAliveDeterminer;
import org.aurochsframework.boot.authorization.security.component.JwtAuthenticationTokenFilter;
import org.aurochsframework.boot.authorization.security.component.RestAuthenticationEntryPoint;
import org.aurochsframework.boot.authorization.security.component.RestfulAccessDeniedHandler;
import org.aurochsframework.boot.authorization.security.component.UserKeepAliveFilter;
import org.aurochsframework.boot.authorization.security.configuration.IgnoreUrlsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfigure extends WebSecurityConfigurerAdapter {

    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .authorizeRequests()
                //不需要保护的资源路径允许访问
                .antMatchers(ignoreUrlsConfig.getUrls().toArray(new String[0]))
                .permitAll()
                //允许跨域请求的OPTIONS请求
                .antMatchers(HttpMethod.OPTIONS)
                .permitAll()
                .and()
                // 任何请求需要身份认证
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                // 关闭跨站请求防护及不使用session
                .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //开启跨域访问
                .and()
                .cors()
                // 自定义权限拒绝处理类
                .and()
                .exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                // 自定义权限拦截器JWT过滤器
                .and()
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }


    @Bean
    public DynamicPermissionEvaluator testDynamicPermissionEvaluator() {
        return new DynamicPermissionEvaluator();
    }

    @Bean
    public UserKeepAliveFilter userKeepAliveFilter(UserKeepAliveDeterminer userKeepAliveDeterminer,
                                                   ApplicationEventPublisher publisher,
                                                   JwtTokenUtil jwtTokenUtil) {
        return new UserKeepAliveFilter(userKeepAliveDeterminer, publisher, jwtTokenUtil);
    }
}
