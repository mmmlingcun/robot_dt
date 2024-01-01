package com.meng.robot_dt.education.controller.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.meng.robot_dt.education.entity.UserCourse;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author ：cola
 * @description： 用户实验课程相关接口
 * @date ：2023/1/3 13:50DvMachineryType
 */
@Data
@NoArgsConstructor
public class UserCourseExcelExportVo {

    @ColumnWidth(20)
    @ExcelProperty("实验记录唯一ID")
    private String id;

    @ColumnWidth(20)
    @ExcelProperty("学号")
    private String userStudentId;

    @ColumnWidth(20)
    @ExcelProperty("真实姓名")
    private String username;

    @ColumnWidth(20)
    @ExcelProperty("所在学校")
    private String schoolName;

    @ColumnWidth(20)
    @ExcelProperty("实验名称")
    private String courseName;

    @ColumnWidth(20)
    @ExcelProperty("实验开始时间")
    private Date creatTime;

    @ColumnWidth(20)
    @ExcelProperty("实验结束时间")
    private Date endTime;

    @ColumnWidth(20)
    @ExcelProperty("实验时长")
    private String duration;

    @ColumnWidth(20)
    @ExcelProperty("实验成绩")
    private Integer score;

    public UserCourseExcelExportVo(UserCourse userCourse) {
        this.id = "robot_XNFZ_" + userCourse.getId();
        if (userCourse.getPanUser() != null) {
            this.userStudentId = userCourse.getPanUser().getUserStudentId();
            this.username = userCourse.getPanUser().getUsername();
            this.schoolName = userCourse.getPanUser().getSchoolName();
        }
        if (userCourse.getCourse() != null) {
            this.courseName = userCourse.getCourse().getName();
        }
        this.creatTime = userCourse.getCreatTime();
        this.endTime = userCourse.getEndTime();
        this.duration = userCourse.getDuration();
        this.score = userCourse.getScore();
    }
}
