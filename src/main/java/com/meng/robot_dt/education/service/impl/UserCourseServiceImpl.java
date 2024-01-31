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

        List<Integer> step9Random = Lists.newArrayList(7, 8, 9);
        List<Integer> step8Random = Lists.newArrayList(7, 8);
        List<Integer> step6Random = Lists.newArrayList(4, 5, 6);
        List<Integer> step5Random = Lists.newArrayList(4, 5);
        List<Integer> step4Random = Lists.newArrayList(3, 4);
        List<Integer> step3Random = Lists.newArrayList(2, 3);
        List<Integer> step2Random = Lists.newArrayList(1, 2);

        List<Long> step480time = Lists.newArrayList(450L, 452L, 453L, 454L, 460L, 470L, 465L, 463L, 462L, 461L,
                441L, 442L, 443L, 444L, 445L, 446L, 447L, 448L, 449L);
        List<Long> step600time = Lists.newArrayList(570L, 572L, 573L, 574L, 560L, 562L, 565L, 563L, 561L, 575L,
                551L, 552L, 553L, 554L, 555L, 556L, 557L, 558L, 559L);
        List<Long> step720time = Lists.newArrayList(690L, 691L, 692L, 693L, 681L, 683L, 688L, 689L, 678L, 676L,
                670L, 671L, 672L, 673L, 674L, 675L, 676L, 677L, 678L, 679L);
        List<Long> step960time = Lists.newArrayList(930L, 932L, 933L, 934L, 935L, 936L, 926L, 927L, 928L, 922L,
                910L, 911L, 912L, 913L, 914L, 915L, 916L, 917L, 918L, 919L);
        List<Long> step300time = Lists.newArrayList(270L, 271L, 272L, 273L, 274L, 275L, 276L, 268L, 267L, 266L,
                250L, 251L, 252L, 253L, 254L, 255L, 256L, 257L, 258L, 259L);
        List<Long> step360time = Lists.newArrayList(330L, 331L, 332L, 333L, 334L, 335L, 336L, 338L, 337L, 326L,
                310L, 311L, 312L, 313L, 314L, 315L, 316L, 317L, 318L, 319L);

        if (!CollectionUtils.isEmpty(userCourses)) {
            for (UserCourse userCourse : userCourses) {
                List<UserCourseStep> userCourseSteps = Lists.newArrayList();
                if (userCourse.getCreatTime() != null) {
                    UserCourseStep step1 = getUserCourseStep(userCourse.getCreatTime(), "步骤1：实验认知和理论测试",
                            step480time.get(random.nextInt(step480time.size())), 8, step8Random.get(random.nextInt(step8Random.size())));
                    UserCourseStep step2 = getUserCourseStep(step1.getEndTime(), "步骤2：交互式操作3D设备模型",
                            step480time.get(random.nextInt(step480time.size())), 5, step5Random.get(random.nextInt(step5Random.size())));
                    UserCourseStep step3 = getUserCourseStep(step2.getEndTime(), "步骤3：工业机器人运动学仿真调试",
                            step480time.get(random.nextInt(step480time.size())), 6, step6Random.get(random.nextInt(step6Random.size())));
                    UserCourseStep step4 = getUserCourseStep(step3.getEndTime(), "步骤4：模块化编程仿真事件配置",
                            step600time.get(random.nextInt(step600time.size())), 5, step5Random.get(random.nextInt(step5Random.size())));
                    UserCourseStep step5 = getUserCourseStep(step4.getEndTime(), "步骤5：设计构建工业机器人搬运工作站场景",
                            step720time.get(random.nextInt(step720time.size())), 8, step8Random.get(random.nextInt(step8Random.size())));
                    UserCourseStep step6 = getUserCourseStep(step5.getEndTime(), "步骤6：设计配置搬运工作站生产设备仿真事件",
                            step480time.get(random.nextInt(step480time.size())), 3, step3Random.get(random.nextInt(step3Random.size())));
                    UserCourseStep step7 = getUserCourseStep(step6.getEndTime(), "步骤7：设计配置搬运工作站ABB机械臂仿真事件",
                            step480time.get(random.nextInt(step480time.size())), 5, step5Random.get(random.nextInt(step5Random.size())));
                    UserCourseStep step8 = getUserCourseStep(step7.getEndTime(), "步骤8：搬运工作站仿真事件离线调试",
                            step480time.get(random.nextInt(step480time.size())), 5, step5Random.get(random.nextInt(step5Random.size())));
                    UserCourseStep step9 = getUserCourseStep(step8.getEndTime(), "步骤9：设计配置搬运工作站工艺仿真信号流",
                            step480time.get(random.nextInt(step480time.size())), 5, step5Random.get(random.nextInt(step5Random.size())));
                    UserCourseStep step10 = getUserCourseStep(step9.getEndTime(), "步骤10：信号触发搬运工作站工艺流程仿真",
                            step480time.get(random.nextInt(step480time.size())), 5, step5Random.get(random.nextInt(step5Random.size())));
                    UserCourseStep step11 = getUserCourseStep(step10.getEndTime(), "步骤11：工业机器人搬运工作站理论知识考核",
                            step480time.get(random.nextInt(step480time.size())), 5, step5Random.get(random.nextInt(step5Random.size())));
                    UserCourseStep step12 = getUserCourseStep(step11.getEndTime(), "步骤12：设计构建工业机器人装配工作站场景",
                            step960time.get(random.nextInt(step960time.size())), 9, step9Random.get(random.nextInt(step9Random.size())));
                    UserCourseStep step13 = getUserCourseStep(step12.getEndTime(), "步骤13：设计配置装配工作站瓶体井式上料模块仿真事件",
                            step300time.get(random.nextInt(step300time.size())), 2, step2Random.get(random.nextInt(step2Random.size())));
                    UserCourseStep step14 = getUserCourseStep(step13.getEndTime(), "步骤14：设计配置装配工作站横向搬运模组模块仿真事件",
                            step480time.get(random.nextInt(step480time.size())), 3, step3Random.get(random.nextInt(step3Random.size())));
                    UserCourseStep step15 = getUserCourseStep(step14.getEndTime(), "步骤15：设计配置装配工作站伺服皮带运输模块仿真事件",
                            step600time.get(random.nextInt(step600time.size())), 4, step4Random.get(random.nextInt(step4Random.size())));
                    UserCourseStep step16 = getUserCourseStep(step15.getEndTime(), "步骤16：设计配置装配工作站钢珠落料模块仿真事件",
                            step480time.get(random.nextInt(step480time.size())), 2, step2Random.get(random.nextInt(step2Random.size())));
                    UserCourseStep step17 = getUserCourseStep(step16.getEndTime(), "步骤17：设计配置装配工作站瓶盖井式上料模块仿真事件",
                            step300time.get(random.nextInt(step300time.size())), 2, step2Random.get(random.nextInt(step2Random.size())));
                    UserCourseStep step18 = getUserCourseStep(step17.getEndTime(), "步骤18：设计配置装配工作站瓶盖装配模块仿真事件",
                            step360time.get(random.nextInt(step360time.size())), 3, step3Random.get(random.nextInt(step3Random.size())));
                    UserCourseStep step19 = getUserCourseStep(step18.getEndTime(), "步骤19：设计配置装配工作站FA机器人仿真事件",
                            step720time.get(random.nextInt(step720time.size())), 5, step5Random.get(random.nextInt(step5Random.size())));
                    UserCourseStep step20 = getUserCourseStep(step19.getEndTime(), "步骤20：设计配置装配工作站工艺仿真信号流",
                            step480time.get(random.nextInt(step480time.size())), 5, step5Random.get(random.nextInt(step5Random.size())));
                    UserCourseStep step21 = getUserCourseStep(step20.getEndTime(), "步骤21：工业机器人装配工作站理论知识考核",
                            step480time.get(random.nextInt(step480time.size())), 5, step5Random.get(random.nextInt(step5Random.size())));

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
                    userCourseSteps.add(step14);
                    userCourseSteps.add(step15);
                    userCourseSteps.add(step16);
                    userCourseSteps.add(step17);
                    userCourseSteps.add(step18);
                    userCourseSteps.add(step19);
                    userCourseSteps.add(step20);
                    userCourseSteps.add(step21);
                    List<UserCourseStep> steps = userCourseStepRepository.saveAll(userCourseSteps);
                    userCourse.setUserCourseSteps(steps);
                    userCourse.setScore(steps.stream().map(UserCourseStep::getScore).reduce(0, Integer::sum));
                }
            }
            userCourseRepository.saveAll(userCourses);
        }
    }

    private UserCourseStep getUserCourseStep(Date startTime, String name, Long seconds, Integer maxScore, Integer score) {
        UserCourseStep step = new UserCourseStep();
        step.setStartTime(startTime);
        step.setName(name);
        step.setEndTime(DateKit.plusSeconds(startTime, seconds));
        step.setMaxScore(maxScore);
        step.setScore(score);
        step.setType(UserCourseStep.Type.PRACTICE);
        step.setTimeUsed(String.valueOf(seconds));
        return step;
    }

    @SneakyThrows
    @Override
    public void addUserCourseThreeHours() {
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

    @Override
    public void test() {
        List<UserCourse> userCourses = this.findAll(new UserCourseQueryDto());
        for (UserCourse userCourse : userCourses) {
            List<UserCourseStep> userCourseSteps = userCourse.getUserCourseSteps();
            for (UserCourseStep userCourseStep : userCourseSteps) {
                if (userCourseStep.getName().contains("步骤4:")) {
                    if (userCourseStep.getScore() > 5) {
                        userCourseStep.setScore(userCourseStep.getScore() - 1);
                        userCourse.setScore(userCourse.getScore() - 1);
                    }
                }
            }
            userCourseStepRepository.saveAll(userCourseSteps);
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
            if (StringKit.isNotBlank(queryDto.getSchoolName())) {
                predicates.add(builder.like(root.get("panUser").get("schoolName"), "%".concat(queryDto.getSchoolName()).concat("%")));
            }
            if (queryDto.getUserType() != null) {
                predicates.add(builder.equal(root.get("panUser").get("type"), queryDto.getUserType()));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
