package com.meng.robot_dt.education.controller;

import com.meng.robot_dt.education.controller.dto.AnalyUpdateDto;
import com.meng.robot_dt.education.controller.dto.UserAnalyUpdateDto;
import com.meng.robot_dt.education.controller.dto.UserCourseQueryDto;
import com.meng.robot_dt.education.repository.AnalyRepository;
import com.meng.robot_dt.education.repository.UserAnalyRepository;
import com.meng.robot_dt.education.service.AnalyService;
import com.meng.robot_dt.education.service.UserAnalyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author taorun
 * @date 2023/1/16 11:14
 */
@Api(tags = "统计")
@RestController
@RequestMapping("/analy")
public class AnalyController {

    @Resource
    private AnalyService analyService;

    @Resource
    private UserAnalyService userAnalyService;

    @Resource
    private UserAnalyRepository userAnalyRepository;

    @Resource
    private AnalyRepository analyRepository;

    @ApiOperation("更新")
    @PutMapping("/update")
    public ResponseEntity update(AnalyUpdateDto updateDto) {
        return ResponseEntity.ok(analyService.update(updateDto));
    }

    @ApiOperation("查询")
    @PutMapping("/find")
    public ResponseEntity find() {
        return ResponseEntity.ok(analyRepository.findAll());
    }

    @ApiOperation("更新用户")
    @PutMapping("/user-analy/update")
    public ResponseEntity userAnalyUpdate(UserAnalyUpdateDto updateDto) {
        return ResponseEntity.ok(userAnalyService.update(updateDto));
    }

    @ApiOperation("查询用户")
    @PutMapping("/user-analy/find")
    public ResponseEntity userAnalyFind(@RequestParam Long userId) {
        return ResponseEntity.ok(userAnalyRepository.findByUser_Id(userId).orElse(null));
    }

    @ApiOperation("统计")
    @GetMapping("/calculate")
    public ResponseEntity calculate(UserCourseQueryDto queryDto) {
        return ResponseEntity.ok(analyService.calculate(queryDto));
    }
}
