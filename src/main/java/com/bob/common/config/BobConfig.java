package com.bob.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: zhang bob
 * @date: 2021-04-30 16:33
 * @description: 项目相关配置类
 */
@Component
@ConfigurationProperties(prefix = "bob")
public class BobConfig {
    /** 项目名称 */
    private static String name;

    /** 版本 */
    private static String version;

    /** 版权年份 */
    private static String copyrightYear;

    /** 实例演示开关 */
    private static boolean demoEnabled;

    /** 上传路径 */
    private static String profile;

    /** 获取地址开关 */
    private static boolean addressEnabled;

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        BobConfig.name = name;
    }

    public static String getVersion() {
        return version;
    }

    public static void setVersion(String version) {
        BobConfig.version = version;
    }

    public static String getCopyrightYear() {
        return copyrightYear;
    }

    public static void setCopyrightYear(String copyrightYear) {
        BobConfig.copyrightYear = copyrightYear;
    }

    public static boolean isDemoEnabled() {
        return demoEnabled;
    }

    public static void setDemoEnabled(boolean demoEnabled) {
        BobConfig.demoEnabled = demoEnabled;
    }

    public static String getProfile() {
        return profile;
    }

    public static void setProfile(String profile) {
        BobConfig.profile = profile;
    }

    public static boolean isAddressEnabled() {
        return addressEnabled;
    }

    public static void setAddressEnabled(boolean addressEnabled) {
        BobConfig.addressEnabled = addressEnabled;
    }

    /**
     * 获取头像上传路径
     */
    public static String getAvatarPath() {
        return getProfile() + "/avatar";
    }

    /**
     * 获取下载路径
     */
    public static String getDownloadPath() {
        return getProfile() + "/download/";
    }

    /**
     * 获取上传路径
     */
    public static String getUploadPath() {
        return getProfile() + "/upload";
    }
}
