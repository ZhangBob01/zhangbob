package com.bob.web.system.controller;

import com.bob.common.annotation.Log;
import com.bob.common.constant.UserConstants;
import com.bob.common.core.controller.BaseController;
import com.bob.common.core.domain.AjaxResult;
import com.bob.common.core.page.TableDataInfo;
import com.bob.common.enums.BusinessType;
import com.bob.common.utils.ShiroUtils;
import com.bob.common.utils.StringUtils;
import com.bob.common.utils.poi.ExcelUtil;
import com.bob.framework.shiro.service.SysPasswordService;
import com.bob.web.system.domain.SystemRole;
import com.bob.web.system.domain.SystemUser;
import com.bob.web.system.service.SystemPostService;
import com.bob.web.system.service.SystemRoleService;
import com.bob.web.system.service.SystemUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: zhang bob
 * @date: 2021-06-15 10:01
 * @description: 用户信息管理
 */
@Controller
@RequestMapping("/system/user")
public class SystemUserController extends BaseController {
    private String prefix = "system/user";

    @Autowired
    private SystemUserService userService;
    @Autowired
    private SystemRoleService roleService;
    @Autowired
    private SystemPostService postService;
    @Autowired
    private SysPasswordService passwordService;

    @RequiresPermissions("system:user:view")
    @GetMapping()
    public String user() {
        return prefix + "/user";
    }

    /**
     * 校验用户名
     *
     * @param user
     * @return
     */
    @PostMapping("/checkLoginNameUnique")
    @ResponseBody
    public String checkLoginNameUnique(SystemUser user) {
        return userService.checkLoginNameUnique(user.getLoginName());
    }


    /**
     * 校验email是否唯一
     *
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
     *
     * @param user
     * @return
     */
    @PostMapping("/checkPhoneUnique")
    @ResponseBody
    public String checkPhoneUnique(SystemUser user) {
        String result = userService.checkPhoneUnique(user);
        return result;
    }

    /**
     * 查询用户列表
     *
     * @param user
     * @return
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SystemUser user) {
        startPage();
        List<SystemUser> list = userService.selectUserList(user);
        return getDataTable(list);
    }

    /**
     * 新增用户
     *
     * @param modelMap
     * @return
     */
    @GetMapping("/add")
    public String add(ModelMap modelMap) {
        modelMap.put("roles", roleService.selectRoleAll().stream().filter(role -> !role.isAdmin()).collect(Collectors.toList()));
        modelMap.put("posts", postService.selectPostAll());
        return prefix + "/add";
    }

