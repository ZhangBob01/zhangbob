package com.bob.web.system.controller;

import com.bob.web.system.domain.SystemUser;
import com.bob.web.system.service.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: zhang bob
 * @date: 2021-06-15 10:01
 * @description: 用户信息管理
 */
@Controller
@RequestMapping("/system/user")
public class SystemUserController {
    private String prefix = "system/user";

    @Autowired
    private SystemUserService userService;

    /**
     * 校验email是否唯一
     * @param user
     * @return
     */
    @PostMapping("/checkEmailUnique")
    @ResponseBody
    public String checkEmailUnique(SystemUser user) {
        return userService.checkEmailUnique(user);
    }

    /**
     * 校验手机号是否唯一
     * @param user
     * @return
     */
    @PostMapping("/checkPhoneUnique")
    @ResponseBody
    public String checkPhoneUnique(SystemUser user) {
        String result = userService.checkPhoneUnique(user);
        return result;
    }

}
