package com.bob.web.system.controller;

import com.bob.common.annotation.Log;
import com.bob.common.constant.UserConstants;
import com.bob.common.core.controller.BaseController;
import com.bob.common.core.domain.AjaxResult;
import com.bob.common.core.page.TableDataInfo;
import com.bob.common.enums.BusinessType;
import com.bob.common.utils.ShiroUtils;
import com.bob.common.utils.poi.ExcelUtil;
import com.bob.web.system.domain.SystemPost;
import com.bob.web.system.service.SystemPostService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: zhang bob
 * @date: 2022-10-31 08:54
 * @description: 岗位管理
 */
@Controller
@RequestMapping("system/post")
public class SystemPostController extends BaseController {

    private String prefix = "system/post";
    @Autowired
    private SystemPostService postService;

    /**
     * 岗位管理
     *
     * @return
     */
    @RequiresPermissions("system:post:view")
    @RequestMapping("")
    public String postManage() {
        return prefix + "/post";
    }

    /**
     * 查询岗位列表
     *
     * @param post
     * @return
     */
    @RequiresPermissions("system:post:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SystemPost post) {
        startPage();
        List<SystemPost> postList = postService.selectPostList(post);
        return getDataTable(postList);
    }

    /**
     * 新增岗位
     *
     * @return
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 保存新增岗位
     *
     * @param systemPost
     * @return
     */
    @RequiresPermissions("system:post:add")
    @Log(title = "岗位管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult add(@Validated SystemPost systemPost) {
        // 判断岗位名称是否存在
        if (UserConstants.POST_NAME_NOT_UNIQUE.equals(postService.checkPostNameUnique(systemPost))){
            return error("新增岗位："+systemPost.getPostName()+",保存失败，岗位名已存在");
        } else if (UserConstants.POST_CODE_NOT_UNIQUE.equals(postService.checkPostCodeUnique(systemPost))){
            return error("新增岗位："+systemPost.getPostCode()+",保存失败，岗位编码已存在");
        }
        systemPost.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(postService.insertPost(systemPost));
    }

    /**
     * 校验岗位名称
     */
    @PostMapping("/checkPostNameUnique")
    @ResponseBody
    public String checkPostNameUnique(SystemPost post)
    {
        return postService.checkPostNameUnique(post);
    }

    /**
     * 校验岗位编码
     */
    @PostMapping("/checkPostCodeUnique")
    @ResponseBody
    public String checkPostCodeUnique(SystemPost post)
    {
        return postService.checkPostCodeUnique(post);
    }

    /**
     * 岗位编辑
     *
     * @param postId
     * @param modelMap
     * @return
     */
    @GetMapping("/edit/{postId}")
    public String edit(@PathVariable("postId") Long postId, ModelMap modelMap) {
        modelMap.put("post", postService.selectPostById(postId));
        return prefix + "/edit";
    }

    /**
     * 保存编辑岗位
     *
     * @param systemPost
     * @return
     */
    @RequiresPermissions("system:post:edit")
    @Log(title = "岗位管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult edit(@Validated SystemPost systemPost) {
        // 判断岗位名称是否存在
        if (UserConstants.POST_NAME_NOT_UNIQUE.equals(postService.checkPostNameUnique(systemPost))){
            return error("新增岗位："+systemPost.getPostName()+",保存失败，岗位名已存在");
        } else if (UserConstants.POST_CODE_NOT_UNIQUE.equals(postService.checkPostCodeUnique(systemPost))){
            return error("新增岗位："+systemPost.getPostCode()+",保存失败，岗位编码已存在");
        }
        systemPost.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(postService.updatePost(systemPost));
    }

    /**
     * 删除岗位
     *
     * @param ids
     * @return
     */
    @RequiresPermissions("system:post:remove")
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids){
        // 删除岗位
        try {
            return toAjax(postService.deletePostByIds(ids));
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    @Log(title = "岗位管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:post:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SystemPost post)
    {
        List<SystemPost> list = postService.selectPostList(post);
        ExcelUtil<SystemPost> util = new ExcelUtil<>(SystemPost.class);
        return util.exportExcel(list, "岗位数据");
    }


}
