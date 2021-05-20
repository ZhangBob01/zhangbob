package com.bob.web.system.service.impl;

import com.bob.common.core.text.Convert;
import com.bob.web.system.domain.SystemLogininfor;
import com.bob.web.system.mapper.SystemLogininforMapper;
import com.bob.web.system.service.SystemLogininforService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: zhang bob
 * @date: 2021-05-18 14:04
 * @description: 系统访问日志信息
 */
@Service
public class SystemLogininforServiceImpl implements SystemLogininforService {

    @Autowired
    private SystemLogininforMapper logininforMapper;

    /**
     * 新增系统登录日志
     *
     * @param logininfor 访问日志对象
     */
    @Override
    public void insertLogininfor(SystemLogininfor logininfor)
    {
        logininforMapper.insertLogininfor(logininfor);
    }

    /**
     * 查询系统登录日志集合
     *
     * @param logininfor 访问日志对象
     * @return 登录记录集合
     */
    @Override
    public List<SystemLogininfor> selectLogininforList(SystemLogininfor logininfor)
    {
        return logininforMapper.selectLogininforList(logininfor);
    }

    /**
     * 批量删除系统登录日志
     *
     * @param ids 需要删除的数据
     * @return
     */
    @Override
    public int deleteLogininforByIds(String ids)
    {
        return logininforMapper.deleteLogininforByIds(Convert.toStrArray(ids));
    }

    /**
     * 清空系统登录日志
     */
    @Override
    public void cleanLogininfor()
    {
        logininforMapper.cleanLogininfor();
    }
}
