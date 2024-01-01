package com.meng.robot_dt.education.service;

import com.meng.robot_dt.education.controller.dto.AnalyUpdateDto;
import com.meng.robot_dt.education.controller.vo.AnalyVo;

public interface AnalyService {

    boolean update(AnalyUpdateDto updateDto);

    AnalyVo calculate();
}
