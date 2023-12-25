package com.meng.robot_dt.education.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author taorun
 * @date 2023/5/25
 */
@Data
public class PanUserResetPasswordDto {

    @NotBlank(message = "phone cannot be blank")
    private String phone;

    @NotBlank(message = "password cannot be blank")
    private String password;

}
