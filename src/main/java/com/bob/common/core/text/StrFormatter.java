package com.bob.common.core.text;

import com.bob.common.utils.StringUtils;

/**
 * @author: zhang bob
 * @date: 2020-12-25 14:21
 * @description: 字符串格式化类
 */
public class StrFormatter {

    public static final String EMPTY_JSON = "{}";
    public static final char C_BACKSLASH = '\\';
    public static final char C_DELIM_START = '{';
    public static final char C_DELIN_END = '}';

    /**
     * 字符串模板替换
     * 格式化字符串：替换{}
     * 例：format(this is {} for {},"a","b")
     * @param stringPattern 模板
     * @param arg 替换字符
     * @return
     */
    public static String format(final String stringPattern, final Object... arg ){
        //检查格式模板是否为空
        if (StringUtils.isEmpty(stringPattern) || StringUtils.isEmpty(arg) ){
            return stringPattern;
        }
        //获取模板长度
        final int stringPatternLength = stringPattern.length();
        //创建字符缓冲区
        StringBuilder sb = new StringBuilder(stringPatternLength + 30);
        //初始位置
        int handledPosition = 0;
        //占位符位置
        int placeholderIndex;
        for (int argIndex = 0; argIndex < arg.length; argIndex++){
            //获取占位符{}位置
            placeholderIndex = stringPattern.indexOf(EMPTY_JSON, handledPosition);
            //未匹配到占位符{}
            if (placeholderIndex == -1){
                //模板不包含占位符
                if (handledPosition ==0){
                    return stringPattern;
                }else {
                    //剩余模板不包含占位符，添加剩余模板
                    sb.append(stringPattern, handledPosition, stringPatternLength);
                    return sb.toString();
                }
            }else {
                //判断占位符前一个字符是否未转义符
                if (placeholderIndex >0 && stringPattern.charAt(placeholderIndex-1) == C_BACKSLASH){
                    //判断占位符前两个字符是否未转义符
                    if (placeholderIndex >1 && stringPattern.charAt(placeholderIndex-2) == C_BACKSLASH){
                        sb.append(stringPattern, handledPosition, placeholderIndex-1);
                        sb.append(arg[argIndex]);
                        sb.append(Convert.utf8Str(arg[argIndex]));
                        handledPosition = placeholderIndex+2;
                    }else {
                        //占位符被转义，原样输出，不被替换
                        argIndex--;
                        sb.append(stringPattern, handledPosition, placeholderIndex - 1);
                        sb.append(C_DELIM_START);
                        handledPosition = placeholderIndex + 1;
                    }
                }else {
                    //未发现转义符，占位符正常替换
                    sb.append(stringPattern, handledPosition, placeholderIndex);
                    sb.append(arg[argIndex]);
                    sb.append(Convert.utf8Str(arg[argIndex]));
                    handledPosition = placeholderIndex + 2;
                }
            }
        }
        sb.append(stringPattern, handledPosition, stringPatternLength);
        return sb.toString();
    }

    public static void main(String[] args) {
        String stringFormat = "this is \\\\{} for {} to very";
        String result = format(stringFormat, "a", "b");
        System.out.println(result);
    }
}
