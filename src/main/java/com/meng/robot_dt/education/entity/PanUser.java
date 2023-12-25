package com.meng.robot_dt.education.entity;

import com.meng.robot_dt.education.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;


@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class PanUser extends BaseEntity {

    private String username;
    private String password;
    private String phone;
    private String nickname;

    /**
     * 本项目只需要区分是否管理员，无需单独建角色表
     */
    private Boolean isAdmin;

    @Enumerated(EnumType.STRING)
    private Type type;

    private String schoolName;

    private String userStudentId;

    private String userCode;

    @Getter
    public enum Type {

        TEACHER("老师"),
        STUDENT("学生"),
        SOCIETY("社会人员");

        private String description;

        Type(String description) {
            this.description = description;
        }
    }

}
