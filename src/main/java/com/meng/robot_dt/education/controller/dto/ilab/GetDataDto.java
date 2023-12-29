package com.meng.robot_dt.education.controller.dto.ilab;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author ：cola
 * @description：
 * @date ：2023/12/28 15:36
 */
@Data
public class GetDataDto {

    @NotNull(message = "接口调用令牌不能为空！")
    @ApiModelProperty("接口调用令牌")
    private String accessToken;
    @ApiModelProperty("查询开始记录，最小数为1(非必填)")
    private Integer start;
    @ApiModelProperty("实验空间的用户名(非必填)")
    private String username;
    @ApiModelProperty("实验平台实验记录ID(非必填)")
    private String originId;
    @ApiModelProperty("实验平台实验记录ID(非必填)")
    private String groupId;
}
