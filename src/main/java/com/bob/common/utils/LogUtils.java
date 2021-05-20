package com.bob.common.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: zhang bob
 * @date: 2021-05-18 13:37
 * @description: 处理并记录日志文件
 */
@Slf4j
public class LogUtils {

    public static String getBlock(Object msg) {
        if (msg == null) {
            msg = "";
        }
        return "[" + msg.toString() + "]";
    }
}
