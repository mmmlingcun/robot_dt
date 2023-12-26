package com.meng.robot_dt.education.controller.vo;

import com.meng.robot_dt.education.entity.UserCourse;
import com.meng.robot_dt.education.util.kit.ConvertKit;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：cola
 * @description： 实验课程相关接口
 * @date ：2023/1/3 13:50DvMachineryType
 */
@Data
public class UserCourseVo {

    private Long id;

    private PanUserVo panUserVo;

    private CourseVo courseVo;

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

    private List<UserCourseStepVo> userCourseSteps;

    public UserCourseVo(UserCourse userCourse) {
        if (userCourse != null) {
            BeanUtils.copyProperties(userCourse, this);
            if (!CollectionUtils.isEmpty(userCourse.getUserCourseSteps())) {
                this.userCourseSteps = userCourse.getUserCourseSteps().stream().map(UserCourseStepVo::new).collect(Collectors.toList());
            }
            if (userCourse.getPanUser() != null) {
                this.panUserVo = ConvertKit.convert(userCourse.getPanUser(), PanUserVo.class);
            }
            if (userCourse.getCourse() != null) {
                this.courseVo = new CourseVo(userCourse.getCourse());
            }
        }
    }
}
