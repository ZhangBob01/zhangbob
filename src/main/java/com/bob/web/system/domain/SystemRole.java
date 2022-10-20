package com.bob.web.system.domain;

import com.bob.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * @author: zhang bob
 * @date: 2021-05-17 09:08
 * @description: 角色表（sys_role）实体
 */
@Data
public class SystemRole extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 角色Id.
     */
    private Long roleId;

    /**
     * 角色名称.
     */
    private String roleName;

    /**
     * 角色权限
     */
    private String roleKey;

    /**
     * 角色排序.
     */
    private String roleSort;

    /**
     * 数据范围（1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限）.
     */
    private String dataScope;

    /**
     * 角色状态（0正常 1停用）.
     */
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）.
     */
    private String delFlag;

    /**
     * 用户是否存在此角色标识 默认不存在.
     */
    private boolean flag = false;

    /**
     * 菜单组.
     */
    private Long[] menuIds;

    /**
     * 部门组（数据权限）.
     */
    private Long[] deptIds;

    public SystemRole() {

    }

    public SystemRole(Long roleId) {
        this.roleId = roleId;
    }

    public boolean isAdmin() {
        return isAdmin(this.roleId);
    }

    public static boolean isAdmin(Long roleId) {
        return roleId != null && 1L == roleId;
    }

}
