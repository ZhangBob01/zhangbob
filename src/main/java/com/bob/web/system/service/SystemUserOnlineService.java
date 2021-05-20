package com.bob.web.system.service;

import com.bob.web.system.domain.SystemUserOnline;

import java.util.Date;
import java.util.List;

/**
 * 在线用户 service层
 */
public interface SystemUserOnlineService {
    /**
     * 通过会话序号查询信息
     *
     * @param sessionId 会话ID
     * @return 在线用户信息
     */
    SystemUserOnline selectOnlineById(String sessionId);

    /**
     * 通过会话序号删除信息
     *
     * @param sessionId 会话ID
     * @return 在线用户信息
     */
    void deleteOnlineById(String sessionId);

    /**
     * 通过会话序号删除信息
     *
     * @param sessions 会话ID集合
     * @return 在线用户信息
     */
    void batchDeleteOnline(List<String> sessions);

    /**
     * 保存会话信息
     *
     * @param online 会话信息
     */
    void saveOnline(SystemUserOnline online);

    /**
     * 查询会话集合
     *
     * @param userOnline 分页参数
     * @return 会话集合
     */
    List<SystemUserOnline> selectUserOnlineList(SystemUserOnline userOnline);

    /**
     * 强退用户
     *
     * @param sessionId 会话ID
     */
    void forceLogout(String sessionId);

    /**
     * 清理用户缓存
     *
     * @param loginName 登录名称
     * @param sessionId 会话ID
     */
    void removeUserCache(String loginName, String sessionId);

    /**
     * 查询会话集合
     *
     * @param expiredDate 有效期
     * @return 会话集合
     */
    List<SystemUserOnline> selectOnlineByExpired(Date expiredDate);
}
