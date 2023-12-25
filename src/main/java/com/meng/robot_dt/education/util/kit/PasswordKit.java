package com.meng.robot_dt.education.util.kit;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * MD5 工具类
 *
 * @author taorun
 * @date 2023/05/25
 */
public class PasswordKit {

    private static final String SALT = "pan_salt";

    /**
     * 密码加密
     *
     * @param password
     * @return
     */
    public static String encrypt(String password) {
        return DigestUtils.md5Hex(password + SALT);
    }

}
