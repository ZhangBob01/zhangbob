package com.bob.web.system.controller;

import com.bob.common.annotation.Log;
import com.bob.common.constant.UserConstants;
import com.bob.common.core.controller.BaseController;
import com.bob.common.core.domain.AjaxResult;
import com.bob.common.core.domain.Ztree;
import com.bob.common.core.page.TableDataInfo;
import com.bob.common.enums.BusinessType;
import com.bob.common.utils.ShiroUtils;
import com.bob.common.utils.poi.ExcelUtil;
import com.bob.web.system.domain.SystemDictType;
import com.bob.web.system.service.SystemDictTypeService;
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
 * @date: 2022-10-31 13:05
 * @description: 数据字典管理
 */
@Controller
@RequestMapping("system/dict")
public class SystemDictTypeController extends BaseController {

    private String prefix = "system/dict/type";
    @Autowired
    private SystemDictTypeService dictTypeService;

    /**
     * 数据字典
     *
     * @return
     */
    @RequiresPermissions("system:dict:view")
    @GetMapping()
    public String type() {
        return prefix + "/type";
    }

    /**
     * 获取数据字典列表
     *
     * @param dictType
     * @return
     */
    @RequiresPermissions("system:dict:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SystemDictType dictType) {
        startPage();
        List<SystemDictType> list = dictTypeService.selectDictTypeList(dictType);

        return getDataTable(list);
    }

    /**
     * 添加数据字典
     *
     * @return
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/add";
    }

    /**
     * 保存添加数据字典
     *
     * @param dictType
     * @return
     */
    @RequiresPermissions("system:dict:add")
    @PostMapping("/add")
    @Log(title = "数据字典管理", businessType = BusinessType.INSERT)
    @ResponseBody
    public AjaxResult add(@Valid SystemDictType dictType) {
        if (UserConstants.DICT_TYPE_NOT_UNIQUE.equals(dictType.getDictType())) {
            return error("添加字典类型：" + dictType.getDictType() + "失败，类型重复");
        }
        dictType.setCreateBy(ShiroUtils.getLoginName());
        return toAjax(dictTypeService.insertDictType(dictType));
    }

    /**
     * 检查字典类型是否唯一
     *
     * @param dictType
     * @return
     */
    @PostMapping("/checkDictTypeUnique")
    @ResponseBody
    public String checkDictTypeUnique(SystemDictType dictType) {
        return dictTypeService.checkDictTypeUnique(dictType);
    }

    /**
     * 字典类型编辑
     *
     * @param dictId
     * @return
     */
    @GetMapping("/edit/{dictId}")
    public String edit(@PathVariable("dictId") Long dictId, ModelMap modelMap) {
        modelMap.put("dict", dictTypeService.selectDictTypeById(dictId));
        return prefix + "/edit";
    }

    /**
     * 保存修改字典类型
     *
     * @param dictType
     * @return
     */
    @Log(title = "字典类型", businessType = BusinessType.UPDATE)
    @RequiresPermissions("system:dict:edit")
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(@Validated SystemDictType dictType) {
        if (UserConstants.DICT_TYPE_NOT_UNIQUE.equals(dictTypeService.checkDictTypeUnique(dictType))) {
            return error("修改字典'" + dictType.getDictName() + "'失败，字典类型已存在");
        }
        dictType.setUpdateBy(ShiroUtils.getLoginName());
        return toAjax(dictTypeService.updateDictType(dictType));
    }

    /**
     * 删除字典类型
     *
     * @param ids
     * @return
     */
    @Log(title = "字典类型", businessType = BusinessType.DELETE)
    @RequiresPermissions("system:dict:remove")
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(dictTypeService.deleteDictTypeByIds(ids));
    }

    /**
     * 导出数据字典
     *
     * @param dictType
     * @return
     */
    @Log(title = "字典类型", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:dict:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SystemDictType dictType) {

        List<SystemDictType> list = dictTypeService.selectDictTypeList(dictType);
        ExcelUtil<SystemDictType> util = new ExcelUtil<>(SystemDictType.class);
        return util.exportExcel(list, "字典类型");
    }

    /**
     * 清空缓存
     *
     * @return
     */
    @RequiresPermissions("system:dict:remove")
    @Log(title = "字典类型", businessType = BusinessType.CLEAN)
    @GetMapping("/clearCache")
    @ResponseBody
    public AjaxResult clearCache() {
        dictTypeService.clearCache();
        return success();
    }

    /**
     * 查询字典详细
     *
     * @param dictId
     * @param modelMap
     * @return
     */
    @RequiresPermissions("system:dict:list")
    @GetMapping("/detail/{dictId}")
    public String detail(@PathVariable("dictId") Long dictId, ModelMap modelMap) {
        modelMap.put("dict", dictTypeService.selectDictTypeById(dictId));
        modelMap.put("dictList", dictTypeService.selectDictTypeAll());
        return "system/dict/data/data";
    }

    /**
     * 选择字典树
     *
     * @param columnId
     * @param dictType
     * @param modelMap
     * @return
     */
    @GetMapping("/selectDictTree/{columnId}/{dictType}")
    public String selectDeptTree(@PathVariable("columnId") Long columnId, @PathVariable("dictType") String dictType,
                                 ModelMap modelMap) {
        modelMap.put("columnId", columnId);
        modelMap.put("dict", dictTypeService.selectDictTypeByType(dictType));
        return prefix + "/tree";
    }

    /**
     * 加载字典列表树
     *
     * @return
     */
    @GetMapping("/treeData")
    @ResponseBody
    public List<Ztree> treeData() {
        List<Ztree> ztrees = dictTypeService.selectDictTree(new SystemDictType());
        return ztrees;
    }

}
