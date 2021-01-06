package com.bob.common.core.page;

import com.bob.common.utils.StringUtils;
import lombok.Data;

/**
 * @author: zhang bob
 * @date: 2020-12-24 15:57
 * @description: 分页实体类
 */
@Data
public class PageDomain {
    /** 当前页数. */
    private Integer pageNum;
    /** 每页显示数据数. */
    private Integer pageSize;
    /** 排序列. */
    private String orderByColumn;
    /** 正序倒序. */
    private String isAsc = "asc";

    /**
     * 排序
     * @return
     */
    public String getOrderBy(){
        if (StringUtils.isEmpty(orderByColumn)){
           return "";
        }
        return StringUtils.toUnderScoreCase(orderByColumn) + " " + isAsc;
    }

}
