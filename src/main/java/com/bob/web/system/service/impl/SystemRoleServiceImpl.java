package com.bob.web.system.service.impl;


import com.bob.web.system.domain.SystemRole;
import com.bob.web.system.mapper.SystemRoleMapper;
import com.bob.web.system.service.SystemRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: zhang bob
 * @date: 2021-05-17 10:41
 * @description: 角色业务逻辑实现类
 */
@Service
public class SystemRoleServiceImpl implements SystemRoleService {
    @Autowired
    private SystemRoleMapper roleMapper;

    @Override
    public List<SystemRole> findRoleList(SystemRole role) {
        return roleMapper.findRoleList(role);
    }

    @Override
    public List<SystemRole> findRoleListByUserId(Long userId) {
        return roleMapper.findRoleListByUserId(userId);
    }

    @Override
    public SystemRole findRoleById(Long roleId) {
        return roleMapper.findRoleById(roleId);
    }
}
