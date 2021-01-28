package com.bob.common.config.datasource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: zhang bob
 * @date: 2021-01-28 10:17
 * @description: 数据源切换处理类
 */
@Slf4j
public class DynamicDataSourceContextHolder {

    /**
     * 使用ThreadLocal维护变量，ThreadLocal为每个使用该变量的线程提供独立的变量副本
     * 每个线程都可以独立的改变自己的副本，不会影响其他线程多对应的副本
     */
    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * 设置数据源变量
     * @param dataSourceType
     */
    public static void setDataSourceType(String dataSourceType){
        log.info("--------------------切换数据源操作：数据源切换至{}",dataSourceType);
        CONTEXT_HOLDER.set(dataSourceType);
    }

    /**
     * 获取数据库源
     * @return
     */
    public static String getDataSourceType() {
        return CONTEXT_HOLDER.get();
    }

    public static void clearDataSourceType() {
        CONTEXT_HOLDER.remove();
    }
}
