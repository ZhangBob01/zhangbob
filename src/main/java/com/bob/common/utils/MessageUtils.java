package com.bob.common.utils;

import com.bob.common.utils.spring.SpringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @author: zhang bob
 * @date: 2021-01-06 10:53
 * @description: 提示信息工具类
 */
public class MessageUtils {

    /**
     * 根据代码和参数获取消息
     * @param code 代码
     * @param args 参数
     * @return
     */
    public static String message(String code, Object... args){
        MessageSource messageSource = SpringUtils.getBean(MessageSource.class);
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}

