package com.bob.web.system.domain;

import com.bob.common.core.domain.BaseEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author: zhang bob
 * @date: 2021-01-27 10:46
 * @description:用户实体类
 */
@Data
public class SystemUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 用户表id. */
    private Long userId;
    /** 部门id. */
    private Long deptId;
    /** 父级部门id. */
    private Long parentId;
    /** 角色Id. */
    private Long roleId;
    /** 登录名. */
    private String loginName;
    /** 用户名. */
    private String userName;
    /** 用户类型. */
    private String userType;
    /** 邮箱. */
    private String email;
    /** 手机号码. */
    private String phonenumber;
    /** 性别. */
    private  String sex;
    /** 用户头像. */
    private String avatar;
    /** 密码. */
    private String password;
    /** 盐加密 */
    private String salt;
    /** 帐号状态（0正常 1停用）. */
    private String status;
    /** 删除标志（0代表存在 2代表删除）. */
    private String delFlag;
    /** 最后登录IP. */
    private String loginIp;
    /** 最后登录时间 */
    private Date loginDate;
    /** 密码最后更新时间 */
    private Date pwdUpdateDate;
    /** 部门对象. */
    private SystemDept systemDept;
    /** 角色列表. */
    private List<SystemRole> roles;

    public boolean isAdmin() {
        return isAdmin(this.userId);
    }

    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }
}
