package com.bob.web.system.controller;

import com.bob.common.core.controller.BaseController;
import com.bob.common.utils.ShiroUtils;
import com.bob.framework.shiro.service.SysPasswordService;
import com.bob.web.system.domain.SystemUser;
import com.bob.web.system.service.SystemUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: zhang bob
 * @date: 2021-06-04 15:52
 * @description: 个人信息控制类
 */
@Slf4j
@Controller
@RequestMapping("/system/user/profile")
public class SystemProfileController extends BaseController {

    // 定义前缀
    private String prefix = "user/profile";

    @Autowired
    private SystemUserService userService;
    @Autowired
    private SysPasswordService passwordService;

    /**
     * 个人信息
     * @param modelMap
     * @return
     */
    @GetMapping()
    public String profile (ModelMap modelMap){
        SystemUser user = ShiroUtils.getSysUser();
        modelMap.put("user", user);
        modelMap.put("roleGroup", userService.findUserRoleGroup(user.getUserId()));
        modelMap.put("postGroup", userService.findUserPostGroup(user.getUserId()));
        return prefix + "/profile";
    }
}
