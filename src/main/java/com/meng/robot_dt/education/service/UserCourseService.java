package com.meng.robot_dt.education.service;

import com.meng.robot_dt.education.controller.dto.UserCourseAddDto;
import com.meng.robot_dt.education.controller.dto.UserCourseQueryDto;
import com.meng.robot_dt.education.entity.UserCourse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserCourseService {

    UserCourse add(UserCourseAddDto dto);

    UserCourse findById(Long id);

    List<UserCourse> findAll(UserCourseQueryDto queryDto);

    void excelImport(MultipartFile multipartFile);
}
