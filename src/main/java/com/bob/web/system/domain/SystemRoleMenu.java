package com.bob.web.system.domain;

import lombok.Data;

/**
 * @author: zhang bob
 * @date: 2022-10-20 11:25
 * @description: 角色和菜单关联 sys_role_menu
 */
@Data
public class SystemRoleMenu {
    /** 角色ID */
    private Long roleId;

    /** 菜单ID */
    private Long menuId;
}
