package com.meng.robot_dt.education.controller.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.meng.robot_dt.education.entity.Course;
import com.meng.robot_dt.education.entity.PanUser;
import com.meng.robot_dt.education.entity.UserCourse;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.Map;

/**
 * @author ：cola
 * @description： 用户实验课程相关接口
 * @date ：2023/1/3 13:50DvMachineryType
 */
@Data
@NoArgsConstructor
public class UserCourseExcelVo {

    @ColumnWidth(20)
    @ExcelProperty("用户姓名")
    private String username;

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

    public UserCourse getUserCourse(Map<String, PanUser> userMap, Map<String, Course> courseMap) {
        UserCourse userCourse = new UserCourse();
        if (!CollectionUtils.isEmpty(userMap)) {
            userCourse.setPanUser(userMap.get(this.username));
        }
        if (!CollectionUtils.isEmpty(courseMap)) {
            userCourse.setCourse(courseMap.get(this.courseName));
        }
        userCourse.setCreatTime(this.creatTime);
        userCourse.setEndTime(this.endTime);
        userCourse.setDuration(this.duration);
        userCourse.setScore(this.score);
        return userCourse;
    }
}
