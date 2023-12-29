package com.meng.robot_dt.education.controller.vo.ilab;

import com.meng.robot_dt.education.common.result.ILableResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ：cola
 * @description：
 * @date ：2023/12/28 14:19
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AccessTokenVo extends ILableResult {

    @ApiModelProperty("用户名")
    private String un;
    @ApiModelProperty("用户姓名")
    private String dis;
    @ApiModelProperty("接口调用凭证")
    private String accessToken;
    @ApiModelProperty("access_token创建时间，13位时间戳")
    private String createTime;
    @ApiModelProperty("创建时间示例")
    private String createTimeDisplay;
    @ApiModelProperty("过期时间，13位时间戳")
    private String expiresTime;
    @ApiModelProperty("过期时间示例")
    private String expiresTimeDisplay;

    @Override
    public String toString() {
        return "AccessTokenVo{" +
                "un='" + un + '\'' +
                ", dis='" + dis + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", createTime='" + createTime + '\'' +
                ", createTimeDisplay='" + createTimeDisplay + '\'' +
                ", expiresTime='" + expiresTime + '\'' +
                ", expiresTimeDisplay='" + expiresTimeDisplay + '\'' +
                ", code='" + super.getCode() + '\'' +
                ", msg='" + super.getMsg() + '\'' +
                '}';
    }
}
