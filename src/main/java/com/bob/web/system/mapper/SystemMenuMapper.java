package com.bob.web.system.mapper;

import com.bob.web.system.domain.SystemMenu;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 查询系统菜单列表
     *
     * @param menu 菜单信息
     * @return 菜单列表
     */
    List<SystemMenu> selectMenuList(SystemMenu menu);

    /**
     * 查询系统菜单列表
     *
     * @param menu 菜单信息
     * @return 菜单列表
     */
    List<SystemMenu> selectMenuListByUserId(SystemMenu menu);

    /**
     * 根据id查询菜单
     *
     * @param menuId
     * @return
     */
    SystemMenu selectMenuById(Long menuId);

    /**
     * 校验菜单名称是否唯一
     *
     * @param menuName 菜单信息
     * @param parentId 父菜单Id
     * @return 结果
     */
    SystemMenu checkMenuNameUnique(@Param("menuName") String menuName, @Param("parentId") Long parentId);

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
     * @param menu
     * @return
     */
    int updateMenu(SystemMenu menu);

    /**
     * 查询子菜单数量
     * @param parentId
     * @return
     */
    int selectCountMenuByParentId(Long parentId);

    /**
     * 删除菜单管理信息
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    int deleteMenuById(Long menuId);
}
