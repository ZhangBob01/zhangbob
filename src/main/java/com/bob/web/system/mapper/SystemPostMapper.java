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

    /**
     * 查询岗位数据集合
     *
     * @param post 岗位信息
     * @return 岗位数据集合
     */
    List<SystemPost> selectPostList(SystemPost post);

    /**
     * 查询所有岗位
     *
     * @return 岗位列表
     */
    List<SystemPost> selectPostAll();

    /**
     * 根据用户ID查询岗位
     *
     * @param userId 用户ID
     * @return 岗位列表
     */
    List<SystemPost> selectPostsByUserId(Long userId);

    /**
     * 通过岗位ID查询岗位信息
     *
     * @param postId 岗位ID
     * @return 角色对象信息
     */
    SystemPost selectPostById(Long postId);

    /**
     * 批量删除岗位信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deletePostByIds(Long[] ids);

    /**
     * 修改岗位信息
     *
     * @param post 岗位信息
     * @return 结果
     */
    int updatePost(SystemPost post);

    /**
     * 新增岗位信息
     *
     * @param post 岗位信息
     * @return 结果
     */
    int insertPost(SystemPost post);

    /**
     * 校验岗位名称
     *
     * @param postName 岗位名称
     * @return 结果
     */
    SystemPost checkPostNameUnique(String postName);

    /**
     * 校验岗位编码
     *
     * @param postCode 岗位编码
     * @return 结果
     */
    SystemPost checkPostCodeUnique(String postCode);
}
