package com.meng.robot_dt.utils.mqtt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 项目启动 监听主题
 */
@Slf4j
//@Component
public class MQTTListener implements ApplicationListener<ContextRefreshedEvent> {

    private final MQTTConnect server;

    @Autowired
    public MQTTListener(MQTTConnect server) {
        this.server = server;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        try {
//            server.setMqttClient("admin", "public", new Callback());
//            server.sub("joint_msg",0);
//        } catch (MqttException e) {
//            log.error(e.getMessage(), e);
//        }
    }
}

