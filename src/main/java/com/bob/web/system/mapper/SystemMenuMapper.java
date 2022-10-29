package com.bob.web.system.mapper;

import com.bob.web.system.domain.SystemMenu;

import java.util.List;

/**
 * 系统菜单数据层
 */
public interface SystemMenuMapper {

    /**
     * 查询所有菜单
     * @return
     */
    List<SystemMenu> findMenuListAll();

    /**
     * 根据用户id查询菜单列表
     * @return
     */
    List<SystemMenu> findMenuListByUserId(Long userId);

    /**
     * 根据用户id查询权限
     * @param userId
     * @return
     */
    List<String> selectPermsByUserId(Long userId);

    /**
     * 根据角色ID查询菜单
     *
     * @param roleId 角色ID
     * @return 菜单列表
     */
    List<String> selectMenuTree(Long roleId);
}
