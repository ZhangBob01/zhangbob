package com.bob.web.system.service;

import com.bob.web.system.domain.SystemUser;

/**
 * 用户信息接口
 */
public interface SystemUserService {
    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    SystemUser findUserByUsername(String username);

    /**
     * 根据登录名获取用户信息
     * @param loginname
     * @return
     */
    SystemUser findUserByLoginname(String loginname);

    /**
     * 修改用户详细信息
     * @param user
     */
    int updateUserInfo(SystemUser user);
}
