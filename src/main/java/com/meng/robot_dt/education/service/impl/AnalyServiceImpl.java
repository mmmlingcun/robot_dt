package com.meng.robot_dt.education.service.impl;

import com.google.common.collect.Maps;
import com.meng.robot_dt.education.controller.dto.AnalyUpdateDto;
import com.meng.robot_dt.education.controller.dto.UserCourseQueryDto;
import com.meng.robot_dt.education.controller.vo.AnalyVo;
import com.meng.robot_dt.education.entity.Analy;
import com.meng.robot_dt.education.entity.UserCourse;
import com.meng.robot_dt.education.repository.AnalyRepository;
import com.meng.robot_dt.education.service.AnalyService;
import com.meng.robot_dt.education.service.UserCourseService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * @author taorun
 * @date 2023/1/16 11:17
 */
@Service
public class AnalyServiceImpl implements AnalyService {

    @Autowired
    private AnalyRepository analyRepository;

    @Autowired
    private UserCourseService userCourseService;

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
        } else {
            return false;
        }
    }

    @SneakyThrows
    @Override
    public AnalyVo calculate(UserCourseQueryDto queryDto) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        AnalyVo result = new AnalyVo();
        List<UserCourse> userCourses = userCourseService.findAll(queryDto);
        //实验人次
        result.setExpNum(userCourses.size());
        int expTime = 0;
        double scoreA = 0;
        double scoreB = 0;
        for (UserCourse userCourse : userCourses) {
            if (userCourse.getScore() != null && !StringUtils.isEmpty(userCourse.getDuration())) {
                if (userCourse.getScore() >= 90) {
                    scoreA++;
                } else {
                    scoreB++;
                }
                expTime = expTime + sdf.parse(userCourse.getDuration()).getHours() * 60 + sdf.parse(userCourse.getDuration()).getMinutes() + 1;
            }
        }
        //实验平均用时
        result.setExpTime(String.valueOf(expTime / userCourses.size()));

        //计算实验通过占比
        Map<String, Double> map = Maps.newHashMap();
        map.put("优秀", BigDecimal.valueOf((scoreA / userCourses.size()) * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        map.put("达标", BigDecimal.valueOf((scoreB / userCourses.size()) * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        result.setExpPassPro(map);
        return result;
    }
}
