package com.meng.robot_dt.education.common.constant;

/**
 * @author ：cola
 * @description：
 * @date ：2023/12/28 11:52
 */
public class ILabXConstant {

    //获取用户信息接口
    public static final String GET_ACCESS_TOKEN = "%s/open/api/v2/token?ticket=%s&appid=%s&signature=%s";

    //实验结果数据回传接口
    public static final String POST_DATA_UPLOAD = "%s/open/api/v2/data_upload?access_token=%s";

    //获取实验结果数据接口
    public static final String GET_DATA_GET = "%s/open/api/v2/data_get?access_token=%sN&appid=%s&signature=%s";

    //重新获取access_token接口
    public static final String GET_REFRESH_TOKEN = "%s/open/api/v2/token/refresh?access_token=%s&appid=%s&signature=%s";
}
