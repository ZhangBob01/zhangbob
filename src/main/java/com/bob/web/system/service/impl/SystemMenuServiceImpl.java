package com.bob.web.system.service.impl;

import com.bob.common.core.domain.Ztree;
import com.bob.common.utils.StringUtils;
import com.bob.web.system.domain.SystemMenu;
import com.bob.web.system.domain.SystemRole;
import com.bob.web.system.domain.SystemUser;
import com.bob.web.system.mapper.SystemMenuMapper;
import com.bob.web.system.service.SystemMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author: zhang bob
 * @date: 2021-01-29 14:27
 * @description: 系统菜单业务逻辑层
 */
@Service
@Slf4j
public class SystemMenuServiceImpl implements SystemMenuService {

    @Autowired
    private SystemMenuMapper systemMenuMapper;
    @Override
    public List<SystemMenu> findMenuListByUser(SystemUser systemUser) {

        List<SystemMenu> systemMenuList = new ArrayList<>();
        // 检查用户是否为管理员
        if (systemUser.isAdmin()) {
            systemMenuList = systemMenuMapper.findMenuListAll();
        } else {
            systemMenuList = systemMenuMapper.findMenuListByUserId(systemUser.getUserId());
        }
        List<SystemMenu> parentChildMenuList = getParentChildMenuList(systemMenuList, 0L);
        return parentChildMenuList;
    }

    @Override
    public List<SystemMenu> findMenuListByUserId(Long userId) {

        return  systemMenuMapper.findMenuListByUserId(userId);
    }

    @Override
    public Set<String> selectPermsByUserId(Long userId) {

        List<String> perms = systemMenuMapper.selectPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms)
        {
            if (StringUtils.isNotEmpty(perm))
            {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 查询所有菜单列表树
     *
     * @param role
     * @param userId 用户id
     * @return
     */
    @Override
    public List<Ztree> menuTreeData(SystemRole role, Long userId) {
        Long roleId = role.getRoleId();
        List<Ztree> ztreeList;
        List<SystemMenu> menuList = selectMenuAll(userId);
        if (StringUtils.isNotNull(roleId)) {
            List<String> roleMenuList = systemMenuMapper.selectMenuTree(roleId);
            ztreeList = initZtree(menuList, roleMenuList, true);
        } else {
            ztreeList = initZtree(menuList, null, true);
        }
        return ztreeList;
    }

    /**
     * 对象转菜单树
     *
     * @param menuList 菜单列表
     * @param roleMenuList 角色已存在菜单列表
     * @param permsFlag 是否需要显示权限标识
     * @return 树结构列表
     */
    private List<Ztree> initZtree(List<SystemMenu> menuList, List<String> roleMenuList, boolean permsFlag) {
        List<Ztree> ztreeList = new ArrayList<>();
        boolean isCheck = StringUtils.isNotNull(roleMenuList);
        for (SystemMenu menu : menuList) {
            Ztree ztree = new Ztree();
            ztree.setId(menu.getMenuId());
            ztree.setpId(menu.getParentId());
            ztree.setName(transMenuName(menu, permsFlag));
            ztree.setTitle(menu.getMenuName());
            if (isCheck) {
                ztree.setChecked(roleMenuList.contains(menu.getMenuId() + menu.getPerms()));
            }
            ztreeList.add(ztree);
        }
        return ztreeList;
    }


    private String transMenuName(SystemMenu menu, boolean permsFlag) {
        StringBuffer sb = new StringBuffer();
        sb.append(menu.getMenuName());
        if (permsFlag)
        {
            sb.append("<font color=\"#888\">&nbsp;&nbsp;&nbsp;" + menu.getPerms() + "</font>");
        }
        return sb.toString();
    }

    /**
     * 根据用户id查询菜单
     * @param userId
     * @return
     */
    private List<SystemMenu> selectMenuAll(Long userId) {
        List<SystemMenu> menuList = null;
        // 判断是否为管理员
        if (SystemUser.isAdmin(userId)) {
            menuList = systemMenuMapper.findMenuListAll();
        } else {
            menuList = systemMenuMapper.findMenuListByUserId(userId);
        }
        return menuList;
    }

    /**
     * 获取子菜单
     * @param systemMenuList
     * @param parentId
     * @return
     */
    private List<SystemMenu> getParentChildMenuList(List<SystemMenu> systemMenuList, Long parentId) {
        // 创建子菜单列表
        List<SystemMenu> parentChildMenuList = new ArrayList<>();
        // 遍历菜单，封装父子菜单列表
        for (Iterator<SystemMenu> iterator = systemMenuList.iterator(); iterator.hasNext();) {
            SystemMenu systemMenu = iterator.next();
            // 遍历所有子节点
            if (systemMenu.getParentId() == parentId) {
                parentChildMenuList.add(systemMenu);
            }
        }

        // 查询子列表的子列表
        if (parentChildMenuList.size() > 0) {
            for (SystemMenu child : parentChildMenuList) {
                List<SystemMenu> childMenuList = getParentChildMenuList(systemMenuList, child.getMenuId());
                if (childMenuList != null && childMenuList.size() > 0) {
                    child.setChildren(childMenuList);
                }
            }
        }
        return parentChildMenuList;
    }


}
