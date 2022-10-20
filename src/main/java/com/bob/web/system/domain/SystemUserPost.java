package com.bob.web.system.domain;

import lombok.Data;

/**
 * @author: zhang bob
 * @date: 2022-10-19 19:18
 * @description: 用户和岗位关联 sys_user_post
 */

@Data
public class SystemUserPost {
    /** 用户ID */
    private Long userId;

    /** 岗位ID */
    private Long postId;
}
