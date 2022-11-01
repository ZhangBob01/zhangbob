package com.bob.web.monitor;

import com.bob.common.annotation.Log;
import com.bob.common.core.controller.BaseController;
import com.bob.common.core.domain.AjaxResult;
import com.bob.common.core.page.TableDataInfo;
import com.bob.common.enums.BusinessType;
import com.bob.common.utils.poi.ExcelUtil;
import com.bob.framework.shiro.service.SysPasswordService;
import com.bob.web.system.domain.SystemLogininfor;
import com.bob.web.system.service.SystemLogininforService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 系统登录日志
 *
 * @author bob
 */
@Controller
@RequestMapping("/monitor/logininfor")
public class SystemLogininforController extends BaseController {
    private String prefix = "monitor/logininfor";

    @Autowired
    private SystemLogininforService logininforService;

    @Autowired
    private SysPasswordService passwordService;

    /**
     * 登录日志
     *
     * @return
     */
    @RequiresPermissions("monitor:logininfor:view")
    @GetMapping()
    public String logininfor() {
        return prefix + "/logininfor";
    }

    /**
     * 查询登录日志列表
     *
     * @param logininfor
     * @return
     */
    @RequiresPermissions("monitor:logininfor:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SystemLogininfor logininfor) {
        startPage();
        List<SystemLogininfor> list = logininforService.selectLogininforList(logininfor);
        return getDataTable(list);
    }

    /**
     * 导出登录日志
     *
     * @param logininfor
     * @return
     */
    @Log(title = "登录日志", businessType = BusinessType.EXPORT)
    @RequiresPermissions("monitor:logininfor:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SystemLogininfor logininfor) {
        List<SystemLogininfor> list = logininforService.selectLogininforList(logininfor);
        ExcelUtil<SystemLogininfor> util = new ExcelUtil<>(SystemLogininfor.class);
        return util.exportExcel(list, "登录日志");
    }

    /**
     * 删除登录日志
     *
     * @param ids
     * @return
     */
    @RequiresPermissions("monitor:logininfor:remove")
    @Log(title = "登录日志", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(logininforService.deleteLogininforByIds(ids));
    }

    /**
     * 清除登录日志
     *
     * @return
     */
    @RequiresPermissions("monitor:logininfor:remove")
    @Log(title = "登录日志", businessType = BusinessType.CLEAN)
    @PostMapping("/clean")
    @ResponseBody
    public AjaxResult clean() {
        logininforService.cleanLogininfor();
        return success();
    }

    /**
     * 账户解锁
     *
     * @param loginName
     * @return
     */
    @RequiresPermissions("monitor:logininfor:unlock")
    @Log(title = "账户解锁", businessType = BusinessType.OTHER)
    @PostMapping("/unlock")
    @ResponseBody
    public AjaxResult unlock(String loginName) {
        passwordService.clearLoginRecordCache(loginName);
        return success();
    }
}