    /**
     * 新增保存用户
     *
     * @param user
     * @return
     */
    @RequiresPermissions("system:user:add")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SystemUser user) {
        if (UserConstants.USER_NAME_NOT_UNIQUE.equals(userService.checkLoginNameUnique(user.getLoginName()))) {
            return error("新增用户'" + user.getLoginName() + "'失败，登录账号已存在");
        } else if (StringUtils.isNotEmpty(user.getPhonenumber())
                && UserConstants.USER_PHONE_NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
            return error("新增用户'" + user.getLoginName() + "'失败，手机号码已存在");
        } else if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.USER_EMAIL_NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
            return error("新增用户'" + user.getLoginName() + "'失败，邮箱账号已存在");
        }
        user.setSalt(ShiroUtils.randomSalt());
        user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        user.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(userService.insertUser(user));
    }

    /**
     * 修改用户
     *
     * @param userId
     * @param modelMap
     * @return
     */
    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable("userId") Long userId, ModelMap modelMap) {
        List<SystemRole> roles = roleService.selectRolesByUserId(userId);
        modelMap.put("user", userService.findUserById(userId));
        modelMap.put("roles", SystemUser.isAdmin(userId) ? roles : roles.stream().filter(role -> !role.isAdmin()).collect(Collectors.toList()));
        modelMap.put("posts", postService.selectPostsByUserId(userId));
        return prefix + "/edit";
    }

    /**
     * 修改保存用户
     *
     * @param user
     * @return
     */
    @RequiresPermissions("system:user:edit")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SystemUser user) {
        userService.checkUserAllowed(user);
        if (StringUtils.isNotEmpty(user.getPhonenumber())
                && UserConstants.USER_PHONE_NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
            return error("修改用户'" + user.getLoginName() + "'失败，手机号码已存在");
        } else if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.USER_EMAIL_NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
            return error("修改用户'" + user.getLoginName() + "'失败，邮箱账号已存在");
        }
        user.setUpdateBy(ShiroUtils.getLoginName());
        AjaxResult ajaxResult = toAjax(userService.updateUser(user));
        return ajaxResult;
    }

    /**
     * 删除用户
     *
     * @param ids
     * @return
     */
    @RequiresPermissions("system:user:remove")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(userService.deleteUserByIds(ids));
    }

    /**
     * excel导入系统用户
     *
     * @param file
     * @param updateSupport
     * @return
     * @throws Exception
     */
    @Log(title = "用户管理", businessType = BusinessType.IMPORT)
    @RequiresPermissions("system:user:import")
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception {
        ExcelUtil<SystemUser> util = new ExcelUtil<>(SystemUser.class);
        List<SystemUser> userList = util.importExcel(file.getInputStream());
        String operName = ShiroUtils.getSysUser().getLoginName();
        String message = userService.importUser(userList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    /**
     * 下载导入模版
     *
     * @return
     */
    @RequiresPermissions("system:user:view")
    @GetMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate() {
        ExcelUtil<SystemUser> util = new ExcelUtil<>(SystemUser.class);
        return util.importTemplateExcel("用户数据");
    }

    /**
     * 导出用户到excel
     * @param user
     * @return
     */
    @Log(title = "用户管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:user:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SystemUser user) {
        List<SystemUser> list = userService.selectUserList(user);
        ExcelUtil<SystemUser> util = new ExcelUtil<>(SystemUser.class);
        return util.exportExcel(list, "用户数据");
    }

    /**
     * 重置密码
     * @param userId
     * @param modelMap
     * @return
     */
    @RequiresPermissions("system:user:resetPwd")
    @GetMapping("/resetPwd/{userId}")
    public String resetPwd(@PathVariable("userId") Long userId, ModelMap modelMap) {
        modelMap.put("user", userService.findUserById(userId));
        return prefix + "/resetPwd";
    }

    /**
     * 重置密码
     * @param user
     * @return
     */
    @RequiresPermissions("system:user:resetPwd")
    @Log(title = "重置密码", businessType = BusinessType.UPDATE)
    @PostMapping("/resetPwd")
    @ResponseBody
    public AjaxResult resetPwdSave(SystemUser user) {
        userService.checkUserAllowed(user);
        user.setSalt(ShiroUtils.randomSalt());
        user.setPassword(passwordService.encryptPassword(user.getLoginName(), user.getPassword(), user.getSalt()));
        if (userService.resetUserPwd(user) > 0) {
            if (ShiroUtils.getUserId().longValue() == user.getUserId().longValue()) {
                ShiroUtils.setSystemUser(userService.findUserById(user.getUserId()));
            }
            return success();
        }
        return error();
    }

    /**
     * 授权角色
     *
     * @param userId
     * @param modelMap
     * @return
     */
    @GetMapping("/authRole/{userId}")
    public String authRole(@PathVariable("userId") Long userId, ModelMap modelMap) {
        SystemUser user = userService.findUserById(userId);
        // 获取用户所属的角色列表
        List<SystemRole> roles = roleService.selectRolesByUserId(userId);
        modelMap.put("user", user);
        modelMap.put("roles", SystemUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        return prefix + "/authRole";
    }

    /**
     * 用户授权角色
     *
     * @param userId
     * @param roleIds
     * @return
     */
    @RequiresPermissions("system:user:add")
    @Log(title = "用户管理", businessType = BusinessType.GRANT)
    @PostMapping("/authRole/insertAuthRole")
    @ResponseBody
    public AjaxResult insertAuthRole(Long userId, Long[] roleIds) {
        userService.insertUserAuth(userId, roleIds);
        return success();
    }
}
