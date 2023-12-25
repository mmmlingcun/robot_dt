package com.meng.robot_dt.education.entity;

import com.meng.robot_dt.education.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

/**
 * @author ：cola
 * @description： 分析统计接口
 * @date ：2023/1/3 13:50DvMachineryType
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Analy extends BaseEntity {

    @ApiModelProperty("浏览量")
    private Long viewVolumeNum;

    @ApiModelProperty("实验人次")
    private Long experimentNum;

    @ApiModelProperty("实验人数")
    private Long userNum;

    @ApiModelProperty("实验平均用时")
    private Long averageTimeNum;

    @ApiModelProperty("做过实验数量")
    private Long doAverageNum;

    @ApiModelProperty("学习课程数量")
    private Long courseNum;

    @ApiModelProperty("登录次数")
    private Long loginNum;

    @ApiModelProperty("在线时长")
    private Long durationNum;
}
