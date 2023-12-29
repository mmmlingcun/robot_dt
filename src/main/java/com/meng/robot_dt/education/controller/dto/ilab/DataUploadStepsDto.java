package com.meng.robot_dt.education.controller.dto.ilab;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author ：cola
 * @description：
 * @date ：2023/12/28 14:35
 */
@Data
public class DataUploadStepsDto {

    @ApiModelProperty("实验步骤序号")
    private String seq;
    @ApiModelProperty("步骤名称：100字以内")
    private String title;
    @ApiModelProperty("实验步骤开始时间：13位时间戳")
    private Long startTime;
    @ApiModelProperty("实验步骤结束时间：13位时间戳")
    private Long endTime;
    @ApiModelProperty("实验步骤用时：非零整数，单位秒")
    private int timeUsed;
    @ApiModelProperty("实验步骤合理用时：单位秒")
    private int expectTime;
    @ApiModelProperty("实验步骤得分：0 ~100，百分制")
    private int score;
    @ApiModelProperty("实验步骤满分：0 ~100，百分制")
    private int maxScore;
    @ApiModelProperty("实验步骤操作次数")
    private int repeatCount;
    @ApiModelProperty("步骤评价：200字以内")
    private String evaluation;
    @ApiModelProperty("赋分模型：200字以内")
    private String scoringModel;
    @ApiModelProperty("备注：200字以内")
    private String remarks;
}
