package com.bob.web.system.service.impl;

import com.bob.common.constant.ShiroConstants;
import com.bob.common.utils.DateUtils;
import com.bob.common.utils.StringUtils;
import com.bob.web.system.domain.SystemUserOnline;
import com.bob.web.system.mapper.SystemUserOnlineMapper;
import com.bob.web.system.service.SystemUserOnlineService;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.Deque;
import java.util.List;

/**
 * @author: zhang bob
 * @date: 2021-05-18 16:23
 * @description: 在线用户
 */
@Service
public class SystemUserOnlineServiceImpl implements SystemUserOnlineService {

    @Autowired
    private SystemUserOnlineMapper userOnlineMapper;
    @Autowired
    private EhCacheManager ehCacheManager;


    @Override
    public SystemUserOnline selectOnlineById(String sessionId) {
        return userOnlineMapper.selectOnlineById(sessionId);
    }

    @Override
    public void deleteOnlineById(String sessionId) {
        SystemUserOnline userOnline = selectOnlineById(sessionId);
        if (StringUtils.isNotNUll(userOnline)) {
            userOnlineMapper.deleteOnlineById(sessionId);
        }
    }

    @Override
    public void batchDeleteOnline(List<String> sessions) {
        for (String sessionId : sessions) {
            SystemUserOnline userOnline = selectOnlineById(sessionId);
            if (StringUtils.isNotNUll(userOnline)) {
                userOnlineMapper.deleteOnlineById(sessionId);
            }
        }
    }

    @Override
    public void saveOnline(SystemUserOnline online) {
        userOnlineMapper.saveOnline(online);
    }

    @Override
    public List<SystemUserOnline> selectUserOnlineList(SystemUserOnline userOnline) {
        return userOnlineMapper.selectUserOnlineList(userOnline);
    }

    @Override
    public void forceLogout(String sessionId) {
        userOnlineMapper.deleteOnlineById(sessionId);
    }

    @Override
    public void removeUserCache(String loginName, String sessionId) {
        Cache<String, Deque<Serializable>> cache = ehCacheManager.getCache(ShiroConstants.SYS_USERCACHE);
        Deque<Serializable> deque = cache.get(loginName);
        if (StringUtils.isEmpty(deque) || deque.size() == 0) {
            return;
        }
        deque.remove(sessionId);
    }

    @Override
    public List<SystemUserOnline> selectOnlineByExpired(Date expiredDate) {
        String lastAccessTime = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, expiredDate);
        return userOnlineMapper.selectOnlineByExpired(lastAccessTime);
    }
}
