package com.meng.robot_dt.education.entity;

import com.meng.robot_dt.education.common.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import java.util.Date;

import static javax.persistence.EnumType.STRING;

/**
 * @author ：cola
 * @description： 实验课程相关接口
 * @date ：2023/1/3 13:50DvMachineryType
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class UserCourseStep extends BaseEntity {

    private Long id;

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

    @Enumerated(STRING)
    private Type type;

    @Getter
    @AllArgsConstructor
    public enum Type {

        THEORY("理论"),
        PRACTICE("实操");

        private String description;

        public static Type getType(String code) {
            for (Type type : values()) {
                if (type.description.equals(code)) {
                    return type;
                }
            }
            return Type.THEORY;
        }
    }
}
