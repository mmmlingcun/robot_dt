package com.meng.robot_dt.education.controller;

import com.meng.robot_dt.education.common.BaseController;
import com.meng.robot_dt.education.controller.dto.ilab.DataUploadDto;
import com.meng.robot_dt.education.controller.dto.ilab.GetDataDto;
import com.meng.robot_dt.education.service.ILabService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author ：cola
 * @description：国家虚拟仿真实验教学课程平台接口对接
 * @date ：2023/12/28 13:28
 */
@Api(tags = "国家虚拟仿真实验教学课程平台接口对接")
@RestController
@RequestMapping("/ilab")
public class ILabController extends BaseController {

    @Autowired
    private ILabService iLabService;

    /**
     * 国家虚拟仿真实验教学课程平台公网回调接口，需要开放白名单，供教育部接口回调。
     *
     * @param ticket
     * @return
     */
    @GetMapping("/callback")
    public ResponseEntity callBack(@RequestParam String ticket) {
        return ResponseEntity.ok(ticket);
    }

    @GetMapping("/getAccessToken")
    public ResponseEntity getAccessToken(@RequestParam String ticket) {
        return ResponseEntity.ok(iLabService.getAccessToken(ticket));
    }

    @PostMapping("/uploadData")
    @ApiOperation("上传实验数据")
    public ResponseEntity dataUpload(@RequestBody @Valid DataUploadDto dto, @RequestParam String accessToken, BindingResult result) {
        return result.hasErrors() ? this.getErrorResponse(result) : ResponseEntity.ok(iLabService.dataUpload(dto, accessToken));
    }

    @GetMapping("/getData")
    public ResponseEntity getData(GetDataDto dto) {
        return ResponseEntity.ok(iLabService.getData(dto));
    }

    @GetMapping("/getRefreshToken")
    public ResponseEntity getRefreshToken(@RequestParam String token) {
        return ResponseEntity.ok(iLabService.getRefreshToken(token));
    }
}
