package com.meng.robot_dt.education.controller.dto.ilab;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author ：cola
 * @description：
 * @date ：2023/12/28 14:35
 */
@Data
public class DataUploadDto {

    @ApiModelProperty("实验空间用户账号")
    private String username;
    @ApiModelProperty("实验名称：用户学习的实验名称（100字以内）")
    private String title;
    @ApiModelProperty("子实验/模块名称：用户学习的子实验名称（100字以内）")
    private String childProjectTitle;
    @ApiModelProperty("实验状态：1 - 完成；2 - 未完成")
    private int status;
    @ApiModelProperty("实验成绩：0 ~100，百分制")
    private int score;
    @ApiModelProperty("实验开始时间：13位时间戳")
    private Long startTime;
    @ApiModelProperty("实验结束时间：13位时间戳")
    private Long endTime;
    @ApiModelProperty("实验用时：非零整数，单位秒")
    private int timeUsed;
    @ApiModelProperty("接入平台编号：由“实验空间”分配给实验教学项目的编号")
    private String appid;
    @ApiModelProperty("实验平台实验记录ID：平台唯一且由大小写字母、数字、“_”组成")
    private String originId;
    @ApiModelProperty("实验步骤记录")
    private List<DataUploadStepsDto> steps;
    @ApiModelProperty("分组标识，最多20个字符")
    private String group_id;
    @ApiModelProperty("分组名称，最多20个字符")
    private String group_name;
    @ApiModelProperty("组里的角色，最多20个字符")
    private String role_in_group;
    @ApiModelProperty("组员名称标识")
    private String group_members;
}
