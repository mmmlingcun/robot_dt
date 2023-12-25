package com.meng.robot_dt.education.entity;

import com.meng.robot_dt.education.common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;


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

}
