package com.bob.web.system.controller;

import com.bob.common.annotation.Log;
import com.bob.common.constant.UserConstants;
import com.bob.common.core.controller.BaseController;
import com.bob.common.core.domain.AjaxResult;
import com.bob.common.core.domain.Ztree;
import com.bob.common.core.page.TableDataInfo;
import com.bob.common.enums.BusinessType;
import com.bob.common.utils.ShiroUtils;
import com.bob.framework.shiro.util.AuthorizationUtils;
import com.bob.web.system.domain.SystemMenu;
import com.bob.web.system.domain.SystemRole;
import com.bob.web.system.service.SystemMenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: zhang bob
 * @date: 2022-10-27 16:46
 * @description: 菜单管理
 */
@Controller
@RequestMapping("system/menu")
public class SystemMenuController extends BaseController {

    private String prefix = "system/menu";
    @Autowired
    private SystemMenuService menuService;

    /**
     * 菜单
     *
     * @return
     */
    @RequiresPermissions("system:menu:view")
    @GetMapping()
    public String menu() {
        return prefix + "/menu";
    }

    /**
     * 查询菜单列表
     *
     * @param menu
     * @return
     */
    @RequiresPermissions("system:menu:list")
    @PostMapping("/list")
    @ResponseBody
    public List<SystemMenu> list(SystemMenu menu) {
        Long userId = ShiroUtils.getUserId();
        List<SystemMenu> menuList = menuService.selectMenuList(menu, userId);
        return menuList;
    }

    /**
     * 添加菜单
     *
     * @param parentId
     * @return
     */
    @RequiresPermissions("system:menu:add")
    @GetMapping("/add/{parentId}")
    public String add(@PathVariable("parentId") Long parentId, ModelMap modelMap){
        // 创建菜单
        SystemMenu menu = new SystemMenu();
        menu.setMenuId(0L);
        menu.setMenuName("主目录");
        // 判断是否主菜单
        if (0L != parentId) {
            menu = menuService.selectMenuById(parentId);
        }
        modelMap.put("menu", menu);
        return prefix + "/add";
    }

    /**
     * 新增保存菜单
     *
     * @param menu
     * @return
     */
    @Log(title = "菜单管理", businessType = BusinessType.INSERT)
    @RequiresPermissions("system:menu:add")
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SystemMenu menu) {
        if (UserConstants.MENU_NAME_NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))) {
            return error("新增菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        }
        menu.setCreateBy(ShiroUtils.getLoginName());
        AuthorizationUtils.clearAllCachedAuthorizationInfo();
        return toAjax(menuService.insertMenu(menu));
    }

    /**
     * 查询所有菜单树
     *
     * @return
     */
    @GetMapping("/roleMenuTreeData")
    @ResponseBody
    public List<Ztree> roleMenuTreeData (SystemRole role){
        Long userId = ShiroUtils.getUserId();
        List<Ztree> ztreeList = menuService.roleMenuTreeData(role, userId);
        return ztreeList;
    }

    /**
     * 选择菜单树
     *
     * @param menuId
     * @param modelMap
     * @return
     */
    @GetMapping("/selectMenuTree/{menuId}")
    public String selectMenuTree(@PathVariable("menuId") Long menuId, ModelMap modelMap) {
        modelMap.put("menu", menuService.selectMenuById(menuId));
        return prefix + "/tree";
    }

    /**
     * 加载所有菜单列表树
     *
     * @return
     */
    @GetMapping("/menuTreeData")
    @ResponseBody
    public List<Ztree> menuTreeData() {
        Long userId = ShiroUtils.getUserId();
        List<Ztree> ztrees = menuService.menuTreeData(userId);
        return ztrees;
    }

    /**
     * 校验菜单名称
     *
     * @param menu
     * @return
     */
    @PostMapping("/checkMenuNameUnique")
    @ResponseBody
    public String checkMenuNameUnique(SystemMenu menu)
    {
        return menuService.checkMenuNameUnique(menu);
    }

    /**
     * 修改菜单
     *
     * @param menuId
     * @param modelMap
     * @return
     */
    @GetMapping("/edit/{menuId}")
    public String edit(@PathVariable("menuId") Long menuId, ModelMap modelMap) {
        modelMap.put("menu", menuService.selectMenuById(menuId));
        return prefix + "/edit";
    }

    /**
     * 修改保存菜单
     * @param menu
     * @return
     */
    @Log(title = "菜单管理", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:menu:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SystemMenu menu) {
        if (UserConstants.MENU_NAME_NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))) {
            return error("修改菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        }
        menu.setUpdateBy(ShiroUtils.getLoginName());
        AuthorizationUtils.clearAllCachedAuthorizationInfo();
        return toAjax(menuService.updateMenu(menu));
    }

    /**
     * 删除菜单
     *
     * @param menuId
     * @return
     */
    @Log(title = "菜单管理", businessType = BusinessType.DELETE)
    @RequiresPermissions("system:menu:remove")
    @GetMapping("/remove/{menuId}")
    @ResponseBody
    public AjaxResult remove(@PathVariable("menuId") Long menuId) {
        if (menuService.selectCountMenuByParentId(menuId) > 0) {
            return AjaxResult.warn("存在子菜单,不允许删除");
        }
        if (menuService.selectCountRoleMenuByMenuId(menuId) > 0) {
            return AjaxResult.warn("菜单已分配,不允许删除");
        }
        AuthorizationUtils.clearAllCachedAuthorizationInfo();
        return toAjax(menuService.deleteMenuById(menuId));
    }
}
