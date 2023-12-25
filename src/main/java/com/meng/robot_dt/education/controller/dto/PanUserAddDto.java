package com.meng.robot_dt.education.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author taorun
 * @date 2023/5/25
 */
@Data
public class PanUserAddDto {

    @NotBlank(message = "username cannot be blank")
    private String username;

    @NotBlank(message = "password cannot be blank")
    private String password;

    @NotBlank(message = "phone cannot be blank")
    private String phone;

}