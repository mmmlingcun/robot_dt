package com.meng.robot_dt.education.controller.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * @author ：cola
 * @description： 实验课程相关接口
 * @date ：2023/1/3 13:50DvMachineryType
 */
@Data
public class AnalyVo {

    @ApiModelProperty("实验人次")
    private Integer expNum;

    @ApiModelProperty("实验平均用时")
    private String expTime;

    @ApiModelProperty("实验完成率")
    private Double expSuccessRate;

    @ApiModelProperty("实验通过率")
    private Double expPassRate;

    @ApiModelProperty("实验通过占比")
    private Map<String, Double> expPassPro;
}
