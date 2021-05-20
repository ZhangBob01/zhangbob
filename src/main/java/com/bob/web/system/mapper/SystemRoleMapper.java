package com.bob.web.system.mapper;

import com.bob.web.system.domain.SystemRole;

import java.util.List;

/**
 * 角色表
 * 数据层
 */
public interface SystemRoleMapper {

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

    /**
     * 根据Id删除角色
     * @param roleId
     * @return
     */
    int deleteRoleById (Long roleId);

    /**
     * 批量删除角色
     * @param ids
     * @return
     */
    int deleteRoleByIds (Long[] ids);

    /**
     * 更新角色
     * @param role
     * @return
     */
    int updateRole (SystemRole role);

    /**
     * 新增角色
     * @param role
     * @return
     */
    int insertRole (SystemRole role);

    /**
     * 校验角色名称是否唯一
     * @param roleName
     * @return
     */
    SystemRole checkRoleNameUnique (String roleName);

    /**
     * 校验角色权限是否唯一
     * @param roleKey
     * @return
     */
    SystemRole checkRoleKeyUnique (String roleKey);
}
