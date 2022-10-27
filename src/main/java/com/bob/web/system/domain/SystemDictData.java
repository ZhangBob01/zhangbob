package com.bob.web.system.domain;

import com.bob.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * @author: zhang bob
 * @date: 2022-10-19 08:34
 * @description:
 */
@Data
public class SystemDictData extends BaseEntity {

    /** 字典编码 */
    private Long dictCode;

    /** 字典排序 */
    private Long dictSort;

    /** 字典标签 */
    private String dictLabel;

    /** 字典键值 */
    private String dictValue;

    /** 字典类型 */
    private String dictType;

    /** 样式属性（其他样式扩展） */
    private String cssClass;

    /** 表格字典样式 */
    private String listClass;

    /** 是否默认（Y是 N否） */
    private String isDefault;

    /** 状态（0正常 1停用） */
    private String status;
}