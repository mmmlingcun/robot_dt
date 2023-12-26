package com.meng.robot_dt.education.service;

import com.meng.robot_dt.education.controller.dto.CourseAddDto;
import com.meng.robot_dt.education.controller.dto.CourseQueryDto;
import com.meng.robot_dt.education.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseService {

    Course add(CourseAddDto dto);

    Course findById(Long id);

    boolean delete(Long id);

    List<Course> findAll(CourseQueryDto queryDto);

    Page<Course> findPage(CourseQueryDto queryDto, Pageable page);
}
