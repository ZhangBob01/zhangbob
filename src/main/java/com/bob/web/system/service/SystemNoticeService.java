package com.bob.web.system.service;

import com.bob.web.system.domain.SystemNotice;

import java.util.List;

/**
 * 公告 服务层
 *
 * @author bob
 */
public interface SystemNoticeService {
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
     * 删除公告信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteNoticeByIds(String ids);
}
