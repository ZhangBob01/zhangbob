package com.bob.web.system.service.impl;

import com.bob.web.system.domain.SystemUser;
import com.bob.web.system.mapper.SystemUserMapper;
import com.bob.web.system.service.SystemUserService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
