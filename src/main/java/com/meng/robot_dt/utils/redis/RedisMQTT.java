package com.meng.robot_dt.utils.redis;

import com.meng.robot_dt.entity.Model;
import com.meng.robot_dt.service.ModelService;
import com.meng.robot_dt.utils.mqtt.MQTTConnect;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RestController;

@Getter
@Setter
@RestController
@Scope("prototype")
public class RedisMQTT implements Runnable{
    @Autowired
    RedisReader redisReader;
    @Autowired
    MQTTConnect mqttConnect;
    @Autowired
    ModelService modelService;

    private String key="ER20-1";
    private String topic=key;
    public volatile boolean exit = false;
    @SneakyThrows
    @Override
    public void run() {
        boolean flag = true;
        while (!exit){
            String msg = redisReader.getMsg(key);
            if(!msg.equals("stop") && flag){
                mqttConnect.pub(topic, msg,0);
            }
            if(flag && redisReader.state.equals("error")){
                Model model = modelService.getModelById(key);
                System.out.println(model.toString());
//                model.setErrorTimes(model.getErrorTimes()+1);
//                modelService.updateModel(model);
            }
            flag = !redisReader.state.equals("error");
            Thread.sleep(16);
        }
    }
}
