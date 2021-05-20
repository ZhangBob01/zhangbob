package com.bob.web.system.service;

import com.bob.web.system.domain.SystemRole;

import java.util.List;

/**
 * 角色业务逻辑
 */
public interface SystemRoleService {

    /**
     * 根据实体查询角色数据
     * @param role 角色实体
     * @return
     */
    List<SystemRole> findRoleList(SystemRole role);

    /**
     * 根据用户Id查询角色
     * @param userId
     * @return
     */
    List<SystemRole> findRoleListByUserId(Long userId);

    /**
     * 根据id查询角色
     * @param roleId
     * @return
     */
    SystemRole findRoleById(Long roleId);

}
