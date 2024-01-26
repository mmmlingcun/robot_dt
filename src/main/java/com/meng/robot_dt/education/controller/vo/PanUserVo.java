package com.meng.robot_dt.education.controller.vo;

import com.meng.robot_dt.education.entity.PanUser;
import lombok.Data;

/**
 * @author taorun
 * @date 2023/1/9 13:06
 */
@Data
public class PanUserVo {

    private Long id;
    private String username;
    private String phone;
    private String nickname;
    private String token;
    private Boolean isAdmin;
    private String schoolName;
    private PanUser.Type type;
    private String userStudentId;

    private String userCode;
}
