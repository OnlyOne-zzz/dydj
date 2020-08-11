package com.bestfeng.dydj.utils;

import com.bestfeng.dydj.constants.Constants;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zh
 */
public class ServletRequestUtils {


    public static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            return request;
        }
        return null;
    }

    public static String getSign(){
        if (getRequest() == null) {
            return null;
        }
        return getRequest().getHeader(Constants.SIGN);
    }

}
