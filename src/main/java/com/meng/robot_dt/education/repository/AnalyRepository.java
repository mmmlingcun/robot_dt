package com.meng.robot_dt.education.repository;

import com.meng.robot_dt.education.entity.Analy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AnalyRepository extends JpaRepository<Analy, Long>, JpaSpecificationExecutor<Analy> {

}
