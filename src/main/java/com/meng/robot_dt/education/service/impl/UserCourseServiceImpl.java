package com.meng.robot_dt.education.service.impl;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.google.common.collect.Lists;
import com.meng.robot_dt.education.common.exception.BusinessException;
import com.meng.robot_dt.education.common.exception.NoEntityFoundException;
import com.meng.robot_dt.education.controller.dto.UserCourseAddDto;
import com.meng.robot_dt.education.controller.dto.UserCourseQueryDto;
import com.meng.robot_dt.education.controller.dto.UserCourseStepAddDto;
import com.meng.robot_dt.education.controller.vo.UserCourseExcelExportVo;
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
import com.meng.robot_dt.education.util.kit.DateKit;
import com.meng.robot_dt.education.util.kit.StringKit;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

    @Override
    public void excelExport(UserCourseQueryDto queryDto, HttpServletResponse response) {
        try {
            List<UserCourseExcelExportVo> devices = findAll(queryDto).stream().map(UserCourseExcelExportVo::new).collect(toList());
            excelHandler.exportExcel(response, devices, UserCourseExcelExportVo.class, "userCourse", "userCourse", ExcelTypeEnum.XLSX);
        } catch (Exception e) {
            throw new BusinessException("导出失败");
        }
    }

    @Override
    public void initUserCourseStep(UserCourseQueryDto queryDto) {
        List<UserCourse> userCourses = this.findAll(queryDto);
        Random random = new Random();
        List<Integer> step11Random = Lists.newArrayList(20, 21, 16, 17, 18, 19, 15);
        List<Integer> step10Random = Lists.newArrayList(7, 8, 9, 6, 5);
        List<Integer> step12Random = Lists.newArrayList(4, 5);
        List<Integer> step13Random = Lists.newArrayList(5, 6, 4);
        List<Integer> step14Random = Lists.newArrayList(7, 8, 6);
        if (!CollectionUtils.isEmpty(userCourses)) {
            for (UserCourse userCourse : userCourses) {
                List<UserCourseStep> userCourseSteps = Lists.newArrayList();
                if (userCourse.getCreatTime() != null) {
                    UserCourseStep step1 = getUserCourseStep(userCourse.getCreatTime(), "步骤1仿真实验平台系统认知", 10L, 3, 3);
                    UserCourseStep step2 = getUserCourseStep(step1.getEndTime(), "步票2交互式3D仿真操作学习", 10L, 5, 5);
                    UserCourseStep step3 = getUserCourseStep(step2.getEndTime(), "步粟3工业机器人运动学仿真调试", 8L, 6, step13Random.get(random.nextInt(step13Random.size())));
                    UserCourseStep step4 = getUserCourseStep(step3.getEndTime(), "步4 学习模块化编程仿真事件配置", 10L, 6, step13Random.get(random.nextInt(step13Random.size())));
                    UserCourseStep step5 = getUserCourseStep(step4.getEndTime(), "步察5工作站设备模型加载与场景搭建", 12L, 8, step14Random.get(random.nextInt(step14Random.size())));
                    UserCourseStep step6 = getUserCourseStep(step5.getEndTime(), "步聚6~7 搬运工作站仿真事件编程配置", 16L, 8, step14Random.get(random.nextInt(step14Random.size())));
                    UserCourseStep step7 = getUserCourseStep(step6.getEndTime(), "步聚8 搬运工作站仿真事件离线调试", 8L, 5, 5);
                    UserCourseStep step8 = getUserCourseStep(step7.getEndTime(), "步票9~10 配置信号连接实现流程仿真", 16L, 10, step10Random.get(random.nextInt(step10Random.size())));
                    UserCourseStep step9 = getUserCourseStep(step8.getEndTime(), "步骤11 仿真场景保存", 8L, 10, 10);
                    UserCourseStep step10 = getUserCourseStep(step9.getEndTime(), "步聚12 装配生产线模型加载与场景搭建", 16L, 9, step10Random.get(random.nextInt(step10Random.size())));
                    UserCourseStep step11 = getUserCourseStep(step10.getEndTime(), "步骤13~19 装配生产线仿真事件配置", 54L, 21, step11Random.get(random.nextInt(step11Random.size())));
                    UserCourseStep step12 = getUserCourseStep(step11.getEndTime(), "步骤20装配生产线信号流程配置与仿真", 8L, 5, step12Random.get(random.nextInt(step12Random.size())));
                    UserCourseStep step13 = getUserCourseStep(step12.getEndTime(), "步21 仿真场景保存", 8L, 5, step12Random.get(random.nextInt(step12Random.size())));
                    userCourseSteps.add(step1);
                    userCourseSteps.add(step2);
                    userCourseSteps.add(step3);
                    userCourseSteps.add(step4);
                    userCourseSteps.add(step5);
                    userCourseSteps.add(step6);
                    userCourseSteps.add(step7);
                    userCourseSteps.add(step8);
                    userCourseSteps.add(step9);
                    userCourseSteps.add(step10);
                    userCourseSteps.add(step11);
                    userCourseSteps.add(step12);
                    userCourseSteps.add(step13);
                    List<UserCourseStep> steps = userCourseStepRepository.saveAll(userCourseSteps);
                    userCourse.setUserCourseSteps(steps);
                    userCourse.setScore(steps.stream().map(UserCourseStep::getScore).reduce(0, Integer::sum));
                }
            }
            userCourseRepository.saveAll(userCourses);
        }
    }

    private UserCourseStep getUserCourseStep(Date startTime, String name, Long minutes, Integer maxScore, Integer score) {
        UserCourseStep step = new UserCourseStep();
        step.setStartTime(startTime);
        step.setName(name);
        step.setEndTime(DateKit.plusMinutes(startTime, minutes));
        step.setMaxScore(maxScore);
        step.setScore(score);
        step.setType(UserCourseStep.Type.PRACTICE);
        step.setTimeUsed(String.valueOf(minutes * 60));
        return step;
    }

    @SneakyThrows
    @Override
    public void test() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        List<UserCourse> userCourses = this.findAll(new UserCourseQueryDto());
        for (UserCourse userCourse : userCourses) {
            if (!StringUtils.isEmpty(userCourse.getDuration())) {
                Date date = sdf.parse(userCourse.getDuration());
                if (date.getHours() < 3) {
                    userCourse.setEndTime(DateKit.plusHours(userCourse.getCreatTime(), 3L));
                    userCourse.setDuration(sdf.format(DateKit.plusHours(date, 3L)));
                }
            }
        }
        userCourseRepository.saveAll(userCourses);
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
