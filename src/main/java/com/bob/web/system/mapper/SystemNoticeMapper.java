package com.bob.web.system.mapper;

import com.bob.web.system.domain.SystemNotice;

import java.util.List;

/**
 * 公告 数据层
 *
 * @author bob
 */
public interface SystemNoticeMapper {
    /**
     * 查询公告信息
     *
     * @param noticeId 公告ID
     * @return 公告信息
     */
    SystemNotice selectNoticeById(Long noticeId);

    /**
     * 查询公告列表
     *
     * @param notice 公告信息
     * @return 公告集合
     */
    List<SystemNotice> selectNoticeList(SystemNotice notice);

    /**
     * 新增公告
     *
     * @param notice 公告信息
     * @return 结果
     */
    int insertNotice(SystemNotice notice);

    /**
     * 修改公告
     *
     * @param notice 公告信息
     * @return 结果
     */
    int updateNotice(SystemNotice notice);

    /**
     * 批量删除公告
     *
     * @param noticeIds 需要删除的数据ID
     * @return 结果
     */
    int deleteNoticeByIds(String[] noticeIds);
}