package com.meng.robot_dt;

import com.meng.robot_dt.entity.Model;
import com.meng.robot_dt.service.ModelService;
import com.meng.robot_dt.service.SceneService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class RobotDtApplicationTests {
    @Autowired
    SceneService sceneService;

    @Test
    void contextLoads() {
        System.out.println(sceneService.getAllScenes());;
    }

}
