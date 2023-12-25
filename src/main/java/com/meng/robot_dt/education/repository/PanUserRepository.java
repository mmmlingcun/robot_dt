package com.meng.robot_dt.education.repository;

import com.meng.robot_dt.education.entity.PanUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface PanUserRepository extends JpaRepository<PanUser, Long>, JpaSpecificationExecutor<PanUser> {

    Optional<PanUser> findByPhone(String phone);

    Optional<PanUser> findByUsername(String username);

}
