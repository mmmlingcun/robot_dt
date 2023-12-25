package com.meng.robot_dt.education.entity;

import com.meng.robot_dt.education.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import java.util.Date;

/**
 * @author ：cola
 * @description： 实验课程相关接口
 * @date ：2023/1/3 13:50DvMachineryType
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class UserCourseStep extends BaseEntity {

    @ApiModelProperty("步骤名称")
    private String name;

    @ApiModelProperty("开始时间")
    private Date startTime;

    @ApiModelProperty("结束时间")
    private Date endTime;

    @ApiModelProperty("时长")
    private String timeUsed;

    @ApiModelProperty("实验步骤满分")
    private Integer maxScore;

    @ApiModelProperty("实验步骤得分")
    private Integer score;

    @ApiModelProperty("实验步骤操作次数")
    private Integer repeatCount;

    @ApiModelProperty("步骤评价")
    private String evaluation;
}
