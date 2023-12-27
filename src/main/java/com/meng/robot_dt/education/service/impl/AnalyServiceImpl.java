package com.meng.robot_dt.education.service.impl;

import com.meng.robot_dt.education.controller.dto.AnalyUpdateDto;
import com.meng.robot_dt.education.entity.Analy;
import com.meng.robot_dt.education.repository.AnalyRepository;
import com.meng.robot_dt.education.service.AnalyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;

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
            analyRepository.save(analy);
        }
    }

    @Override
    @Transactional
    public boolean update(AnalyUpdateDto updateDto) {
        List<Analy> analies = analyRepository.findAll();
        if (!CollectionUtils.isEmpty(analies)) {
            analies.forEach(item -> {
                if (updateDto.getExperimentNum() != null) {
                    item.setExperimentNum(item.getExperimentNum() + 1);
                }
                if (updateDto.getUserNum() != null) {
                    item.setUserNum(item.getUserNum() + 1);
                }
                if (updateDto.getAverageTimeNum() != null) {
                    item.setAverageTimeNum(item.getAverageTimeNum() + 1);
                }
                if (updateDto.getViewVolumeNum() != null) {
                    item.setViewVolumeNum(item.getViewVolumeNum() + 1);
                }
            });
            return true;
        } else return false;
    }
}
