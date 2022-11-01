package com.bob.web.system.controller;

import com.bob.common.annotation.Log;
import com.bob.common.constant.UserConstants;
import com.bob.common.core.controller.BaseController;
import com.bob.common.core.domain.AjaxResult;
import com.bob.common.core.page.TableDataInfo;
import com.bob.common.enums.BusinessType;
import com.bob.common.utils.ShiroUtils;
import com.bob.web.system.domain.SystemConfig;
import com.bob.web.system.service.SystemConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author: zhang bob
 * @date: 2022-11-01 09:51
 * @description: 系统参数管理
 */
@Controller
@RequestMapping("/system/config")
public class SystemConfigController extends BaseController {

    private String prefix = "system/config";
    @Autowired
    private SystemConfigService configService;

    /**
     * 参数管理
     *
     * @return
     */
    @RequiresPermissions("system:config:view")
    @GetMapping()
    public String view(){
        return prefix + "/config";
    }

    /**
     * 查询参数列表
     *
     * @param config
     * @return
     */
    @RequiresPermissions("system:config:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SystemConfig config) {
        startPage();
        List<SystemConfig> list = configService.selectConfigList(config);
        return getDataTable(list);
    }

    /**
     * 添加参数
     *
     * @return
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 保存添加参数
     *
     * @param config
     * @return
     */
    @RequiresPermissions("system:config:add")
    @Log(title = "配置参数管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult add(@Valid SystemConfig config) {
        // 判断参数key是否唯一
        if (UserConstants.CONFIG_KEY_NOT_UNIQUE.equals(configService.checkConfigKeyUnique(config))) {
            return error("保存参数："+ config.getConfigName()+"失败，参数键名已存在");
        }
        config.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(configService.insertConfig(config));
    }

    /**
     * 校验参数键名
     *
     * @param config
     * @return
     */
    @PostMapping("/checkConfigKeyUnique")
    @ResponseBody
    public String checkConfigKeyUnique(SystemConfig config) {
        return configService.checkConfigKeyUnique(config);
    }

    /**
     * 修改参数配置
     * @param configId
     * @param modelMap
     * @return
     */
    @GetMapping("/edit/{configId}")
    public String edit(@PathVariable("configId") Long configId, ModelMap modelMap) {
        modelMap.put("config", configService.selectConfigById(configId));
        return prefix + "/edit";
    }

    /**
     * 保存修改参数配置
     *
     * @param config
     * @return
     */
    @RequiresPermissions("system:config:edit")
    @Log(title = "参数管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SystemConfig config) {
        if (UserConstants.CONFIG_KEY_NOT_UNIQUE.equals(configService.checkConfigKeyUnique(config))) {
            return error("修改参数'" + config.getConfigName() + "'失败，参数键名已存在");
        }
        config.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(configService.updateConfig(config));
    }

    /**
     * 删除参数配置
     *
     * @param ids
     * @return
     */
    @RequiresPermissions("system:config:remove")
    @Log(title = "参数管理", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(configService.deleteConfigByIds(ids));
    }

    /**
     * 清空缓存
     *
     * @return
     */
    @RequiresPermissions("system:config:remove")
    @Log(title = "参数管理", businessType = BusinessType.CLEAN)
    @GetMapping("/clearCache")
    @ResponseBody
    public AjaxResult clearCache() {
        configService.clearCache();
        return success();
    }
}
