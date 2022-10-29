package com.bob.web.system.service;

import com.bob.web.system.domain.SystemUser;

import java.util.List;

/**
 * 用户信息接口
 */
public interface SystemUserService {
    /**
     * 根据用户名获取用户信息
     *
     * @param username
     * @return
     */
    SystemUser findUserByUsername(String username);

    /**
     * 根据登录名获取用户信息
     *
     * @param loginname
     * @return
     */
    SystemUser findUserByLoginname(String loginname);

    /**
     * 修改用户详细信息
     *
     * @param user
     */
    int updateUserInfo(SystemUser user);

    /**
     * 根据用户Id查询用户角色组
     *
     * @param userId
     * @return
     */
    String findUserRoleGroup(Long userId);

    /**
     * 查询用户所属岗位组
     *
     * @param userId
     * @return
     */
    String findUserPostGroup(Long userId);

    /**
     * 根据Id查询用户信息
     *
     * @param userId
     * @return
     */
    SystemUser findUserById(Long userId);

    /**
     * 校验email地址是否唯一
     *
     * @param user
     * @return
     */
    String checkEmailUnique(SystemUser user);

    /**
     * 校验手机号是否唯一
     *
     * @param user
     * @return
     */
    String checkPhoneUnique(SystemUser user);

    /**
     * 更新用户密码
     *
     * @param user
     * @return
     */
    int resetUserPwd(SystemUser user);

    /**
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    List<SystemUser> selectUserList(SystemUser user);

    /**
     * 校验用户名称是否唯一
     *
     * @param loginName 登录名称
     * @return 结果
     */
    String checkLoginNameUnique(String loginName);

    /**
     * 保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    int insertUser(SystemUser user);

    /**
     * 保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    int updateUser(SystemUser user);

    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    void checkUserAllowed(SystemUser user);

    /**
     * 根据id批量删除用户
     *
     * @param ids 要删除的用户id数组
     * @return
     */
    int deleteUserByIds(String ids);

    /**
     * 导入用户数据
     *
     * @param userList 用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    String importUser(List<SystemUser> userList, Boolean isUpdateSupport, String operName);

    /**
     * 用户授权角色
     *
     * @param userId 用户ID
     * @param roleIds 角色组
     */
    void insertUserAuth(Long userId, Long[] roleIds);

    /**
     * 根据条件分页查询已分配用户角色列表
     *
     * @param user
     * @return
     */
    List<SystemUser> selectAlllocatedList(SystemUser user);

    /**
     * 根据条件分页查询未分配用户角色列表
     * @param user
     * @return
     */
    List<SystemUser> selectUnalllocatedList(SystemUser user);
}
