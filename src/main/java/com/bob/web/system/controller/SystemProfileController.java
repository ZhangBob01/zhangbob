package com.bob.web.system.controller;

import com.bob.common.annotation.Log;
import com.bob.common.constant.UserConstants;
import com.bob.common.core.controller.BaseController;
import com.bob.common.core.domain.AjaxResult;
import com.bob.common.enums.BusinessType;
import com.bob.common.utils.DateUtils;
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

    /**
     * 检查密码是否正确
     * @param password
     * @return
     */
    @GetMapping("/checkPassword")
    @ResponseBody
    public String checkPassword(String password) {
        // 登录用户信息
        SystemUser user = ShiroUtils.getSysUser();
        // 校验密码
        boolean result = passwordService.matches(user, password);
        if (result) {
            return UserConstants.SYSTEM_ZERO;
        }
        return UserConstants.SYSTEM_ONE;
    }

    /**
     * 跳转修改密码
     * @param modelMap
     * @return
     */
    @GetMapping("/resetPwd")
    public String resetPwd(ModelMap modelMap) {
        // 获取登录用户信息
        SystemUser user = ShiroUtils.getSysUser();

        modelMap.put("user", user);
        return prefix + "resetPwd";
    }

    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @PostMapping("/resetPwd")
    @ResponseBody
    public AjaxResult resetPwd(String oldPassword, String newPassword) {
        SystemUser user = ShiroUtils.getSysUser();
        // 校验原密码
        boolean pwdFlag = passwordService.matches(user, oldPassword);
        if (!pwdFlag) {
            return error("修改密码失败，原密码错误");
        }
        // 校验新密码
        pwdFlag = passwordService.matches(user, newPassword);
        if (pwdFlag) {
            return error("新密码不能与原密码相同");
        }

        // 设置盐
        user.setSalt(ShiroUtils.randomSalt());
        // 设置新密码
        user.setPassword(passwordService.encryptPassword(user.getLoginName(), newPassword, user.getSalt()));
        user.setPwdUpdateDate(DateUtils.getNowDate());
        // 更新密码
        int result = userService.resetUserPwd(user);
        if (result > 0) {
            ShiroUtils.setSystemUser(userService.findUserById(user.getUserId()));
            return success();
        }
        return error("修改密码失败，请联系管理员");
    }

}
