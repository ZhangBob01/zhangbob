package com.bob.common.utils;

import com.bob.common.utils.bean.BeanUtils;
import com.bob.web.system.domain.SystemUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
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

    /**
     * 获取shiro用户信息
     *
     * @return
     */
    public static SystemUser getSysUser() {
        SystemUser user = null;
        Object object = getSubject().getPrincipal();
        if (StringUtils.isNotNull(object)) {
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

    /**
     * 设置shiro用户信息
     *
     * @param user
     */
    public static void setSystemUser(SystemUser user) {
        Subject subject = getSubject();
        PrincipalCollection principalCollection = subject.getPrincipals();
        String realmName = principalCollection.getRealmNames().iterator().next();
        PrincipalCollection newPrincipalCollection = new SimplePrincipalCollection(user, realmName);
        // 重新加载
        subject.runAs(newPrincipalCollection);
    }

    /**
     * 随机生成校验盐
     *
     * @return
     */
    public static String randomSalt() {
        SecureRandomNumberGenerator secureRandomNumberGenerator = new SecureRandomNumberGenerator();
        String hox = secureRandomNumberGenerator.nextBytes(3).toHex();
        return hox;
    }

    public static String getLoginName() {
        return getSysUser().getLoginName();
    }

    /**
     * 获取登录用户id
     * @return
     */
    public static Long getUserId() {
        return getSysUser().getUserId();
    }
}
