package com.bob.web.system.service;

import com.bob.web.system.domain.SystemMenu;
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
}
