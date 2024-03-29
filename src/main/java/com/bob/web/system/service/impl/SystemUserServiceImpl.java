package com.bob.web.system.service.impl;

import com.bob.common.annotation.DataScope;
import com.bob.common.constant.UserConstants;
import com.bob.common.core.text.Convert;
import com.bob.common.exception.BusinessException;
import com.bob.common.utils.StringUtils;
import com.bob.common.utils.security.Md5Utils;
import com.bob.web.system.domain.*;
import com.bob.web.system.mapper.*;
import com.bob.web.system.service.SystemConfigService;
import com.bob.web.system.service.SystemUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    @Autowired
    private SystemUserPostMapper userPostMapper;
    @Autowired
    private SystemUserRoleMapper userRoleMapper;
    @Autowired
    private SystemConfigService configService;

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
     * 根据条件分页查询用户列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    public List<SystemUser> selectUserList(SystemUser user) {
        return systemUserMapper.selectUserList(user);
    }

    /**
     * 查询用户所属角色组
     *
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
     *
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
     *
     * @param userId
     * @return
     */
    @Override
    public SystemUser findUserById(Long userId) {

        return systemUserMapper.findUserById(userId);
    }

    /**
     * 校验登录名称是否唯一
     *
     * @param loginName 用户名
     * @return
     */
    @Override
    public String checkLoginNameUnique(String loginName) {
        int count = systemUserMapper.checkLoginNameUnique(loginName);
        if (count > 0) {
            return UserConstants.USER_NAME_NOT_UNIQUE;
        }
        return UserConstants.USER_NAME_UNIQUE;
    }

    /**
     * 校验email是否唯一
     *
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
     *
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
     *
     * @param user
     * @return
     */
    @Override
    public int resetUserPwd(SystemUser user) {
        return updateUserInfo(user);
    }

    /**
     * 新增保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int insertUser(SystemUser user) {
        // 新增用户信息
        int rows = systemUserMapper.insertUser(user);
        // 新增用户岗位关联
        insertUserPost(user);
        // 新增用户与角色管理
        insertUserRole(user.getUserId(), user.getRoleIds());
        return rows;
    }

    /**
     * 新增用户岗位信息
     *
     * @param user 用户对象
     */
    public void insertUserPost(SystemUser user) {
        Long[] posts = user.getPostIds();
        if (StringUtils.isNotNull(posts)) {
            // 新增用户与岗位管理
            List<SystemUserPost> list = new ArrayList<SystemUserPost>();
            for (Long postId : posts) {
                SystemUserPost up = new SystemUserPost();
                up.setUserId(user.getUserId());
                up.setPostId(postId);
                list.add(up);
            }
            if (list.size() > 0) {
                userPostMapper.batchUserPost(list);
            }
        }
    }

    /**
     * 新增用户角色信息
     *
     * @param userId
     * @param roleIds
     */
    public void insertUserRole(Long userId, Long[] roleIds) {
        if (StringUtils.isNotNull(roleIds)) {
            // 新增用户与角色管理
            List<SystemUserRole> list = new ArrayList<>();
            for (Long roleId : roleIds) {
                SystemUserRole ur = new SystemUserRole();
                ur.setUserId(userId);
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0) {
                userRoleMapper.batchUserRole(list);
            }
        }
    }

    /**
     * 修改保存用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateUser(SystemUser user) {
        Long userId = user.getUserId();
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        insertUserRole(user.getUserId(), user.getRoleIds());
        // 删除用户与岗位关联
        userPostMapper.deleteUserPostByUserId(userId);
        // 新增用户与岗位管理
        insertUserPost(user);
        return systemUserMapper.updateUser(user);
    }

    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    @Override
    public void checkUserAllowed(SystemUser user) {
        if (StringUtils.isNotNull(user.getUserId()) && user.isAdmin()) {
            throw new BusinessException("不允许操作超级管理员用户");
        }
    }

    /**
     * 根据用户id批量删除用户
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional
    public int deleteUserByIds(String ids) {
        Long[] userIds = Convert.toLongArray(ids);
        for (Long userId : userIds) {
            checkUserAllowed(new SystemUser(userId));
        }
        // 删除用户与角色关联
        userRoleMapper.deleteUserRole(userIds);
        // 删除用户与岗位关联
        userPostMapper.deleteUserPost(userIds);
        return systemUserMapper.deleteUserByIds(userIds);
    }

    /**
     * 导入用户数据
     *
     * @param userList        用户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName        操作用户
     * @return 结果
     */
    @Override
    public String importUser(List<SystemUser> userList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(userList) || userList.size() == 0) {
            throw new BusinessException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        String password = configService.selectConfigByKey("sys.user.initPassword");
        for (SystemUser user : userList) {
            try {
                // 验证是否存在这个用户
                SystemUser u = systemUserMapper.selectUserByLoginName(user.getLoginName());
                if (StringUtils.isNull(u)) {
                    user.setPassword(Md5Utils.hash(user.getLoginName() + password));
                    user.setCreateBy(operName);
                    this.insertUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getLoginName() + " 导入成功");
                } else if (isUpdateSupport) {
                    user.setUpdateBy(operName);
                    this.updateUser(user);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、账号 " + user.getLoginName() + " 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、账号 " + user.getLoginName() + " 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、账号 " + user.getLoginName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new BusinessException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    /**
     * 用户授权角色
     *
     * @param userId  用户ID
     * @param roleIds 角色组
     */
    @Override
    public void insertUserAuth(Long userId, Long[] roleIds) {
        userRoleMapper.deleteUserRoleByUserId(userId);
        insertUserRole(userId, roleIds);
    }

    /**
     * 根据条件分页查询已分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SystemUser> selectAlllocatedList(SystemUser user) {
        return systemUserMapper.selectAllocatedList(user);
    }

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SystemUser> selectUnalllocatedList(SystemUser user) {
        return systemUserMapper.selectUnallocatedList(user);
    }

}
