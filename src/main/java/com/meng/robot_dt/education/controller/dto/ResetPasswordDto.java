package com.meng.robot_dt.education.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;


/**
 * @author taorun
 * @date 2023/5/25
 */
@Data
public class ResetPasswordDto {

    @NotBlank(message = "phone cannot be blank")
    private String phone;

    @NotBlank(message = "pastPassword cannot be blank")
    private String pastPassword;

    @NotBlank(message = "newPassword cannot be blank")
    private String newPassword;
}
