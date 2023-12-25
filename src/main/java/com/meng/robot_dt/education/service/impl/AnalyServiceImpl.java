package com.meng.robot_dt.education.service.impl;

import com.meng.robot_dt.education.controller.dto.AnalyUpdateDto;
import com.meng.robot_dt.education.entity.Analy;
import com.meng.robot_dt.education.repository.AnalyRepository;
import com.meng.robot_dt.education.service.AnalyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

/**
 * @author taorun
 * @date 2023/1/16 11:17
 */
@Service
public class AnalyServiceImpl implements AnalyService {

    @Autowired
    private AnalyRepository analyRepository;

    @PostConstruct
    private void init() {
        List<Analy> analies = analyRepository.findAll();
        if (CollectionUtils.isEmpty(analies)) {
            Analy analy = new Analy();
            analy.setViewVolumeNum(34512L);
            analy.setExperimentNum(12841L);
            analy.setUserNum(10458L);
            analy.setAverageTimeNum(86L);
            analy.setDoAverageNum(38L);
            analy.setCourseNum(67L);
            analy.setLoginNum(352L);
            analy.setDurationNum(1032L);
            analyRepository.save(analy);
        }
    }

    @Override
    public boolean update(AnalyUpdateDto dto) {
        Optional<Analy> optional = analyRepository.findById(dto.getId());
        if (optional.isPresent()) {
            Analy analy = optional.get();
            BeanUtils.copyProperties(dto, analy);
            return true;
        } else return false;
    }
}
