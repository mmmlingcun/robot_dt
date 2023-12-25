package com.meng.robot_dt.education.controller.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.meng.robot_dt.education.entity.PanUser;
import com.meng.robot_dt.education.util.kit.PasswordKit;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liqs
 * @description:
 * @date 2023/7/13 14:20
 */

@Data
@NoArgsConstructor
public class UserExcelVo {

    @ColumnWidth(20)
    @ExcelProperty("用户姓名")
    private String username;

    @ColumnWidth(20)
    @ExcelProperty("用户学号")
    private String userStudentId;

    @ColumnWidth(20)
    @ExcelProperty("用户账号")
    private String userCode;

    @ColumnWidth(20)
    @ExcelProperty("学校名称")
    private String schoolName;

    public PanUser getPanUser() {
        PanUser user = new PanUser();
        user.setUsername(this.username);
        user.setPassword(PasswordKit.encrypt("123456"));
        user.setType(PanUser.Type.STUDENT);
        user.setUserStudentId(this.userStudentId);
        user.setUserCode(this.userCode);
        user.setIsAdmin(false);
        user.setNickname(this.username);
        user.setSchoolName(this.schoolName);
        return user;
    }

}
