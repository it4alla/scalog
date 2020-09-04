package com.java4all.scalog.utils;

import com.java4all.scalog.configuration.Constants;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;


/**
 * @decription userInfo Util
 * @author yufangze
 */
public class UserInfoUtil {

    /**
     * get userId
     *
     * @return String
     */
    public static String getUserId(HttpServletRequest request){
        String userIdUnderline = request.getHeader(Constants.USER_ID);
        if (!StringUtils.isEmpty(userIdUnderline)){
            return userIdUnderline;
        }
        String userId = request.getHeader(Constants.USERID);
        if (!StringUtils.isEmpty(userId)){
            return userId;
        }
        String authToken = request.getHeader(Constants.AUTH_HEADER);
        if (authToken != null) {
            String uid = getUid(authToken);
            if (uid!=null){
                return uid;
            }
        }
        return null;
    }

    public static String getUid(String authToken) {
        if (StringUtils.isEmpty(authToken)) {
            return null;
        }
        String uid = null;
        for (String item : authToken.split("&")) {
            int idx = item.indexOf("=");
            if (idx < 0) {
                return null;
            }
            String key = item.substring(0, idx);
            String value = item.substring(idx + 1);
            switch (key) {
                case "uid":
                    uid = value;
                    break;
            }
        }

        if (uid == null) {
            return null;
        }
        return uid;
    }


}
