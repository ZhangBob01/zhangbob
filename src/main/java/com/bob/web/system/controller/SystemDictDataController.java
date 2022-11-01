package com.bob.web.system.controller;

import com.bob.common.annotation.Log;
import com.bob.common.core.controller.BaseController;
import com.bob.common.core.domain.AjaxResult;
import com.bob.common.core.page.TableDataInfo;
import com.bob.common.enums.BusinessType;
import com.bob.common.utils.ShiroUtils;
import com.bob.common.utils.poi.ExcelUtil;
import com.bob.web.system.domain.SystemDictData;
import com.bob.web.system.service.SystemDictDataService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据字典数据管理
 *
 * @author bob
 */
@Controller
@RequestMapping("/system/dict/data")
public class SystemDictDataController extends BaseController {
    private String prefix = "system/dict/data";

    @Autowired
    private SystemDictDataService dictDataService;

    /**
     * 数据字典数据
     *
     * @return
     */
    @RequiresPermissions("system:dict:view")
    @GetMapping()
    public String dictData() {
        return prefix + "/data";
    }

    /**
     * 数据列表
     *
     * @param dictData
     * @return
     */
    @PostMapping("/list")
    @RequiresPermissions("system:dict:list")
    @ResponseBody
    public TableDataInfo list(SystemDictData dictData) {
        startPage();
        List<SystemDictData> list = dictDataService.selectDictDataList(dictData);
        return getDataTable(list);
    }

    /**
     * 数据字典导出
     * @param dictData
     * @return
     */
    @Log(title = "字典数据", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:dict:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SystemDictData dictData) {
        List<SystemDictData> list = dictDataService.selectDictDataList(dictData);
        ExcelUtil<SystemDictData> util = new ExcelUtil<>(SystemDictData.class);
        return util.exportExcel(list, "字典数据");
    }

    /**
     * 新增字典类型
     * 
     * @param dictType
     * @param mmap
     * @return
     */
    @GetMapping("/add/{dictType}")
    public String add(@PathVariable("dictType") String dictType, ModelMap mmap) {
        mmap.put("dictType", dictType);
        return prefix + "/add";
    }

    /**
     * 新增保存字典类型
     *
     * @param dict
     * @return
     */
    @Log(title = "字典数据", businessType = BusinessType.INSERT)
    @RequiresPermissions("system:dict:add")
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(@Validated SystemDictData dict) {
        dict.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(dictDataService.insertDictData(dict));
    }

    /**
     * 修改字典类型
     * @param dictCode
     * @param mmap
     * @return
     */
    @GetMapping("/edit/{dictCode}")
    public String edit(@PathVariable("dictCode") Long dictCode, ModelMap mmap) {
        mmap.put("dict", dictDataService.selectDictDataById(dictCode));
        return prefix + "/edit";
    }

    /**
     * 修改保存字典类型
     *
     * @param dict
     * @return
     */
    @Log(title = "字典数据", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:dict:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SystemDictData dict) {
        dict.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(dictDataService.updateDictData(dict));
    }

    /**
     * 删除数据字典数据
     *
     * @param ids
     * @return
     */
    @Log(title = "字典数据", businessType = BusinessType.DELETE)
    @RequiresPermissions("system:dict:remove")
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(dictDataService.deleteDictDataByIds(ids));
    }
}
