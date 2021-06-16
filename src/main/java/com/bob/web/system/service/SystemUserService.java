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

    /**
     * 根据用户Id查询用户角色组
     * @param userId
     * @return
     */
    String findUserRoleGroup(Long userId);

    /**
     * 查询用户所属岗位组
     * @param userId
     * @return
     */
    String findUserPostGroup(Long userId);

    /**
     * 根据Id查询用户信息
     * @param userId
     * @return
     */
    SystemUser findUserById(Long userId);

    /**
     * 校验email地址是否唯一
     * @param user
     * @return
     */
    String checkEmailUnique(SystemUser user);

    /**
     * 校验手机号是否唯一
     * @param user
     * @return
     */
    String checkPhoneUnique(SystemUser user);

    /**
     * 更新用户密码
     * @param user
     * @return
     */
    int resetUserPwd(SystemUser user);
}
