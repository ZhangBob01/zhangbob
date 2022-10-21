package com.bob.framework.manager.factory;

import com.bob.common.constant.Constants;
import com.bob.common.utils.*;
import com.bob.common.utils.spring.SpringUtils;
import com.bob.framework.shiro.session.OnlineSession;
import com.bob.web.system.domain.SystemLogininfor;
import com.bob.web.system.domain.SystemOperLog;
import com.bob.web.system.domain.SystemUserOnline;
import com.bob.web.system.service.SystemOperLogService;
import com.bob.web.system.service.SystemUserOnlineService;
import com.bob.web.system.service.impl.SystemLogininforServiceImpl;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;

import java.util.TimerTask;

/**
 * @author: zhang bob
 * @date: 2021-05-17 13:11
 * @description: 异步工厂
 */
@Slf4j
public class AsyncFactory {

    /**
     * 记录登录信息
     *
     * @param username 用户名
     * @param status   状态
     * @param message  消息
     * @param args     列表
     * @return 任务task
     */
    public static TimerTask recordLogininfor(final String username, final String status, final String message, final Object... args) {
        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        final String ip = ShiroUtils.getIp();
        return new TimerTask() {
            @Override
            public void run() {
                String address = AddressUtils.getRealAddressByIP(ip);
                StringBuilder s = new StringBuilder();
                s.append(LogUtils.getBlock(ip));
                s.append(address);
                s.append(LogUtils.getBlock(username));
                s.append(LogUtils.getBlock(status));
                s.append(LogUtils.getBlock(message));
                // 打印信息到日志
                log.info(s.toString(), args);
                // 获取客户端操作系统
                String os = userAgent.getOperatingSystem().getName();
                // 获取客户端浏览器
                String browser = userAgent.getBrowser().getName();
                // 封装对象
                SystemLogininfor logininfor = new SystemLogininfor();
                logininfor.setLoginName(username);
                logininfor.setIpaddr(ip);
                logininfor.setLoginLocation(address);
                logininfor.setBrowser(browser);
                logininfor.setOs(os);
                logininfor.setMsg(message);
                // 日志状态
                if (StringUtils.equalsAny(status, Constants.LOGIN_SUCCESS, Constants.LOGOUT, Constants.REGISTER)) {
                    logininfor.setStatus(Constants.SUCCESS);
                } else if (Constants.LOGIN_FAIL.equals(status)) {
                    logininfor.setStatus(Constants.FAIL);
                }
                // 插入数据
                SpringUtils.getBean(SystemLogininforServiceImpl.class).insertLogininfor(logininfor);
            }
        };
    }

    /**
     * 同步session到数据库
     *
     * @param onlineSession 在线用户会话
     * @return 任务task
     */
    public static TimerTask syncSessionToDb(final OnlineSession onlineSession) {
        return new TimerTask() {
            @Override
            public void run() {
                SystemUserOnline online = new SystemUserOnline();
                online.setSessionId(String.valueOf(onlineSession.getId()));
                online.setDeptName(onlineSession.getDeptName());
                online.setLoginName(onlineSession.getLoginName());
                online.setStartTimestamp(onlineSession.getStartTimestamp());
                online.setLastAccessTime(onlineSession.getLastAccessTime());
                online.setExpireTime(onlineSession.getTimeout());
                online.setIpaddr(onlineSession.getHost());
                online.setLoginLocation(AddressUtils.getRealAddressByIP(onlineSession.getHost()));
                online.setBrowser(onlineSession.getBrowser());
                online.setOs(onlineSession.getOs());
                online.setStatus(onlineSession.getStatus());
                SpringUtils.getBean(SystemUserOnlineService.class).saveOnline(online);

            }
        };
    }

    /**
     * 操作日志记录
     *
     * @param operLog 操作日志信息
     * @return 任务task
     */
    public static TimerTask recordOper(final SystemOperLog operLog) {
        return new TimerTask() {
            @Override
            public void run() {
                // 远程查询操作地点
                operLog.setOperLocation(AddressUtils.getRealAddressByIP(operLog.getOperIp()));
                SpringUtils.getBean(SystemOperLogService.class).insertOperlog(operLog);
            }
        };
    }
}
