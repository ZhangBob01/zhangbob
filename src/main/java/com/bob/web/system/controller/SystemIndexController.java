package com.bob.web.system.controller;

import com.bob.common.config.BobConfig;
import com.bob.common.constant.ShiroConstants;
import com.bob.common.core.controller.BaseController;
import com.bob.common.core.text.Convert;
import com.bob.common.utils.DateUtils;
import com.bob.common.utils.ServletUtils;
import com.bob.common.utils.ShiroUtils;
import com.bob.common.utils.StringUtils;
import com.bob.web.system.domain.SystemMenu;
import com.bob.web.system.domain.SystemUser;
import com.bob.web.system.service.SystemConfigService;
import com.bob.web.system.service.SystemMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;


import javax.servlet.http.Cookie;
import java.util.Date;
import java.util.List;

/**
 * @author: zhang bob
 * @date: 2021-01-29 11:46
 * @description: 系统首页
 */
@Controller
public class SystemIndexController extends BaseController {

    @Autowired
    private SystemMenuService systemMenuService;
    @Autowired
    private SystemConfigService configService;

    /**
     * 跳转系统首页
     *
     * @param modelMap
     * @return
     */
    @GetMapping("/index")
    public String index(ModelMap modelMap) {
        // 获取用户信息
        SystemUser user = ShiroUtils.getSysUser();

        //根据用户id获取菜单
        List<SystemMenu> systemMenuList = systemMenuService.findMenuListByUser(user);
        modelMap.put("menus", systemMenuList);
        modelMap.put("user", user);
        modelMap.put("sideTheme", configService.selectConfigByKey("sys.index.sideTheme"));
        modelMap.put("skinName", configService.selectConfigByKey("sys.index.skinName"));
        modelMap.put("ignoreFooter", configService.selectConfigByKey("sys.index.ignoreFooter"));
        modelMap.put("copyrightYear", BobConfig.getCopyrightYear());
        modelMap.put("demoEnabled", BobConfig.isDemoEnabled());
        modelMap.put("isDefaultModifyPwd", initPasswordIsModify(user.getPwdUpdateDate()));
        modelMap.put("isPasswordExpired", passwordIsExpiration(user.getPwdUpdateDate()));


        // 菜单导航显示风格
        String menuStyle = configService.selectConfigByKey("sys.index.menuStyle");
        // 移动端，默认使左侧导航菜单，否则取默认配置
        String indexStyle = ServletUtils.checkAgentIsMobile(ServletUtils.getRequest().getHeader("User-Agent")) ? "index" : menuStyle;

        // 优先Cookie配置导航菜单
        Cookie[] cookies = ServletUtils.getRequest().getCookies();
        for (Cookie cookie : cookies) {
            if (StringUtils.isNotEmpty(cookie.getName()) && "nav-style".equalsIgnoreCase(cookie.getName())) {
                indexStyle = cookie.getValue();
                break;
            }
        }
        String webIndex = "topnav".equalsIgnoreCase(indexStyle) ? "index-topnav" : "index";
        return webIndex;
    }

    /**
     * 检查密码是否过期
     * @param pwdUpdateDate
     * @return
     */
    private boolean passwordIsExpiration(Date pwdUpdateDate) {
        Integer passwordValidateDays = Convert.toInt(configService.selectConfigByKey("sys.account.passwordValidateDays"));

        if (passwordValidateDays != null && passwordValidateDays > 0) {
            if (StringUtils.isNull(pwdUpdateDate)) {
                return true;
            }
            Date nowDate = new Date();
            return DateUtils.differentDaysByMillisecond(nowDate, pwdUpdateDate) > passwordValidateDays;
        }
        return false;
    }

    /**
     * 检查是否是初始密码
     * @param pwdUpdateDate
     * @return
     */
    private boolean initPasswordIsModify(Date pwdUpdateDate) {
        Integer initPasswordModify = Convert.toInt(configService.selectConfigByKey("sys.account.initPasswordModify"));
        return initPasswordModify != null && initPasswordModify == 1 && pwdUpdateDate == null;
    }

    /**
     * 锁屏操作
     * @param modelMap
     * @return
     */
    @GetMapping("/lockscreen")
    public String lockscreen (ModelMap modelMap) {
        // 设置用户信息
        modelMap.put("user", ShiroUtils.getSysUser());
        // 设置session中锁屏标志
        ServletUtils.getSesion().setAttribute(ShiroConstants.LOCK_SCREEN, true);
        return "lock";
    }

}



