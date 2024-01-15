package com.meng.robot_dt.education.common.constant;

/**
 * @Author: TR
 * @Date: 2023/5/26
 */
public class SystemConst {

    public static final String AUTHORIZATION = "Authorization";

    public static final String PWD_REG = "(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[^a-zA-Z0-9]).{6,30}";
    /**
     * warning displayed when user's pwd does not match pwdReg
     */
    public static final String PWD_REG_WARNING = "密码中必须包含大小写字母、数字、特殊字符，最少6位，最多30位";

}
