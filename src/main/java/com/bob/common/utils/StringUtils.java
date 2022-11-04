package com.bob.common.utils;

import com.bob.common.core.text.StrFormatter;

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
    private static final char SEPARATOR = '_';

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
     * @return
     */
    public static String substring(final String str, int start){
        if (str == null){
            return NULLSTRING;
        }
        if (start<0){
            start = 0;
        }
        if (start > str.length()){
            return NULLSTRING;
        }
        return str.substring(start);
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

    /**
     * 格式化文本，占位符替换
     * @param template
     * @param params
     * @return
     */
    public static String format(String template, Object... params){
        if (isEmpty(params) || isEmpty(template)){
            return template;
        }
        return StrFormatter.format(template, params);
    }

    /**
     * 下划线转驼峰命名
     * @param str 字符串
     * @return
     */
    public static String toUnderScoreCase(String str) {
        if (str == null) {
            return NULLSTRING;
        }
        StringBuilder sb = new StringBuilder();
        //前置字符是否大写
        boolean preCharIsUpperCase = true;
        //当前字符是否大写
        boolean currentCharIsUpperCase = true;
        //下一个字符是否大写
        boolean nextCharIsUpperCase = true;

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (i > 0) {
                preCharIsUpperCase = Character.isUpperCase(str.charAt(i - 1));
            }else {
                preCharIsUpperCase = false;
            }
            currentCharIsUpperCase = Character.isUpperCase(c);

            if (i< (str.length() - 1)){
                nextCharIsUpperCase = Character.isUpperCase(str.charAt(i+1));
            }

            if (preCharIsUpperCase && currentCharIsUpperCase && nextCharIsUpperCase) {
                sb.append(SEPARATOR);
            }else if ((i != 0 && !preCharIsUpperCase) && currentCharIsUpperCase) {
                sb.append(SEPARATOR);
            }

            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }

    /**
     * 判断是否保函字符串(忽略大小写)
     * @param str 字符串
     * @param strs 字符串数组
     * @return
     */
    public static boolean inStringIgnoreCase (String str, String... strs){
        if (str !=null && strs !=null){
            for (String s:strs){
                if (str.equalsIgnoreCase(trim(s))){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 大写字母转驼峰式
     * @param str 字符串
     * @return
     */
    public static String convertToCamelCase(String str){

        StringBuilder sb = new StringBuilder();
        if (str == null ||str.isEmpty()){
            return NULLSTRING;
        }else if (!str.contains("_")){
            //不包含_，将首字母大写
            return str.substring(0,1).toUpperCase()+str.substring(1);
        }

        //用下划线将首字符分割
        String[] camelArray = str.split("_");
        for (String camel:camelArray){
            //过滤字符串开头或结尾的下划线，双重下划线
            if (camel.isEmpty()){
                continue;
            }
            sb.append(camel.substring(0,1).toUpperCase());
            sb.append(camel.substring(1).toLowerCase());
        }

        return sb.toString();
    }

    /**
     * 判断一个对象是否为非空
     * @param object
     * @return
     */
    public static boolean isNotNull(Object object) {
        return !isNull(object);
    }

    /**
     * 转换
     * @param obj
     * @return
     * @param <T>
     */
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj)
    {
        return (T) obj;
    }

    /**
     * 驼峰式命名法
     * 例如：user_name->userName
     */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }
        if (s.indexOf(SEPARATOR) == -1) {
            return s;
        }
        s = s.toLowerCase();
        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
