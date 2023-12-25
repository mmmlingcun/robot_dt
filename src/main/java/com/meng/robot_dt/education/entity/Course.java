package com.meng.robot_dt.education.entity;

import com.meng.robot_dt.education.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

/**
 * @author ：cola
 * @description： 实验课程相关接口
 * @date ：2023/1/3 13:50DvMachineryType
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Course extends BaseEntity {

    @ApiModelProperty("实验名称")
    private String name;

    @ApiModelProperty("实验介绍")
    private String title;

    @ApiModelProperty("实验必读")
    private String mustRead;
}
