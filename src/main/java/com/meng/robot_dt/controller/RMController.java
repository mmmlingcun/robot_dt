package com.meng.robot_dt.controller;

import com.meng.robot_dt.service.RMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class RMController {

    @Autowired
    RMService rmService;

    @GetMapping("/connect")
    public String connect(){
        rmService.connect();
        return "正在读取redis数据并通过mqtt发布数据……";
    }

    @GetMapping("/disconnect")
    public String disconnect(){
        rmService.disconnect();
        return "停止数据读取";
    }
}