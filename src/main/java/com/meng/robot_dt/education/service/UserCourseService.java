package com.meng.robot_dt.education.service;

import org.springframework.web.multipart.MultipartFile;

public interface UserCourseService {

    void excelImport(MultipartFile multipartFile);
}
