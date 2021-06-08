package com.bob.web.system.mapper;

import com.bob.web.system.domain.SystemPost;

import java.util.List;

/**
 * 岗位信息 数据层
 */
public interface SystemPostMapper {

    /**
     * 根据用户id查询岗位列表
     * @param userId
     * @return
     */
    List<SystemPost> findPostListByUserId(Long userId);
}
