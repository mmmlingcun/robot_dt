package com.meng.robot_dt.education.repository;

import com.meng.robot_dt.education.entity.UserAnaly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserAnalyRepository extends JpaRepository<UserAnaly, Long>, JpaSpecificationExecutor<UserAnaly> {

    Optional<UserAnaly> findByUser_Id(Long userId);
}
