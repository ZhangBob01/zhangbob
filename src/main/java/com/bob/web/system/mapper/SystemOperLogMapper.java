package com.bob.web.system.mapper;

import com.bob.web.system.domain.SystemOperLog;

import java.util.List;

/**
 * 操作日志 数据层
 *
 * @author bob
 */
public interface SystemOperLogMapper {
    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     */
    void insertOperlog(SystemOperLog operLog);

    /**
     * 查询系统操作日志集合
     *
     * @param operLog 操作日志对象
     * @return 操作日志集合
     */
    List<SystemOperLog> selectOperLogList(SystemOperLog operLog);

    /**
     * 批量删除系统操作日志
     *
     * @param ids 需要删除的数据
     * @return 结果
     */
    int deleteOperLogByIds(String[] ids);

    /**
     * 查询操作日志详细
     *
     * @param operId 操作ID
     * @return 操作日志对象
     */
    SystemOperLog selectOperLogById(Long operId);

    /**
     * 清空操作日志
     */
    void cleanOperLog();
}
