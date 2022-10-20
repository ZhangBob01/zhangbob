package com.bob.web.system.domain;

import com.bob.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: zhang bob
 * @date: 2021-01-29 13:47
 * @description: 系统菜单类
 */
@Data
public class SystemMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /** 菜单ID. */
    private Long menuId;

    /** 菜单名称. */
    private String menuName;

    /** 父菜单名称. */
    private String parentName;

    /** 父菜单ID. */
    private Long parentId;

    /** 显示顺序. */
    private String orderNum;

    /** 菜单URL. */
    private String url;

    /** 打开方式（menuItem页签 menuBlank新窗口）. */
    private String target;

    /** 类型（M目录 C菜单 F按钮）. */
    private String menuType;

    /** 菜单状态（0显示 1隐藏）. */
    private String visible;

    /** 是否刷新（0刷新 1不刷新）. */
    private String isRefresh;

    /** 权限字符串. */
    private String perms;

    /** 菜单图标. */
    private String icon;
    /** 子菜单 */
    private List<SystemMenu> children = new ArrayList<>();
}
