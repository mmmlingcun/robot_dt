package com.meng.robot_dt.education.entity;

import com.meng.robot_dt.education.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

/**
 * @author ：cola
 * @description： 实验课程相关接口
 * @date ：2023/1/3 13:50DvMachineryType
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class UserCourse extends BaseEntity {

    @ManyToOne
    private PanUser panUser;

    @ManyToOne
    private Course course;

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

    @OneToMany
    private List<UserCourseStep> userCourseSteps;
}
