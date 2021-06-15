package com.bob.web.system.controller;

import com.bob.common.annotation.Log;
import com.bob.common.core.controller.BaseController;
import com.bob.common.core.domain.AjaxResult;
import com.bob.common.enums.BusinessType;
import com.bob.common.utils.ShiroUtils;
import com.bob.framework.shiro.service.SysPasswordService;
import com.bob.web.system.domain.SystemUser;
import com.bob.web.system.service.SystemUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    /**
     * 修改用户头像
     * @param modelMap
     * @return
     */
    @GetMapping("/avatar")
    public String avatar(ModelMap modelMap) {
        // 获取登录用户
        SystemUser user = ShiroUtils.getSysUser();
        SystemUser reUser = userService.findUserById(user.getUserId());
        modelMap.put("user", reUser);
        return prefix + "/avatar";
    }

    /**
     * 更新用户基本信息
     * @param user
     * @return
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PostMapping("/update")
    @ResponseBody
    public AjaxResult update(SystemUser user) {
        SystemUser loginUser = ShiroUtils.getSysUser();
        loginUser.setUserName(user.getUserName());
        loginUser.setEmail(user.getEmail());
        loginUser.setPhonenumber(user.getPhonenumber());
        loginUser.setSex(user.getSex());
        int result = userService.updateUserInfo(loginUser);
        if (result > 0) {
            ShiroUtils.setSystemUser(loginUser);
            return success();
        }

        return error();
    }

}
