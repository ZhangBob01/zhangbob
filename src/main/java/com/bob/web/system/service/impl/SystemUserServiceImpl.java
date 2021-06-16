package com.bob.web.system.service.impl;

import com.bob.common.constant.UserConstants;
import com.bob.common.utils.StringUtils;
import com.bob.web.system.domain.SystemPost;
import com.bob.web.system.domain.SystemRole;
import com.bob.web.system.domain.SystemUser;
import com.bob.web.system.mapper.SystemPostMapper;
import com.bob.web.system.mapper.SystemRoleMapper;
import com.bob.web.system.mapper.SystemUserMapper;
import com.bob.web.system.service.SystemUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.StringJoiner;

/**
 * @author: zhang bob
 * @date: 2021-01-27 11:53
 * @description: 用户业务逻辑类
 */
@Service
@Slf4j
public class SystemUserServiceImpl implements SystemUserService {
    @Autowired
    private SystemUserMapper systemUserMapper;
    @Autowired
    private SystemRoleMapper systemRoleMapper;
    @Autowired
    private SystemPostMapper systemPostMapper;

    @Override
    public SystemUser findUserByUsername(String username) {
        return systemUserMapper.findUserByUsername(username);
    }

    @Override
    public SystemUser findUserByLoginname(String loginname) {
        return systemUserMapper.findUserByLoginname(loginname);
    }

    @Override
    public int updateUserInfo(SystemUser user) {
        return systemUserMapper.updateUser(user);
    }

    /**
     * 查询用户所属角色组
     * @param userId
     * @return
     */
    @Override
    public String findUserRoleGroup(Long userId) {
        // 查询用户角色列表
        List<SystemRole> systemRoleList = systemRoleMapper.findRoleListByUserId(userId);
        // Java 8新技术
        StringJoiner stringJoiner = new StringJoiner(",");
        systemRoleList.stream().forEach(e -> {
            stringJoiner.add(e.getRoleName());
        });
        return stringJoiner.toString();
    }

    /**
     * 查询用户所属岗位组
     * @param userId
     * @return
     */
    @Override
    public String findUserPostGroup(Long userId) {
        // 查询用户岗位
        List<SystemPost> systemPostList = systemPostMapper.findPostListByUserId(userId);
        // Java 8新技术
        StringJoiner stringJoiner = new StringJoiner(",");
        systemPostList.stream().forEach(e -> {
            stringJoiner.add(e.getPostName());
        });
        return stringJoiner.toString();
    }

    /**
     * 根据id查询用户信息
     * @param userId
     * @return
     */
    @Override
    public SystemUser findUserById(Long userId) {

        return systemUserMapper.findUserById(userId);
    }

    /**
     * 校验email是否唯一
     * @param user
     * @return
     */
    @Override
    public String checkEmailUnique(SystemUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        // 根据email获取用户信息
        List<SystemUser> systemUserList = systemUserMapper.findUserListByEmail(user.getEmail());

        // 判断用户列表，若大于1则不唯一
        if (systemUserList != null && systemUserList.size() > 1) {
            return UserConstants.USER_EMAIL_NOT_UNIQUE;
        }
        // 判断用户列表，若等于1，切id不相等，则不唯一
        if (systemUserList != null && systemUserList.size() == 1 && systemUserList.get(0).getUserId().longValue() != userId.longValue()) {
            return UserConstants.USER_EMAIL_NOT_UNIQUE;
        }
        return UserConstants.USER_EMAIL_UNIQUE;
    }

    /**
     * 校验手机号是否唯一
     * @param user
     * @return
     */
    @Override
    public String checkPhoneUnique(SystemUser user) {
        Long userId = StringUtils.isNull(user.getUserId()) ? -1L : user.getUserId();
        // 根据手机号获取用户信息
        List<SystemUser> systemUserList = systemUserMapper.findUserListByPhone(user.getPhonenumber());

        // 判断用户列表，若大于1则不唯一
        if (systemUserList != null && systemUserList.size() > 1) {
            return UserConstants.USER_EMAIL_NOT_UNIQUE;
        }
        // 判断用户列表，若等于1，切id不相等，则不唯一
        if (systemUserList != null && systemUserList.size() == 1 && systemUserList.get(0).getUserId().longValue() != userId.longValue()) {
            return UserConstants.USER_EMAIL_NOT_UNIQUE;
        }
        return UserConstants.USER_EMAIL_UNIQUE;
    }

    /**
     * 更新用户密码
     * @param user
     * @return
     */
    @Override
    public int resetUserPwd(SystemUser user) {
        return updateUserInfo(user);
    }
}
