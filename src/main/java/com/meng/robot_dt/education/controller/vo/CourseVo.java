package com.meng.robot_dt.education.controller.vo;

import com.meng.robot_dt.education.entity.Course;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @author ：cola
 * @description： 实验课程相关接口
 * @date ：2023/1/3 13:50DvMachineryType
 */
@Data
public class CourseVo {

    private Long id;

    @ApiModelProperty("实验名称")
    private String name;

    @ApiModelProperty("实验介绍")
    private String title;

    @ApiModelProperty("实验必读")
    private String mustRead;

    public CourseVo(Course course) {
        if (course != null) {
            BeanUtils.copyProperties(course, this);
        }
    }
}
