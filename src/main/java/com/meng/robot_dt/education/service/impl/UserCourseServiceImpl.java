package com.meng.robot_dt.education.service.impl;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.google.common.collect.Lists;
import com.meng.robot_dt.education.common.exception.NoEntityFoundException;
import com.meng.robot_dt.education.controller.dto.UserCourseAddDto;
import com.meng.robot_dt.education.controller.dto.UserCourseQueryDto;
import com.meng.robot_dt.education.controller.dto.UserCourseStepAddDto;
import com.meng.robot_dt.education.controller.vo.UserCourseExcelVo;
import com.meng.robot_dt.education.entity.Course;
import com.meng.robot_dt.education.entity.PanUser;
import com.meng.robot_dt.education.entity.UserCourse;
import com.meng.robot_dt.education.entity.UserCourseStep;
import com.meng.robot_dt.education.handler.ExcelHandler;
import com.meng.robot_dt.education.repository.CourseRepository;
import com.meng.robot_dt.education.repository.PanUserRepository;
import com.meng.robot_dt.education.repository.UserCourseRepository;
import com.meng.robot_dt.education.repository.UserCourseStepRepository;
import com.meng.robot_dt.education.service.PanUserService;
import com.meng.robot_dt.education.service.UserCourseService;
import com.meng.robot_dt.education.util.kit.StringKit;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * @author taorun
 * @date 2023/1/16 11:17
 */
@Service
public class UserCourseServiceImpl implements UserCourseService {

    @Resource
    private PanUserRepository panUserRepository;

    @Autowired
    private ExcelHandler excelHandler;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserCourseRepository userCourseRepository;

    @Autowired
    private UserCourseStepRepository userCourseStepRepository;

    @Autowired
    private PanUserService panUserService;

    @Override
    public UserCourse add(UserCourseAddDto dto) {
        UserCourse userCourse = new UserCourse();
        List<UserCourseStep> userCourseSteps = Lists.newArrayList();
        if (dto.getId() != null) {
            userCourse = findById(dto.getId());
            userCourseSteps = userCourse.getUserCourseSteps();
        }
        BeanUtils.copyProperties(dto, userCourse);
        if (dto.getUserId() != null) {
            userCourse.setPanUser(panUserService.findById(dto.getUserId()));
        }
        if (dto.getCourseId() != null) {
            userCourse.setCourse(courseRepository.findById(dto.getCourseId()).orElse(null));
        }
        List<UserCourseStepAddDto> dtos = dto.getSteps();
        if (!CollectionUtils.isEmpty(dtos)) {
            List<UserCourseStep> steps = dtos.stream().map(x -> {
                UserCourseStep courseStep = new UserCourseStep();
                BeanUtils.copyProperties(x, courseStep);
                return courseStep;
            }).collect(toList());
            userCourseStepRepository.saveAll(steps);
            userCourseSteps.addAll(steps);
            userCourse.setUserCourseSteps(userCourseSteps);
        }
        userCourseRepository.save(userCourse);
        return userCourse;
    }

    @Override
    public UserCourse findById(Long id) {
        return userCourseRepository.findById(id).orElseThrow(() -> new NoEntityFoundException("No record found by id = " + id));
    }

    @Override
    public List<UserCourse> findAll(UserCourseQueryDto queryDto) {
        return userCourseRepository.findAll(getSpecification(queryDto));
    }

    @Override
    public void excelImport(MultipartFile multipartFile) {
        List<Course> courses = courseRepository.findAll();
        Map<String, Course> courseMap = courses.stream().collect(toMap(Course::getName, y -> y));
        List<PanUser> panUsers = panUserRepository.findAll();
        Map<String, PanUser> userMap = panUsers.stream().collect(toMap(PanUser::getUsername, y -> y));
        try {
            List<UserCourse> userCourses = excelHandler.importExcel(multipartFile, UserCourseExcelVo.class, null, ExcelTypeEnum.XLSX).stream().map(x -> x.getUserCourse(userMap, courseMap)).collect(toList());
            userCourseRepository.saveAll(userCourses);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Specification<UserCourse> getSpecification(UserCourseQueryDto queryDto) {
        return (root, query, builder) -> {
            List<Predicate> predicates = Lists.newArrayList();
            predicates.add(builder.isFalse(root.get("isDeleted")));
            if (StringKit.isNotBlank(queryDto.getCourseName())) {
                predicates.add(builder.like(root.get("course").get("name"), "%".concat(queryDto.getCourseName()).concat("%")));
            }
            if (StringKit.isNotBlank(queryDto.getUserName())) {
                predicates.add(builder.like(root.get("panUser").get("username"), "%".concat(queryDto.getUserName()).concat("%")));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
