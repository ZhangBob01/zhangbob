package com.bob.web.system.domain;

import lombok.Data;

/**
 * @author: zhang bob
 * @date: 2022-10-20 09:04
 * @description: 角色和部门关联 sys_role_dept
 */
@Data
public class SystemRoleDept {
    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 部门ID
     */
    private Long deptId;
}
