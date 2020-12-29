package com.bob.common.core.controller;

import com.bob.common.utils.DateUtils;
import lombok.extern.java.Log;
import org.springframework.web.bind.WebDataBinder;

import java.beans.PropertyEditorSupport;
import java.util.Date;

/**
 * web层基础数据处理
 */
@Log
public class BaseController {

    /**
     * 处理前台日期格式的String，自动转化为Date
     * @param webDataBinder
     */
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.registerCustomEditor(Date.class, new PropertyEditorSupport(){
            @Override
            public void setAsText(String text){
                setValue(DateUtils.parseDate(text));
            }
        });
    }

    /**
     * 设置数据分页
     */
    protected void startPage() {

    }
}

