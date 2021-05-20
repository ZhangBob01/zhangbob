package com.bob.web.system.domain;

import com.bob.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @author: zhang bob
 * @date: 2021-05-18 13:47
 * @description:
 */
@Data
public class SystemLogininfor extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** ID */
    private Long infoId;

    /** 用户账号 */
    private String loginName;

    /** 登录状态 0成功 1失败 */
    private String status;

    /** 登录IP地址 */
    private String ipaddr;

    /** 登录地点 */
    private String loginLocation;

    /** 浏览器类型 */
    private String browser;

    /** 操作系统 */
    private String os;

    /** 提示消息 */
    private String msg;

    /** 访问时间 */
    private Date loginTime;
}
