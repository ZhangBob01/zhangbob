package com.bob.common.utils;

import java.util.Collection;
import java.util.Map;


/**
 * @author: zhang bob
 * @date: 2020-12-24 16:06
 * @description:String工具类
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    /**
     * 空字符串.
     */
    private static final String NULLSTRING = "";
    /**
     * 下划线.
     */
    private static final String SEPARATOR = "_";

    /**
     * 设置参数不为空的默认值
     *
     * @param value
     * @param defaultValue
     * @param <T>
     * @return
     */
    public static <T> T nvl(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }

    /**
     * 判断对象是否为null
     *
     * @param obj
     * @return true空，false非空
     */
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    /**
     * 判断一个Collection是否为空：list、set、Queue
     *
     * @param coll
     * @return true空，false非空
     */
    public static boolean isEmpty(Collection<?> coll) {
        return isNull(coll) || coll.isEmpty();
    }

    /**
     * 判断一个Collection是否为非空：list、set、Queue
     *
     * @param coll
     * @return true非空，false空
     */
    public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }

    /**
     * 判断数组是否为空
     *
     * @param objs
     * @return true空，false非空
     */
    public static boolean isEmpty(Object[] objs) {
        return isNull(objs) || (objs.length == 0);
    }

    /**
     * 判断数组是否为非空
     *
     * @param objs
     * @return true非空，false空
     */
    public static boolean isNotEmpty(Object[] objs) {
        return !isEmpty(objs);
    }

    /**
     * 判断map集合是否为空
     *
     * @param map
     * @return true空，false非空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return isNull(map) || map.isEmpty();
    }

    /**
     * 判断map集合是否为非空
     *
     * @param map 集合
     * @return true非空，false空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * 判断字符串是否为空
     * @param str 字符串
     * @return true空，false非空
     */
    public static boolean isEmpty(String str){
        return isNull(str) || NULLSTRING.equals(str.trim());
    }

    /**
     * 判断字符串是否为非空
     * @param str 字符串
     * @return true非空，false空
     */
    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }

    /**
     * 字符串去除空格
     * @param str 字符串
     * @return
     */
    public static String trim(String str){
        return str == null ? "" : str.trim();
    }

    /**
     * 截取字符串
     * @param str 字符串
     * @param start 起始位置
     * @param end 结束位置
     * @return
     */
    public static String substring(final String str, int start, int end){
        if (isNull(str)){
            return NULLSTRING;
        }
        if (end<0){
            end = 0;
        }
        if (start<0){
            start = 0;
        }
        if (end>str.length()){
            end = str.length();
        }
        if (start>end){
            return NULLSTRING;
        }
        return str.substring(start, end);
    }

//    public static String format(String template, Object... params){
//        if (isEmpty(params) || isEmpty(template)){
//            return template;
//        }
//        return StrFormatter.format(template, params);
//    }
















}
