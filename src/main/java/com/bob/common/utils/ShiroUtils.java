package com.bob.common.utils;

import com.bob.common.utils.bean.BeanUtils;
import com.bob.web.system.domain.SystemUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * @author: zhang bob
 * @date: 2021-05-17 15:51
 * @description: shiro工具类
 */
public class ShiroUtils {

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static String getIp() {
        return getSubject().getSession().getHost();
    }

    public static SystemUser getSysUser() {
        SystemUser user = null;
        Object object = getSubject().getPrincipal();
        if (StringUtils.isNotNUll(object)) {
            user = new SystemUser();
            BeanUtils.copyBeanProp(user, object);
        }
        return user;
    }

    public static String getSessionId() {
        return String.valueOf(getSubject().getSession().getId());
    }

    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }
}
