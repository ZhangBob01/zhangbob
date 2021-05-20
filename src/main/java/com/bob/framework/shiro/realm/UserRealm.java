package com.bob.framework.shiro.realm;

import com.bob.common.exception.user.*;
import com.bob.common.utils.ShiroUtils;
import com.bob.framework.shiro.service.SystemLoginService;
import com.bob.web.system.domain.SystemRole;
import com.bob.web.system.domain.SystemUser;
import com.bob.web.system.service.SystemMenuService;
import com.bob.web.system.service.SystemRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: zhang bob
 * @date: 2021-05-17 09:03
 * @description: 用户realm
 */
@Slf4j
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private SystemMenuService menuService;
    @Autowired
    private SystemRoleService roleService;
    @Autowired
    private SystemLoginService loginService;

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SystemUser user = ShiroUtils.getSysUser();
        // 角色列表
        Set<String> roleSet = new HashSet<>();
        // 功能列表
        Set<String> menuSet = new HashSet<>();

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 判断是否管理员
        if (user.isAdmin()) {
            info.addRole("admin");
            info.addStringPermission("*:*:*");
        } else {
            List<SystemRole> roleList = roleService.findRoleListByUserId(user.getUserId());
            roleList.stream().forEach(e -> {
                roleSet.addAll(Arrays.asList(e.getRoleKey().trim().split(",")));
            });
            menuSet = menuService.selectPermsByUserId(user.getUserId());
            // 角色加入AuthorizationInfo认证对象
            info.setRoles(roleSet);
            // 权限加入AuthorizationInfo认证对象
            info.setStringPermissions(menuSet);
        }
        return info;
    }

    /**
     * 登录认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        String password = "";

        if (token.getPassword() != null){
            password = new String(token.getPassword());
        }
        SystemUser user = null;
        try {
            user = loginService.login(username, password);
        } catch (CaptchaException e) {
            throw new AuthenticationException(e.getMessage(), e);
        } catch (UserNotExistsException e) {
            throw new UnknownAccountException(e.getMessage(), e);
        } catch (UserPasswordNotMatchException e) {
            throw new IncorrectCredentialsException(e.getMessage(), e);
        } catch (UserPasswordRetryLimitExceedException e) {
            throw new ExcessiveAttemptsException(e.getMessage(), e);
        } catch (UserBlockedException e) {
            throw new LockedAccountException(e.getMessage(), e);
        } catch (RoleBlockedException e) {
            throw new LockedAccountException(e.getMessage(), e);
        } catch (Exception e) {
            log.info("对用户[" + username + "]进行登录验证..验证未通过{}", e.getMessage());
            throw new AuthenticationException(e.getMessage(), e);
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
        return info;
    }

    /**
     * 清理指定用户授权信息缓存
     */
    public void clearCachedAuthorizationInfo(Object principal)
    {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
        this.clearCachedAuthorizationInfo(principals);
    }

    /**
     * 清理所有用户授权信息缓存
     */
    public void clearAllCachedAuthorizationInfo()
    {
        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
        if (cache != null)
        {
            for (Object key : cache.keys())
            {
                cache.remove(key);
            }
        }
    }

}
