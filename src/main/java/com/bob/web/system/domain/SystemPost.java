package com.bob.web.system.domain;

import com.bob.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * @author: zhang bob
 * @date: 2021-06-04 16:39
 * @description: 岗位表 sys_post
 */
@Data
public class SystemPost extends BaseEntity {
    private static final long serialVersionUID = 1l;

    /** 岗位表Id. */
    private Long postId;

    /** 岗位编码. */
    private String postCode;

    /** 岗位名称 */
    private String postName;

    /** 岗位排序 */
    private String postSort;

    /** 状态（0正常 1停用） */
    private String status;

    /** 用户是否存在此岗位标识 默认不存在 */
    private boolean flag = false;
}
