package com.meng.robot_dt.education.service;

import com.meng.robot_dt.education.controller.dto.UserAnalyUpdateDto;
import com.meng.robot_dt.education.entity.UserAnaly;

public interface UserAnalyService {

    boolean update(UserAnalyUpdateDto updateDto);

    UserAnaly findById(Long id);
}
