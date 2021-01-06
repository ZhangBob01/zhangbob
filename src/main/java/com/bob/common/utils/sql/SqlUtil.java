package com.bob.common.utils.sql;

import com.bob.common.exception.base.BaseException;
import com.bob.common.utils.StringUtils;

/**
 * @author: zhang bob
 * @date: 2021-01-06 09:15
 * @description: sql工具类
 */
public class SqlUtil {
    /** 规则：字母、大写字母、数字、下划线、空格、逗号、点（支持多字段排序） .*/
    public static String SQL_PATTERN = "[a-zA-Z0-9_\\ \\,\\.]+";

    /**
     * 检查字符串，防止sql注入
     * @param value
     * @return
     */
    public static String escapeOrderBySql(String value){
        if (StringUtils.isNotEmpty(value) && !isValidOrderBySql(value)){
            throw new BaseException("参数不符合规范，请修改");
        }
        return value;
    }

    /**
     * 验证排序语句是否符合规范
     * @param value
     * @return
     */
    public static boolean isValidOrderBySql(String value){
        return value.matches(SQL_PATTERN);
    }
}
