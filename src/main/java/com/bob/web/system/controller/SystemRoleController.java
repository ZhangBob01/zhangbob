package com.bob.web.system.controller;

import com.bob.common.annotation.Log;
import com.bob.common.constant.UserConstants;
import com.bob.common.core.controller.BaseController;
import com.bob.common.core.domain.AjaxResult;
import com.bob.common.core.page.TableDataInfo;
import com.bob.common.enums.BusinessType;
import com.bob.common.utils.ShiroUtils;
import com.bob.common.utils.poi.ExcelUtil;
import com.bob.framework.shiro.util.AuthorizationUtils;
import com.bob.web.system.domain.SystemRole;
import com.bob.web.system.domain.SystemUser;
import com.bob.web.system.domain.SystemUserRole;
import com.bob.web.system.service.SystemRoleService;
import com.bob.web.system.service.SystemUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: zhang bob
 * @date: 2022-10-27 15:07
 * @description: 角色管理
 */
@Controller
@RequestMapping("/system/role")
public class SystemRoleController extends BaseController {

    private String prefix = "system/role";
    @Autowired
    private SystemRoleService roleService;
    @Autowired
    private SystemUserService userService;

    /**
     * 跳转角色列表页面
     * @return
     */
    @RequiresPermissions("system:role:view")
    @GetMapping()
    public String role() {
        return prefix + "/role";
    }

    /**
     * 查询角色列表
     *
     * @param systemRole
     * @return
     */
    @RequiresPermissions("system:role:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SystemRole systemRole) {
        startPage();
        List<SystemRole> list = roleService.selectRoleList(systemRole);
        return getDataTable(list);
    }

    /**
     * 新增角色
     *
     * @return
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 校验角色名是否唯一
     *
     * @param role
     * @return
     */
    @PostMapping("/checkRoleNameUnique")
    @ResponseBody
    public String checkRoleNameUnique(SystemRole role) {
        return roleService.checkRoleNameUnique(role);
    }

    /**
     * 校验角色权限
     *
     * @param role
     * @return
     */
    @PostMapping("/checkRoleKeyUnique")
    @ResponseBody
    public String checkRoleKeyUnique(SystemRole role) {
        return roleService.checkRoleKeyUnique(role);
    }

