package com.meng.robot_dt.education.service;

import com.meng.robot_dt.education.controller.dto.*;
import com.meng.robot_dt.education.controller.vo.PanUserVo;
import com.meng.robot_dt.education.entity.PanUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface PanUserService {

    PanUserVo register(PanUserAddDto addDto);

    PanUserVo login(PanUserLoginDto loginDto);

    Boolean logout(Long userId);

    PanUser findById(Long userId);

    PanUser findByPhone(String phone);

    PanUser findByUsername(String username);

    boolean update(PanUserUpdateDto updateDto);

    boolean delete(Long id);

    PanUserVo resetPassword(ResetPasswordDto resetDto);

    Page<PanUser> findPage(PanUserQueryDto queryDto, Pageable page);

    void test();

    boolean checkUnique(PanUserCheckDto checkDto);

    void excelImport(MultipartFile multipartFile);
}
