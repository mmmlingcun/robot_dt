package com.meng.robot_dt.education.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @author ：cola
 * @description： 网络请求
 * @date ：2023/12/28 11:33
 */
@Slf4j
public class HttpUtils {

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param urlPath     发送请求的URL
     * @param requestBody 发送请求的body
     * @return 返回的数据
     */
    public static String sendPostRequest(String urlPath, String requestBody) {
        String result = null;
        try {
            URL url = new URL(urlPath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    // 打印JSON格式的响应数据
                    log.info(response.toString());
                    result = response.toString();
                }
            } else {
                log.error("POST请求未成功，响应码：" + responseCode);
            }
        } catch (Exception e) {
            log.error("POST请求未成功");
        }
        return result;
    }

    /**
     * 向指定 URL 发送Get方法的请求
     *
     * @param url
     * @return
     */
    public static String sendGetRequest(String url) {
        String result = null;
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    result = response.toString();
                }
            } else {
                log.error("GET请求未成功，响应码：" + responseCode);
            }
        } catch (IOException e) {
            log.error("GET请求未成功");
        }
        return result;
    }
}