    /**
     * 新增保存角色
     *
     * @param role
     * @return
     */
    @RequiresPermissions("system:role:add")
    @Log(title = "角色管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SystemRole role) {
        if (UserConstants.ROLE_NAME_NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role))) {
            return error("新增角色'" + role.getRoleName() + "'失败，角色名称已存在");
        } else if (UserConstants.ROLE_KEY_NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role))) {
            return error("新增角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        role.setCreateBy(ShiroUtils.getLoginName());
        AuthorizationUtils.clearAllCachedAuthorizationInfo();
        return toAjax(roleService.insertRole(role));
    }

    /**
     * 修改角色
     *
     * @param roleId
     * @param modelMap
     * @return
     */
    @GetMapping("/edit/{roleId}")
    public String edit(@PathVariable("roleId") Long roleId, ModelMap modelMap) {
        modelMap.put("role", roleService.selectRoleById(roleId));
        return prefix + "/edit";
    }

    /**
     * 修改保存角色
     *
     * @param role
     * @return
     */
    @RequiresPermissions("system:role:edit")
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SystemRole role) {
        roleService.checkRoleAllowed(role);
        if (UserConstants.ROLE_NAME_NOT_UNIQUE.equals(roleService.checkRoleNameUnique(role))) {
            return error("修改角色'" + role.getRoleName() + "'失败，角色名称已存在");
        } else if (UserConstants.ROLE_KEY_NOT_UNIQUE.equals(roleService.checkRoleKeyUnique(role))) {
            return error("修改角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        role.setUpdateBy(ShiroUtils.getLoginName());
        AuthorizationUtils.clearAllCachedAuthorizationInfo();
        return toAjax(roleService.updateRole(role));
    }

    /**
     * 删除角色
     *
     * @param ids
     * @return
     */
    @RequiresPermissions("system:role:remove")
    @Log(title = "角色管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(roleService.deleteRoleByIds(ids));
    }

    /**
     * 导出角色列表
     *
     * @param role
     * @return
     */
    @Log(title = "角色管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:role:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SystemRole role) {
        List<SystemRole> list = roleService.selectRoleList(role);
        ExcelUtil<SystemRole> util = new ExcelUtil<>(SystemRole.class);
        return util.exportExcel(list, "角色数据");
    }

    /**
     * 分配用户
     *
     * @param roleId
     * @param modelMap
     * @return
     */
    @RequiresPermissions("system:role:edit")
    @GetMapping("/authUser/{roleId}")
    public String authUser(@PathVariable("roleId") Long roleId, ModelMap modelMap) {
        modelMap.put("role", roleService.selectRoleById(roleId));
        return prefix + "/authUser";
    }

    /**
     * 查询已分配用户角色列表
     *
     * @param user
     * @return
     */
    @RequiresPermissions("system:role:list")
    @PostMapping("/authUser/allocatedList")
    @ResponseBody
    public TableDataInfo allocatedList(SystemUser user) {
        startPage();
        List<SystemUser> list = userService.selectAlllocatedList(user);
        return getDataTable(list);
    }

    /**
     * 取消授权
     *
     * @param userRole
     * @return
     */
    @Log(title = "角色管理", businessType = BusinessType.GRANT)
    @PostMapping("/authUser/cancel")
    @ResponseBody
    public AjaxResult cancelAuthUser(SystemUserRole userRole) {
        return toAjax(roleService.deleteAuthUser(userRole));
    }

    /**
     * 批量取消授权
     *
     * @param roleId
     * @param userIds
     * @return
     */
    @Log(title = "角色管理", businessType = BusinessType.GRANT)
    @PostMapping("/authUser/cancelAll")
    @ResponseBody
    public AjaxResult cancelAuthUserAll(Long roleId, String userIds) {
        return toAjax(roleService.deleteAuthUsers(roleId, userIds));
    }

    /**
     * 选择用户
     *
     * @param roleId
     * @param modelMap
     * @return
     */
    @GetMapping("/authUser/selectUser/{roleId}")
    public String selectUser(@PathVariable("roleId") Long roleId, ModelMap modelMap) {
        modelMap.put("role", roleService.selectRoleById(roleId));
        return prefix + "/selectUser";
    }

    /**
     * 查询未分配用户角色列表
     *
     * @param user
     * @return
     */
    @RequiresPermissions("system:role:list")
    @PostMapping("/authUser/unallocatedList")
    @ResponseBody
    public TableDataInfo unallocatedList(SystemUser user) {
        startPage();
        List<SystemUser> list = userService.selectUnalllocatedList(user);
        return getDataTable(list);
    }

    /**
     * 批量选择用户授权
     *
     * @param roleId
     * @param userIds
     * @return
     */
    @Log(title = "角色管理", businessType = BusinessType.GRANT)
    @PostMapping("/authUser/selectAll")
    @ResponseBody
    public AjaxResult selectAuthUserAll(Long roleId, String userIds) {
        return toAjax(roleService.insertAuthUsers(roleId, userIds));
    }

    /**
     * 角色分配数据权限
     *
     * @param roleId
     * @param modelMap
     * @return
     */
    @GetMapping("/authDataScope/{roleId}")
    public String authDataScope(@PathVariable("roleId") Long roleId, ModelMap modelMap) {
        modelMap.put("role", roleService.selectRoleById(roleId));
        return prefix + "/dataScope";
    }

    /**
     * 保存角色分配数据权限
     *
     * @param role
     * @return
     */
    @RequiresPermissions("system:role:edit")
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PostMapping("/authDataScope")
    @ResponseBody
    public AjaxResult authDataScopeSave(SystemRole role) {
        roleService.checkRoleAllowed(role);
        role.setUpdateBy(ShiroUtils.getLoginName());
        if (roleService.authDataScope(role) > 0) {
            ShiroUtils.setSystemUser(userService.findUserById(ShiroUtils.getSysUser().getUserId()));
            return success();
        }
        return error();
    }

}
