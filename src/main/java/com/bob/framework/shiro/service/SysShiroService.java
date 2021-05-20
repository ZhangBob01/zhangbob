package com.bob.framework.shiro.service;

import java.io.Serializable;

import com.bob.common.utils.StringUtils;
import com.bob.framework.shiro.session.OnlineSession;
import com.bob.web.system.domain.SystemUserOnline;
import com.bob.web.system.service.SystemUserOnlineService;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 会话db操作处理
 */
@Component
public class SysShiroService {
    @Autowired
    private SystemUserOnlineService onlineService;

    /**
     * 删除会话
     *
     * @param onlineSession 会话信息
     */
    public void deleteSession(OnlineSession onlineSession) {
        onlineService.deleteOnlineById(String.valueOf(onlineSession.getId()));
    }

    /**
     * 获取会话信息
     *
     * @param sessionId
     * @return
     */
    public Session getSession(Serializable sessionId) {
        SystemUserOnline userOnline = onlineService.selectOnlineById(String.valueOf(sessionId));
        return StringUtils.isNull(userOnline) ? null : createSession(userOnline);
    }

    public Session createSession(SystemUserOnline userOnline) {
        OnlineSession onlineSession = new OnlineSession();
        if (StringUtils.isNotNUll(userOnline)) {
            onlineSession.setId(userOnline.getSessionId());
            onlineSession.setHost(userOnline.getIpaddr());
            onlineSession.setBrowser(userOnline.getBrowser());
            onlineSession.setOs(userOnline.getOs());
            onlineSession.setDeptName(userOnline.getDeptName());
            onlineSession.setLoginName(userOnline.getLoginName());
            onlineSession.setStartTimestamp(userOnline.getStartTimestamp());
            onlineSession.setLastAccessTime(userOnline.getLastAccessTime());
            onlineSession.setTimeout(userOnline.getExpireTime());
        }
        return onlineSession;
    }
}
