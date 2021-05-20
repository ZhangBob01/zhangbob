package com.bob.common.exception.base;

import com.bob.common.utils.MessageUtils;
import com.bob.common.utils.StringUtils;
import lombok.Data;

/**
 * @author: zhang bob
 * @date: 2021-01-06 09:26
 * @description: 自定义异常类
 */
@Data
public class BaseException extends RuntimeException {

    /** 所属模块. */
    private String module;
    /** 错误提示码. */
    private String code;
    /** 错误码对应参数. */
    private Object[] args;
    /** 错误提示消息. */
    private String message;

    /**
     * 全参数构造方法
     * @param module
     * @param code
     * @param args
     * @param message
     */
    public BaseException(String module, String code, Object[] args, String message){
        this.module = module;
        this.code = code;
        this.args = args;
        this.message = message;
    }
    public BaseException(String module, String code, Object[] args)
    {
        this(module, code, args, null);
    }

    public BaseException(String module, String defaultMessage)
    {
        this(module, null, null, defaultMessage);
    }

    public BaseException(String code, Object[] args)
    {
        this(null, code, args, null);
    }
    public BaseException(String message) {
        this(null, null, null, message);
    }

    @Override
    public String getMessage(){
        String newMessage = null;
        if (StringUtils.isNotEmpty(code)){
            newMessage = MessageUtils.message(code, args);
        }
        if (newMessage == null){
            newMessage = message;
        }
        return newMessage;
    }


}
