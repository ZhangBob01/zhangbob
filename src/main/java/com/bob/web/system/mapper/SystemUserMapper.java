package com.bob.web.system.mapper;

import com.bob.web.system.domain.SystemUser;

import java.util.List;

/**
 * @author: zhang bob
 * @date: 2021-01-27 11:58
 * @description:
 */
public interface SystemUserMapper {
    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    SystemUser findUserByUsername(String username);

    /**
     * 根据登录名查询用户信息
     * @param loginname
     * @return
     */
    SystemUser findUserByLoginname(String loginname);

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    int updateUser(SystemUser user);

    /**
     * 根据id查询用户信息
     * @param userId
     * @return
     */
    SystemUser findUserById(Long userId);

    /**
     * 根据Email获取用户信息
     * @param email
     * @return
     */
    List<SystemUser> findUserListByEmail(String email);

    /**
     * 根据手机号获取用户信息
     * @param phonenumber
     * @return
     */
    List<SystemUser> findUserListByPhone(String phonenumber);
}
