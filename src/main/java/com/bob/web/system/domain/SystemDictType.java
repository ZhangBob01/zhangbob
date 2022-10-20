package com.bob.web.system.domain;

import com.bob.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * @author: zhang bob
 * @date: 2022-10-19 08:34
 * @description:
 */
@Data
public class SystemDictType extends BaseEntity {

    /** 字典主键 */
    private Long dictId;

    /** 字典名称 */
    private String dictName;

    /** 字典类型 */
    private String dictType;

    /** 状态（0正常 1停用） */
    private String status;
}
