package com.meng.robot_dt.education.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：cola
 * @description： 用户分析统计接口
 * @date ：2023/1/3 13:50DvMachineryType
 */
@Data
public class UserAnalyUpdateDto {

    private Long userId;

    @ApiModelProperty("做过实验数量")
    private Long doAverageNum;

    @ApiModelProperty("学习课程数量")
    private Long courseNum;

    @ApiModelProperty("登录次数")
    private Long loginNum;

    @ApiModelProperty("在线时长")
    private Long durationNum;
}
