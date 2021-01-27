package com.bob.web.system.controller;

import com.bob.common.core.controller.BaseController;
import com.bob.common.core.domain.AjaxResult;
import com.bob.common.utils.ServletUtils;
import com.bob.common.utils.StringUtils;
import com.bob.web.system.domain.SystemUser;
import com.bob.web.system.service.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: zhang bob
 * @date: 2021-01-25 14:19
 * @description: 系统登录控制类
 */
@Controller
public class SystemLoginController extends BaseController {

    @Autowired
    private SystemUserService systemUserService;

    /**
     * 用户登录
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response){
        // 检查请求是否为ajax，返回Json字符串
        if (ServletUtils.isAjaxRequest(request)) {
            return ServletUtils.renderString(response, "{\"code\":\"1\", \"msg\":\"未登录或者请求超时，请重新登录\"}");
        }
        return "login";
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @param rememberMe
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public AjaxResult ajaxLogin(String username, String password, Boolean rememberMe){
        if (StringUtils.isEmpty(username)) {
            return error("请输入用户名");
        }
        if (StringUtils.isEmpty(password)) {
            return error("请输入密码");
        }

        //根据用户名查询用户信息
        SystemUser user = systemUserService.findUserByUsername(username);
        if (StringUtils.isNull(user)){
            return error("用户信息不存在");
        }


        return success();
    }
}
