package com.bob.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author: zhang bob
 * @date: 2021-05-18 11:53
 * @description: http请求工具类
 */
@Slf4j
public class HttpUtils {

    /**
     * 向指定的URL发送get请求
     * @param ipUrl
     * @param s
     * @param gbk
     * @return
     */
    public static String sendGet(String ipUrl, String s, String gbk) {
        StringBuilder sb = new StringBuilder();
        BufferedReader in = null;
        try {
            String urlNameString = ipUrl + "?" + s;
            log.info("sendGet - {}", urlNameString);
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), gbk));
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            log.info("recv - {}", sb);
        } catch (ConnectException e){
            log.error("调用HttpUtils.sendGet ConnectException, url=" + ipUrl + ",param=" + s, e);
        } catch (SocketTimeoutException e) {
            log.error("调用HttpUtils.sendGet SocketTimeoutException, url=" + ipUrl + ",param=" + s, e);
        } catch (IOException e) {
            log.error("调用HttpUtils.sendGet IOException, url=" + ipUrl + ",param=" + s, e);
        } catch (Exception e) {
            log.error("调用HttpsUtil.sendGet Exception, url=" + ipUrl + ",param=" + s, e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                log.error("调用in.close Exception, url=" + ipUrl + ",param=" + s, e);
            }
        }

        return sb.toString();
    }
}
