package com.bob.common.utils;

import com.bob.common.core.text.Convert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author: zhang bob
 * @date: 2021-01-05 19:22
 * @description:
 */
public class ServletUtils {

    /** 移动端请求类型 .*/
    private final static String[] agentArray = {"Android", "iPhone", "iPad", "iPod", "Windows Phone", "MQQBrowser"};

    /**
     * 获取String参数
     * @param name
     * @return
     */
    public static String getParameter(String name) {
        return getRequest().getParameter(name);
    }

    /**
     * 获取String参数
     * @param name
     * @param defaultValue
     * @return
     */
    public static String getParameter(String name, String defaultValue){
        return Convert.toStr(getRequest().getParameter(name), defaultValue);
    }

    /**
     * 获取Integer参数
     * @param name
     * @return
     */
    public static Integer getParameterToInt(String name){
        return Convert.toInt(getRequest().getParameter(name));
    }

    /**
     * 获取Integer参数
     * @param name
     * @param defaultValue
     * @return
     */
    public static Integer getParameterToInt(String name, Integer defaultValue){
        return Convert.toInt(getRequest().getParameter(name), defaultValue);
    }

    /**
     * 获取Request
     * @return
     */
    public static HttpServletRequest getRequest(){
        return getRequestAttributes().getRequest();
    }

    /**
     * 获取Response
     * @return
     */
    public static HttpServletResponse getResponse(){
        return getRequestAttributes().getResponse();
    }

    /**
     * 获取Session
     * @return
     */
    public static HttpSession getSesion() {
        return getRequest().getSession();
    }

    /**
     * 获取请求参数
     * @return
     */
    public static ServletRequestAttributes getRequestAttributes(){
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }

    /**
     * 字符串渲染到客户端
     * @param response
     * @param string
     * @return
     */
    public static String renderString(HttpServletResponse response,String string){
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
        return null;
    }

    /**
     * 判断是否Ajax异步请求
     * @param request
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest request){
        String accept = request.getHeader("accept");
        if (accept !=null && accept.indexOf("application/json") != -1){
            return true;
        }

        String xRequestedWith = request.getHeader("X-Requested-With");
        if (xRequestedWith !=null && xRequestedWith.indexOf("XMLHttpRequest") !=-1){
            return true;
        }

        String uri = request.getRequestURI();
        if (StringUtils.inStringIgnoreCase(uri, ".json", ".xml")){
            return true;
        }
        String ajax = request.getParameter("__ajax");
        if (StringUtils.inStringIgnoreCase(ajax, ".json", ".xml")){
            return true;
        }

        return false;
    }

    /**
     * 判断是否来自手机
     * @param agent
     * @return
     */
    public static boolean checkAgentIsMobile(String agent){
        boolean flag = false;
        if (!agent.contains("Windows NT") || (agent.contains("Windows NT") && agent.contains("compatible; MSIE 9.0;"))){
            //过滤apple桌面系统
            if(!agent.contains("Windows NT") && !agent.contains("Macintosh")){
                for (String item: agentArray){
                    if (agent.contains(item)){
                        flag =true;
                        break;
                    }
                }
            }
        }
        return flag;
    }
}
