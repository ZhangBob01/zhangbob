package com.bob.common.core.domain;

import com.bob.common.utils.StringUtils;

import java.util.HashMap;

/**
 * @author: zhang bob
 * @date: 2021-01-06 14:12
 * @description: ajax操作消息提醒
 */
public class AjaxResult extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    /** 状态码. */
    public static final String CODE_TAG = "code";
    /** 状态信息. */
    public static final String MSG_TAG = "message";
    /** 数据对象. */
    public static final String DATA_TAG = "data";

    public enum Type{
        /** 成功. */
        SUCCESS(0),
        /** 警告. */
        WARN(301),
        /** 错误. */
        ERROR(500);

        private final int value;

        Type(int value){
            this.value =value;
        }

        public int value(){
            return this.value;
        }
    }

    public AjaxResult(){}

    /**
     * 状态码，状态信息
     * @param type
     * @param message
     */
    public AjaxResult(Type type, String message){
        super.put(CODE_TAG, type.value);
        super.put(MSG_TAG, message);
    }

    /**
     * 状态码，状态信息，数据
     * @param type
     * @param message
     * @param data
     */
    public AjaxResult (Type type, String message, Object data){
        super.put(CODE_TAG, type.value);
        super.put(MSG_TAG, message);
        if (StringUtils.isNotNull(data)){
            super.put(DATA_TAG, data);
        }
    }

    /**
     * 存储
     * @param key
     * @param value
     * @return
     */
    @Override
    public AjaxResult put(String key, Object value){
        super.put(key, value);
        return this;
    }

    /**
     * 无参成功
     * @return
     */
    public static AjaxResult success(){
        return AjaxResult.success("操作成功");
    }

    /**
     * 返回成功数据
     * @param data
     * @return
     */
    public static AjaxResult success(Object data){
        return AjaxResult.success("操作成功", data);
    }

    /**
     * 返回成功消息
     * @param message
     * @return
     */
    public static AjaxResult success(String message){
        return AjaxResult.success(message, null);
    }

    /**
     * 返回成功消息、数据
     * @param message
     * @param data
     * @return
     */
    public static AjaxResult success(String message, Object data){
        return new AjaxResult(Type.SUCCESS, message, data);
    }

    /**
     * 返回警告信息
     * @param message
     * @return
     */
    public static AjaxResult warn (String message){
        return AjaxResult.warn(message, null);
    }

    /**
     * 返回警告信息、数据
     * @param message
     * @param data
     * @return
     */
    public static AjaxResult warn(String message, Object data){
        return new AjaxResult(Type.WARN, message, data);
    }

    /**
     * 返回错误
     * @return
     */
    public static AjaxResult error(){
        return AjaxResult.error("操作失败");
    }

    /**
     * 返回错误信息
     * @param message
     * @return
     */
    public static AjaxResult error(String message){
        return AjaxResult.error(message, null);
    }

    /**
     * 返回错误信息、数据
     * @param message
     * @param data
     * @return
     */
    public static AjaxResult error(String message, Object data){
        return new AjaxResult(Type.ERROR, message, data);
    }
}
