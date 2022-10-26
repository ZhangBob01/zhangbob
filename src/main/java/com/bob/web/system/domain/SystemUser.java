package com.bob.web.system.domain;

import com.bob.common.annotation.Excel;
import com.bob.common.annotation.Excel.Type;
import com.bob.common.annotation.Excel.ColumnType;
import com.bob.common.annotation.Excels;
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
    @Excel(name = "用户序号", cellType = ColumnType.NUMERIC, prompt = "用户编号")
    private Long userId;
    /** 部门id. */
    @Excel(name = "部门编号", type = Type.IMPORT)
    private Long deptId;
    /** 父级部门id. */
    private Long parentId;
    /** 角色Id. */
    private Long roleId;
    /** 登录名. */
    @Excel(name = "登录名称")
    private String loginName;
    /** 用户名. */
    @Excel(name = "用户名称")
    private String userName;
    /** 用户类型. */
    private String userType;
    /** 邮箱. */
    @Excel(name = "用户邮箱")
    private String email;
    /** 手机号码. */
    @Excel(name = "手机号码")
    private String phonenumber;
    /** 性别. */
    @Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
    private  String sex;
    /** 用户头像. */
    private String avatar;
    /** 密码. */
    private String password;
    /** 盐加密 */
    private String salt;
    /** 帐号状态（0正常 1停用）. */
    @Excel(name = "帐号状态", readConverterExp = "0=正常,1=停用")
    private String status;
    /** 删除标志（0代表存在 2代表删除）. */
    private String delFlag;
    /** 最后登录IP. */
    @Excel(name = "最后登录IP", type = Type.EXPORT)
    private String loginIp;
    /** 最后登录时间 */
    @Excel(name = "最后登录时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Type.EXPORT)
    private Date loginDate;
    /** 密码最后更新时间 */
    private Date pwdUpdateDate;
    /** 部门对象. */
    @Excels({
            @Excel(name = "部门名称", targetAttr = "deptName", type = Type.EXPORT),
            @Excel(name = "部门负责人", targetAttr = "leader", type = Type.EXPORT)
    })
    private SystemDept systemDept;
    /** 角色列表. */
    private List<SystemRole> roles;
    /** 角色组 */
    private Long[] roleIds;
    /**
     * 岗位组
     */
    private Long[] postIds;

    public SystemUser() {
    }

    public SystemUser(Long userId) {
        this.userId = userId;
    }

    public boolean isAdmin() {
        return isAdmin(this.userId);
    }

    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }
}
