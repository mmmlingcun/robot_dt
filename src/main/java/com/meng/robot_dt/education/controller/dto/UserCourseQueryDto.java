package com.meng.robot_dt.education.controller.dto;

import com.meng.robot_dt.education.entity.PanUser;
import lombok.Data;

/**
 * @author ：cola
 * @description：
 * @date ：2023/12/26 11:29
 */
@Data
public class UserCourseQueryDto {

    private String courseName;

    private String userName;

    private PanUser.Type userType;

    private String schoolName;
}
