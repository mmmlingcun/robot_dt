package com.meng.robot_dt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication(scanBasePackages = "com.meng.robot_dt")
public class RobotDtApplication {

    public static void main(String[] args) {
        SpringApplication.run(RobotDtApplication.class, args);
    }

}
