package com.meng.robot_dt.service;

import com.meng.robot_dt.utils.redis.RedisMQTT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RMService {
    @Autowired
    RedisMQTT redisMQTT;
    @Autowired
    RedisMQTT redisMQTT2;
    @Autowired
    RedisMQTT redisMQTT3;
    @Autowired
    RedisMQTT redisMQTT4;

    public void connect(){
        redisMQTT.exit = false;
        redisMQTT2.exit = false;
        redisMQTT3.exit = false;
        redisMQTT4.exit = false;
        redisMQTT.setKey("ER20");
        redisMQTT.setTopic("ER20");
        Thread t1 = new Thread(redisMQTT);
        t1.start();
        redisMQTT2.setKey("ER20(1)");
        redisMQTT2.setTopic("ER20(1)");
        Thread t2 = new Thread(redisMQTT2);
        t2.start();
        redisMQTT3.setKey("ER20(1)(1)");
        redisMQTT3.setTopic("ER20(1)(1)");
        Thread t3 = new Thread(redisMQTT3);
        t3.start();
        redisMQTT4.setKey("ER20(1)(1)(1)");
        redisMQTT4.setTopic("ER20(1)(1)(1)");
        Thread t4 = new Thread(redisMQTT4);
        t4.start();
    }

    public void disconnect(){
        redisMQTT.exit = true;
        redisMQTT2.exit = true;
        redisMQTT3.exit = true;
        redisMQTT4.exit = true;
    }
}
