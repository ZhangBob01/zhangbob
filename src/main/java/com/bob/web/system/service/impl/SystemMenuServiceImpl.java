package com.bob.web.system.service.impl;

import com.bob.common.utils.StringUtils;
import com.bob.web.system.domain.SystemMenu;
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
