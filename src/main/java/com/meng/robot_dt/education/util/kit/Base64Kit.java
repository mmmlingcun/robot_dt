package com.meng.robot_dt.education.util.kit;

import java.util.Base64;

/**
 * @Author: TR
 * @Date: 2023/5/25
 */
public class Base64Kit {

    /**
     * base64 加密
     *
     * @param string
     * @return 密文
     */
    public static String encode(String string) {
        return Base64.getEncoder().encodeToString(string.getBytes());
    }

    /**
     * base64 解密
     *
     * @param string
     * @return 明文
     */
    public static String decode(String string) {
        return new String(Base64.getDecoder().decode(string.getBytes()));
    }

}
