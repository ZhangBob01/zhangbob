package com.bob.framework.web.service;

import com.bob.web.system.service.SystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: zhang bob
 * @date: 2021-06-16 15:05
 * @description: 参数管理：用于取值
 */
@Service("config")
public class ConfigService {
    @Autowired
    private SystemConfigService systemConfigService;

    /**
     * 根据key查询参数配置
     * @param key
     * @return
     */
    public String getKey(String key) {
        return systemConfigService.selectConfigByKey(key);
    }
}
