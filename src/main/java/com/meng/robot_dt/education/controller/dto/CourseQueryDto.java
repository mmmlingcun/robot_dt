package com.meng.robot_dt.education.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：cola
 * @description： 实验课程相关接口
 * @date ：2023/1/3 13:50DvMachineryType
 */
@Data
public class CourseQueryDto {

    @ApiModelProperty("实验名称")
    private String name;
}
