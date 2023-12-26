package com.meng.robot_dt.education.repository;

import com.meng.robot_dt.education.entity.UserCourseStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserCourseStepRepository extends JpaRepository<UserCourseStep, Long>, JpaSpecificationExecutor<UserCourseStep> {

}
