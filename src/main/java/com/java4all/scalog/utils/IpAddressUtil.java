package com.java4all.scalog.utils;

import javax.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

/**
 * @author wangzhongxiang
 * @date 2020年05月14日 11:37:30
 */
public class IpAddressUtil {
    /**
     * get ip address
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String unKnown = "unKnown";
        String xRealIp = "X-Real-IP";
        String xForwardedFor = "X-Forwarded-For";
        String headerXRealIp = request.getHeader(xRealIp);
        String headerXForwardedFor = request.getHeader(xForwardedFor);
        if (!StringUtils.isEmpty(headerXForwardedFor) && !unKnown.equalsIgnoreCase(headerXForwardedFor)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = headerXForwardedFor.indexOf(",");
            if (index != -1) {
                return headerXForwardedFor.substring(0, index);
            } else {
                return headerXForwardedFor;
            }
        }
        headerXForwardedFor = headerXRealIp;
        if (!StringUtils.isEmpty(headerXForwardedFor) && !unKnown.equalsIgnoreCase(headerXForwardedFor)) {
            return headerXForwardedFor;
        }
        if (StringUtils.isEmpty(headerXForwardedFor) || unKnown.equalsIgnoreCase(headerXForwardedFor)) {
            headerXForwardedFor = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(headerXForwardedFor) || unKnown.equalsIgnoreCase(headerXForwardedFor)) {
            headerXForwardedFor = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(headerXForwardedFor) || unKnown.equalsIgnoreCase(headerXForwardedFor)) {
            headerXForwardedFor = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isEmpty(headerXForwardedFor) || unKnown.equalsIgnoreCase(headerXForwardedFor)) {
            headerXForwardedFor = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isEmpty(headerXForwardedFor) || unKnown.equalsIgnoreCase(headerXForwardedFor)) {
            headerXForwardedFor = request.getRemoteAddr();
        }
        return headerXForwardedFor;
    }
}
