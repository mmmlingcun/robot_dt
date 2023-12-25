package com.meng.robot_dt.education.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author taorun
 * @date 2023/1/16 11:11
 */
@Data
public class PanUserUpdateDto {

    @NotNull(message = "id cannot be null")
    private long id;

    private String nickname;

    private String phone;

    private Boolean isAdmin;

}
