package com.bestfeng.dydj.configuration;

import com.alibaba.fastjson.JSON;
import com.bestfeng.dydj.annotation.SignValidated;
import com.bestfeng.dydj.constants.Constants;
import com.bestfeng.dydj.enums.ApiErrorCodeEnums;
import com.bestfeng.dydj.manager.request.DefaultSignVerify;
import com.bestfeng.dydj.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.aurochsframework.boot.commons.api.CommonResult;
import org.aurochsframework.boot.core.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Map;

@Component
@Slf4j
public class ContainerInterceptor implements HandlerInterceptor {
    @Autowired
    private DefaultSignVerify signVerify;

    /**是否打印所有参数*/
    private boolean enablePrintParam=true;

    private final String UTF_8 = Charset.forName("UTF-8").displayName();


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**做日志链路*/
        String nowRequest = IDUtils.getId("api-");// 区分当前这次请求的日志记录
        request.setAttribute("nowRequest", nowRequest);
        String url = request.getRequestURI();
        // 拦截错误请求
        if (url == null || url.contains("error")) {
            log.error("请求批次号={},请求发生error", nowRequest);
            return false;
        }
        String reqBody = IOUtils.toString(request.getInputStream(), UTF_8);
        /**参数打印*/
        this.printParam(request);
        /**判断当前类或者方法是否需要验签*/
        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            HandlerMethod  handlerMethod = (HandlerMethod) handler;
            //标记在方法上 方法需要签名
            SignValidated signValidated = null;
            //标记在方法或者类上
            if (handlerMethod.getMethod().isAnnotationPresent(SignValidated.class)) {
                signValidated = handlerMethod.getMethod().getAnnotation(SignValidated.class);
            } else {
                //标记在class上 所有方法都需要签名
                if (handlerMethod.getBeanType().isAnnotationPresent(SignValidated.class)) {
                    signValidated = handlerMethod.getBeanType().getAnnotation(SignValidated.class);
                }
            }
            if (signValidated != null ) {
                /**验签*/
                String sign = WebUtils.getHeader(Constants.SIGN,request);
                if(StringUtils.isEmpty(sign)){
                    setResPonse(response,ApiErrorCodeEnums.SIGN_ERR);
                    return false;
                }
                try {
                    signVerify.check(sign,reqBody);
                }catch (Exception e){
                    if(e instanceof BusinessException){
                        setResPonse(response,ApiErrorCodeEnums.SIGN_ERR);
                        return false;
                    }
                }
            }else {
                return true;
            }
        }
        return true;
    }
    // 在DispatcherServlet完全处理完请求之后被调用，可用于清理资源
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // log.debug("在DispatcherServlet完全处理完请求之后被调用，可用于清理资源 ");
    }

    // 在业务处理器处理请求完成之后，生成视图之前执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // log.debug("在业务处理器处理请求完成之后，生成视图之前执行");

    }
    /**
     * 设置拦截器响应数据信息
     * @param response
     */
    public static void setResPonse(HttpServletResponse response, ApiErrorCodeEnums resCode) {
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = null;
        if(!WebUtils.isSameOrigin(ServletRequestUtils.getRequest())){
            response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,"*");
            response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS,"POST,GET,OPTIONS");
            response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,"*");
            response.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS,"*");
            response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS,"true");
        }
        try {
            out = response.getWriter();
            out.append(JSON.toJSONString(CommonResult.custom(resCode.getCode(),resCode.getText(),null)));
        } catch (IOException e) {
            log.error("设置拦截器响应数据信息错误:", e);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

    /**
     * 参数打印
     * @param request
     */
    private void printParam(HttpServletRequest request) {
        if (enablePrintParam) {
            try {
                String nowRequest = (String) request.getAttribute("nowRequest");
                String ip = IpUtils.getIpAddr(request);
                String url = request.getRequestURI();
                String userAgent = request.getHeader("User-Agent");
                String reqParams = IOUtils.toString(request.getInputStream(), UTF_8);
                /**暂时请求头只有sign*/
                Map<String, String> headers = WebUtils.getHeaders(request, Constants.SIGN);
                if (log.isDebugEnabled()) {
                    log.debug("请求批次号={},ip={},url={},agent={},params={},header={}", nowRequest, ip, url, userAgent, reqParams, FastJsons.convertObjectToJSON(headers));
                }
            } catch (Exception e) {

            }
        }
    }
}
