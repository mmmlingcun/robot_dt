package com.meng.robot_dt.education.service.impl;

import com.google.common.collect.Lists;
import com.meng.robot_dt.education.common.exception.NoEntityFoundException;
import com.meng.robot_dt.education.controller.dto.CourseAddDto;
import com.meng.robot_dt.education.controller.dto.CourseQueryDto;
import com.meng.robot_dt.education.entity.Course;
import com.meng.robot_dt.education.repository.CourseRepository;
import com.meng.robot_dt.education.service.CourseService;
import com.meng.robot_dt.education.util.kit.StringKit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * @author taorun
 * @date 2023/1/16 11:17
 */
@Service
public class CourseServiceImpl implements CourseService {

    @Resource
    private CourseRepository courseRepository;

    @Override
    public Course add(CourseAddDto dto) {
        Course course = new Course();
        course.setName(dto.getName());
        course.setMustRead(dto.getMustRead());
        course.setTitle(dto.getTitle());
        return courseRepository.save(course);
    }

    @Override
    public Course findById(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new NoEntityFoundException("No record found by id = " + id));
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        Course course = this.findById(id);
        course.setDeleted(true);
        return true;
    }

    @Override
    public List<Course> findAll(CourseQueryDto queryDto) {
        return courseRepository.findAll(getSpecification(queryDto));
    }

    @Override
    public Page<Course> findPage(CourseQueryDto queryDto, Pageable page) {
        return courseRepository.findAll(getSpecification(queryDto), page);
    }

    private Specification<Course> getSpecification(CourseQueryDto queryDto) {
        return (root, query, builder) -> {
            List<Predicate> predicates = Lists.newArrayList();
            predicates.add(builder.isFalse(root.get("isDeleted")));
            if (StringKit.isNotBlank(queryDto.getName())) {
                predicates.add(builder.like(root.get("name"), "%".concat(queryDto.getName()).concat("%")));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
