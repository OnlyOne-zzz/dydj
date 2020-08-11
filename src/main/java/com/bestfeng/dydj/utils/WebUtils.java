package com.bestfeng.dydj.utils;

import com.google.common.collect.Maps;
import org.springframework.http.server.ServletServerHttpRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * @author Xs.Tao
 */
public class WebUtils extends org.springframework.web.util.WebUtils {

	public static Map<String, String> getHeaders(HttpServletRequest request) {

		return getHeaders(request, null);
	}

	public static Map<String, String> getHeaders(HttpServletRequest request, String... AccpetHeaderName) {
		Map<String, String> rst = Maps.newHashMap();
		Enumeration<String> enumeration = request.getHeaderNames();
        List<String> accpetHeader = (AccpetHeaderName != null && AccpetHeaderName.length > 0) ?
                Arrays.asList(AccpetHeaderName) : null;
		while (enumeration.hasMoreElements()) {
			String name = enumeration.nextElement();
            if (accpetHeader == null || accpetHeader.size() <= 0) {
                rst.put(name, getHeader(name, request));
                continue;
            }
			if (accpetHeader != null && accpetHeader.size() > 0 && accpetHeader.contains(name)) {
				rst.put(name, getHeader(name, request));
			}
		}
		return rst;
	}

	public static String getHeader(String header, HttpServletRequest request) {
		return request.getHeader(header);
	}
	public static boolean isSameOrigin(HttpServletRequest servletRequest){
		ServletServerHttpRequest request =new ServletServerHttpRequest(servletRequest);
		return isSameOrigin(request);
	}


}
