package com.bob.web.system.domain;

import lombok.Data;

/**
 * @author: zhang bob
 * @date: 2022-10-19 19:31
 * @description: 用户角色关联表 sys_user_role
 */
@Data
public class SystemUserRole {
    /** 用户ID */
    private Long userId;

    /** 角色ID */
    private Long roleId;
}
