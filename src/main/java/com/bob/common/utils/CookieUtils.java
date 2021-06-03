package com.bob.common.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author: zhang bob
 * @date: 2021-06-02 15:29
 * @description: Cookie 工具类
 */
public class CookieUtils {

    /**
     * 设置cookie
     * @param response
     * @param name
     * @param value
     */
    public static void setCookie(HttpServletResponse response, String name, String value) {
        setCookie(response, name, value, 60*60*24);
    }

    /**
     * 设置cookie
     * @param response
     * @param name
     * @param value
     * @param maxAge
     */
    public static void setCookie(HttpServletResponse response, String name, String value, int maxAge) {
        setCookie(response, name, value, "/", maxAge);
    }

    /**
     * 设置cookie
     * @param response
     * @param name
     * @param value
     * @param path
     * @param maxAge
     */
    public static void setCookie(HttpServletResponse response, String name, String value, String path, int maxAge) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath(path);
        cookie.setMaxAge(maxAge);

        try {
            cookie.setValue(URLEncoder.encode(value, "UTF-8"));
        } catch ( UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.addCookie(cookie);
    }
}
