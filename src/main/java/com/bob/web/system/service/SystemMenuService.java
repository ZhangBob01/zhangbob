package com.bob.web.system.service;

import com.bob.common.core.domain.Ztree;
import com.bob.web.system.domain.SystemMenu;
import com.bob.web.system.domain.SystemRole;
import com.bob.web.system.domain.SystemUser;

import java.util.List;
import java.util.Set;

/**
 * 系统菜单业务逻辑层
 */
public interface SystemMenuService {

    List<SystemMenu> findMenuListByUser(SystemUser systemUser);

    List<SystemMenu> findMenuListByUserId(Long userId);

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    Set<String> selectPermsByUserId(Long userId);

    /**
     * 根据角色ID查询菜单
     *
     * @param role
     * @param userId
     * @return
     */
    List<Ztree> roleMenuTreeData(SystemRole role, Long userId);

    /**
     * 查询所有菜单列表树
     *
     * @param userId
     * @param userId
     * @return
     */
    List<Ztree> menuTreeData(Long userId);

    /**
     * 查询菜单集合
     *
     * @param menu
     * @param userId
     * @return
     */
    List<SystemMenu> selectMenuList(SystemMenu menu, Long userId);

    /**
     * 根据ID查询菜单
     *
     * @param parentId
     * @return
     */
    SystemMenu selectMenuById(Long parentId);

    /**
     * 检查菜单名称
     *
     * @param menu
     * @return
     */
    String checkMenuNameUnique(SystemMenu menu);

    /**
     * 新增菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    int insertMenu(SystemMenu menu);

    /**
     * 修改保存菜单信息
     *
     * @param menu 菜单信息
     * @return 结果
     */
    int updateMenu(SystemMenu menu);

    /**
     * 查询菜单数量
     *
     * @param parentId 菜单父ID
     * @return 结果
     */
    int selectCountMenuByParentId(Long parentId);

    /**
     * 查询菜单使用数量
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    int selectCountRoleMenuByMenuId(Long menuId);

    /**
     * 删除菜单
     * @param menuId
     * @return
     */
    int deleteMenuById(Long menuId);
}
