package com.meng.robot_dt.education.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author ：cola
 * @description：
 * @date ：2023/12/25 19:47
 */
@Data
public class UserCourseAddDto {

    private Long id;

    @ApiModelProperty("用户名称")
    private String username;

    @ApiModelProperty("实验课程名称")
    private String courseName;

    @ApiModelProperty("实验状态：1 - 完成；2 - 未完成")
    private Integer status;

    @ApiModelProperty("开始时间")
    private Date creatTime;

    @ApiModelProperty("结束时间")
    private Date endTime;

    @ApiModelProperty("时长")
    private String duration;

    @ApiModelProperty("分数")
    private Integer score;

    private List<UserCourseStepAddDto> steps;
}
