package com.meng.robot_dt.education.service.impl;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.meng.robot_dt.education.controller.vo.UserCourseExcelVo;
import com.meng.robot_dt.education.entity.Course;
import com.meng.robot_dt.education.entity.PanUser;
import com.meng.robot_dt.education.entity.UserCourse;
import com.meng.robot_dt.education.handler.ExcelHandler;
import com.meng.robot_dt.education.repository.CourseRepository;
import com.meng.robot_dt.education.repository.PanUserRepository;
import com.meng.robot_dt.education.repository.UserCourseRepository;
import com.meng.robot_dt.education.service.UserCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
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
}
