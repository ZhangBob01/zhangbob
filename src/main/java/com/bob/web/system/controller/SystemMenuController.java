package com.bob.web.system.controller;

import com.bob.common.core.controller.BaseController;
import com.bob.common.core.domain.Ztree;
import com.bob.common.utils.ShiroUtils;
import com.bob.web.system.domain.SystemRole;
import com.bob.web.system.service.SystemMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author: zhang bob
 * @date: 2022-10-27 16:46
 * @description: 菜单管理
 */
@Controller
@RequestMapping("system/menu")
public class SystemMenuController extends BaseController {

    @Autowired
    private SystemMenuService menuService;

    /**
     * 查询所有菜单树
     *
     * @return
     */
    @GetMapping("/roleMenuTreeData")
    @ResponseBody
    public List<Ztree> roleMenuTreeData (SystemRole role){
        Long userId = ShiroUtils.getUserId();
        List<Ztree> ztreeList = menuService.menuTreeData(role, userId);
        return ztreeList;
    }
}
