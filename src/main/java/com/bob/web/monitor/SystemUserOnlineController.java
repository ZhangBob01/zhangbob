package com.bob.web.monitor;

import com.bob.common.annotation.Log;
import com.bob.common.core.controller.BaseController;
import com.bob.common.core.domain.AjaxResult;
import com.bob.common.core.page.TableDataInfo;
import com.bob.common.core.text.Convert;
import com.bob.common.enums.BusinessType;
import com.bob.common.enums.OnlineStatus;
import com.bob.common.utils.ShiroUtils;
import com.bob.framework.shiro.session.OnlineSession;
import com.bob.framework.shiro.session.OnlineSessionDAO;
import com.bob.web.system.domain.SystemUserOnline;
import com.bob.web.system.service.SystemUserOnlineService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 在线用户监控
 *
 * @author bob
 */
@Controller
@RequestMapping("/monitor/online")
public class SystemUserOnlineController extends BaseController {
    private String prefix = "monitor/online";

    @Autowired
    private SystemUserOnlineService userOnlineService;

    @Autowired
    private OnlineSessionDAO onlineSessionDAO;

    /**
     * 查看在线用户
     *
     * @return
     */
    @RequiresPermissions("monitor:online:view")
    @GetMapping()
    public String online() {
        return prefix + "/online";
    }

    /**
     * 获取在线用户列表
     *
     * @param userOnline
     * @return
     */
    @RequiresPermissions("monitor:online:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SystemUserOnline userOnline) {
        startPage();
        List<SystemUserOnline> list = userOnlineService.selectUserOnlineList(userOnline);
        return getDataTable(list);
    }

    /**
     * 批量登出
     *
     * @param ids
     * @return
     */
    @RequiresPermissions(value = {"monitor:online:batchForceLogout", "monitor:online:forceLogout"}, logical = Logical.OR)
    @Log(title = "在线用户", businessType = BusinessType.FORCE)
    @PostMapping("/batchForceLogout")
    @ResponseBody
    public AjaxResult batchForceLogout(String ids) {
        for (String sessionId : Convert.toStrArray(ids)) {
            SystemUserOnline online = userOnlineService.selectOnlineById(sessionId);
            if (online == null) {
                return error("用户已下线");
            }
            OnlineSession onlineSession = (OnlineSession) onlineSessionDAO.readSession(online.getSessionId());
            if (onlineSession == null) {
                return error("用户已下线");
            }
            if (sessionId.equals(ShiroUtils.getSessionId())) {
                return error("当前登录用户无法强退");
            }
            onlineSessionDAO.delete(onlineSession);
            online.setStatus(OnlineStatus.off_line);
            userOnlineService.saveOnline(online);
            userOnlineService.removeUserCache(online.getLoginName(), sessionId);
        }
        return success();
    }
}
