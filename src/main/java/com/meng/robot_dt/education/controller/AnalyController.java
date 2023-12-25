package com.meng.robot_dt.education.controller;

import com.meng.robot_dt.education.controller.dto.AnalyUpdateDto;
import com.meng.robot_dt.education.repository.AnalyRepository;
import com.meng.robot_dt.education.service.AnalyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

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
    private AnalyRepository analyRepository;

    @ApiOperation("更新")
    @PutMapping("/update")
    public ResponseEntity updateMQ(@RequestBody @Valid AnalyUpdateDto updateDto) {
        return ResponseEntity.ok(analyService.update(updateDto));
    }

    @ApiOperation("查询")
    @PutMapping("/find")
    public ResponseEntity find() {
        return ResponseEntity.ok(analyRepository.findAll());
    }
}
