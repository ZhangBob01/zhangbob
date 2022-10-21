package com.bob.web.system.service.impl;

import com.bob.common.core.text.Convert;
import com.bob.web.system.domain.SystemOperLog;
import com.bob.web.system.mapper.SystemOperLogMapper;
import com.bob.web.system.service.SystemOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 操作日志 服务层处理
 *
 * @author bob
 */
@Service
public class SystemOperLogServiceImpl implements SystemOperLogService {
    @Autowired
    private SystemOperLogMapper operLogMapper;

    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     */
    @Override
    public void insertOperlog(SystemOperLog operLog) {
        operLogMapper.insertOperlog(operLog);
    }

    /**
     * 查询系统操作日志集合
     *
     * @param operLog 操作日志对象
     * @return 操作日志集合
     */
    @Override
    public List<SystemOperLog> selectOperLogList(SystemOperLog operLog) {
        return operLogMapper.selectOperLogList(operLog);
    }

    /**
     * 批量删除系统操作日志
     *
     * @param ids 需要删除的数据
     * @return
     */
    @Override
    public int deleteOperLogByIds(String ids) {
        return operLogMapper.deleteOperLogByIds(Convert.toStrArray(ids));
    }

    /**
     * 查询操作日志详细
     *
     * @param operId 操作ID
     * @return 操作日志对象
     */
    @Override
    public SystemOperLog selectOperLogById(Long operId) {
        return operLogMapper.selectOperLogById(operId);
    }

    /**
     * 清空操作日志
     */
    @Override
    public void cleanOperLog() {
        operLogMapper.cleanOperLog();
    }
}
