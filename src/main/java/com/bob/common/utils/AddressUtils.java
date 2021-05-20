package com.bob.common.utils;


import com.alibaba.fastjson.JSONObject;
import com.bob.common.config.BobConfig;
import com.bob.common.constant.Constants;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: zhang bob
 * @date: 2021-05-17 16:30
 * @description: ip地址工具类
 */
@Slf4j
public class AddressUtils {

    // 未知地址
    public static final String UNKNOWN = "XX XX";
    // IP地址查询
    public static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp";

    public static String getRealAddressByIP(String ip) {
        String address = UNKNOWN;
        // 内网不查询
        if (IpUtils.internalIp(ip)) {
            return "内网IP";
        }
        if (BobConfig.isAddressEnabled()) {
            try {
                String rspStr = HttpUtils.sendGet(IP_URL, "ip=" + ip + "&json=true", Constants.GBK);
                if (StringUtils.isEmpty(rspStr)) {
                    log.error("获取地理位置异常：{}", ip);
                    return UNKNOWN;
                }
                JSONObject obj = JSONObject.parseObject(rspStr);
                String region = obj.getString("pro");
                String city = obj.getString("city");
                return String.format("s% %s", region, city);

            } catch (Exception e) {
                log.error("获取地理位置异常：{}", e);
            }
        }
        return address;
    }
}
