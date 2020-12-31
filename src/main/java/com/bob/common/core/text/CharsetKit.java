package com.bob.common.core.text;

import com.bob.common.utils.StringUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author: zhang bob
 * @date: 2020-12-29 15:45
 * @description: 字符集类
 */
public class CharsetKit {

    /** ISO_8859_1 */
    public static final String ISO_8859_1 = "ISO_8859_1";
    /** utf-8 */
    public static final String UTF_8 = "utf-8";
    /** gbk */
    public static final String GBK = "GBK";

    /** ISO_8859_1 Charset */
    public static final Charset CHARSET_ISO_8859_1 = Charset.forName(ISO_8859_1);
    /** utf-8 Charset */
    public static final Charset CHARSET_UTF_8 = Charset.forName(UTF_8);
    /** gbk Charset */
    public static final Charset CHARSET_GBK = Charset.forName(GBK);


    /**
     * 转换为Charset对象
     * @param charset 字符集，为空返回默认字符集
     * @return
     */
    public static Charset charset(String charset){
        return StringUtils.isEmpty(charset) ? Charset.defaultCharset():Charset.forName(charset);
    }

    /**
     * 转换字符串的字符集编码
     * @param string 字符串
     * @param srcCharset 源字符集，默认ISO_8859_1
     * @param destCharset 目标字符集，默认UTF-8
     * @return
     */
    public static String convert(String string, String srcCharset, String destCharset){
        return convert(string, Charset.forName(srcCharset), Charset.forName(destCharset));
    }

    /**
     * 转换字符串的字符集编码
     * @param string 字符串
     * @param srcCharset 源字符集，默认ISO_8859_1
     * @param destCharset 目标字符集，默认UTF-8
     * @return
     */
    public static String convert(String string, Charset srcCharset, Charset destCharset){
        if (srcCharset == null){
            srcCharset = StandardCharsets.ISO_8859_1;
        }
        if (destCharset == null){
            destCharset = StandardCharsets.UTF_8;
        }
        if (StringUtils.isEmpty(string) || srcCharset.equals(destCharset)){
            return string;
        }
        return new String(string.getBytes(srcCharset), destCharset);
    }

}
