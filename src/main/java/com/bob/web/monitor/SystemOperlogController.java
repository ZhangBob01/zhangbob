package com.bob.web.monitor;

import com.bob.common.annotation.Log;
import com.bob.common.core.controller.BaseController;
import com.bob.common.core.domain.AjaxResult;
import com.bob.common.core.page.TableDataInfo;
import com.bob.common.enums.BusinessType;
import com.bob.common.utils.poi.ExcelUtil;
import com.bob.web.system.domain.SystemOperLog;
import com.bob.web.system.service.SystemOperLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 操作日志记录
 *
 * @author bob
 */
@Controller
@RequestMapping("/monitor/operlog")
public class SystemOperlogController extends BaseController {
    private String prefix = "monitor/operlog";

    @Autowired
    private SystemOperLogService operLogService;

    /**
     * 访问操作日志
     *
     * @return
     */
    @RequiresPermissions("monitor:operlog:view")
    @GetMapping()
    public String operlog() {
        return prefix + "/operlog";
    }

    /**
     * 获取操作日志列表
     *
     * @param operLog
     * @return
     */
    @RequiresPermissions("monitor:operlog:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SystemOperLog operLog) {
        startPage();
        List<SystemOperLog> list = operLogService.selectOperLogList(operLog);
        return getDataTable(list);
    }

    /**
     * 导出操作日志
     *
     * @param operLog
     * @return
     */
    @Log(title = "操作日志", businessType = BusinessType.EXPORT)
    @RequiresPermissions("monitor:operlog:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SystemOperLog operLog) {
        List<SystemOperLog> list = operLogService.selectOperLogList(operLog);
        ExcelUtil<SystemOperLog> util = new ExcelUtil<>(SystemOperLog.class);
        return util.exportExcel(list, "操作日志");
    }

    /**
     * 删除操作日志
     *
     * @param ids
     * @return
     */
    @RequiresPermissions("monitor:operlog:remove")
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(operLogService.deleteOperLogByIds(ids));
    }

    /**
     * 查看操作日志
     *
     * @param operId
     * @param mmap
     * @return
     */
    @RequiresPermissions("monitor:operlog:detail")
    @GetMapping("/detail/{operId}")
    public String detail(@PathVariable("operId") Long operId, ModelMap mmap) {
        mmap.put("operLog", operLogService.selectOperLogById(operId));
        return prefix + "/detail";
    }

    /**
     * 清除操作日志
     *
     * @return
     */
    @Log(title = "操作日志", businessType = BusinessType.CLEAN)
    @RequiresPermissions("monitor:operlog:remove")
    @PostMapping("/clean")
    @ResponseBody
    public AjaxResult clean() {
        operLogService.cleanOperLog();
        return success();
    }
}
