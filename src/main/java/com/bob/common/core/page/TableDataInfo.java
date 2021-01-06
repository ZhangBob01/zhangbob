package com.bob.common.core.page;

import lombok.Data;

import java.util.List;

/**
 * @author: zhang bob
 * @date: 2021-01-06 14:00
 * @description: 表格分页数据对象
 */
@Data
public class TableDataInfo {
    private static final long serialVersionUID = 1L;

    /** 总记录数. */
    private long total;
    /** 列表数据. */
    private List<?> rows;
    /** 消息状态码. */
    private int code;
    /** 消息内容. */
    private String message;

    /**
     * 无参构造方法
     */
    public TableDataInfo() {
    }

    public TableDataInfo(List<?> rows, long total) {
        this.total = total;
        this.rows = rows;
    }
}
