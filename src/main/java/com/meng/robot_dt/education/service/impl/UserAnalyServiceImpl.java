package com.meng.robot_dt.education.service.impl;

import com.meng.robot_dt.education.common.exception.NoEntityFoundException;
import com.meng.robot_dt.education.controller.dto.UserAnalyUpdateDto;
import com.meng.robot_dt.education.entity.UserAnaly;
import com.meng.robot_dt.education.repository.UserAnalyRepository;
import com.meng.robot_dt.education.service.PanUserService;
import com.meng.robot_dt.education.service.UserAnalyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author taorun
 * @date 2023/1/16 11:17
 */
@Service
public class UserAnalyServiceImpl implements UserAnalyService {

    @Autowired
    private UserAnalyRepository userAnalyRepository;

    @Autowired
    private PanUserService userService;

    @Override
    @Transactional
    public boolean update(UserAnalyUpdateDto updateDto) {
        Optional<UserAnaly> optional = userAnalyRepository.findByUser_Id(updateDto.getUserId());
        if (optional.isPresent()) {
            UserAnaly userAnaly = optional.get();
            if (updateDto.getDurationNum() != null) {
                userAnaly.setDurationNum(userAnaly.getDurationNum() + 1L);
            }
            if (updateDto.getDoAverageNum() != null) {
                userAnaly.setDoAverageNum(userAnaly.getDoAverageNum() + 1L);
            }
            if (updateDto.getCourseNum() != null) {
                userAnaly.setCourseNum(userAnaly.getCourseNum() + 1L);
            }
            if (updateDto.getLoginNum() != null) {
                userAnaly.setLoginNum(userAnaly.getLoginNum() + 1L);
            }
        } else {
            UserAnaly userAnaly = new UserAnaly();
            userAnaly.setCourseNum(1L);
            userAnaly.setDoAverageNum(1L);
            userAnaly.setDurationNum(1L);
            userAnaly.setLoginNum(1L);
            userAnaly.setUser(userService.findById(updateDto.getUserId()));
            userAnalyRepository.save(userAnaly);
        }
        return true;
    }

    @Override
    public UserAnaly findById(Long id) {
        return userAnalyRepository.findById(id).orElseThrow(() -> new NoEntityFoundException("No user find by id = " + id));
    }
}
