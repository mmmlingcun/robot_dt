package com.meng.robot_dt.education.controller;

import com.meng.robot_dt.education.common.BaseController;
import com.meng.robot_dt.education.controller.dto.UserCourseAddDto;
import com.meng.robot_dt.education.controller.dto.UserCourseQueryDto;
import com.meng.robot_dt.education.controller.vo.UserCourseVo;
import com.meng.robot_dt.education.service.UserCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author taorun
 * @date 2023/1/16 11:14
 */
@Api(tags = "用户课程")
@RestController
@RequestMapping("/user-course")
public class UserCourseController extends BaseController {

    @Resource
    private UserCourseService userCourseService;

    @ApiOperation("新增")
    @PostMapping
    public ResponseEntity add(@RequestBody @Valid UserCourseAddDto addDto, BindingResult result) {
        return result.hasErrors() ? this.getErrorResponse(result) : ResponseEntity.ok(userCourseService.add(addDto));
    }

    @ApiOperation("查询列表")
    @GetMapping("/list")
    public ResponseEntity findPage(UserCourseQueryDto queryDto) {
        return ResponseEntity.ok(userCourseService.findAll(queryDto).stream().map(UserCourseVo::new));
    }

    /**
     * excel导入-单个sheet
     *
     * @param multipartFile 文件流
     * @return
     * @throws Exception
     */
    @PostMapping("/excelImport/userCourse")
    @ApiOperation(value = "excel导入用户关联课程")
    public ResponseEntity<Object> excelImportUserCourse(@RequestParam("file") MultipartFile multipartFile) {
        userCourseService.excelImport(multipartFile);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/userCourseStep")
    @ApiOperation(value = "初始化步骤")
    public ResponseEntity<Object> init(UserCourseQueryDto queryDto) {
        userCourseService.initUserCourseStep(queryDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/test")
    @ApiOperation(value = "ceshi")
    public ResponseEntity<Object> test111() {
        userCourseService.test();
        return ResponseEntity.ok().build();
    }

    /**
     * excel导出-单个sheet
     *
     * @param response 响应流
     */
    @ApiOperation(value = "excel导出", httpMethod = "GET")
    @GetMapping("/excelExport")
    public void exportCsv(UserCourseQueryDto queryDto, HttpServletResponse response) {
        userCourseService.excelExport(queryDto, response);
    }

    @ApiOperation(value = "对用户不满足三个小时课程的练习时间增加三个小时")
    @GetMapping("/addUserCourseThreeHours")
    public void test() {
        userCourseService.addUserCourseThreeHours();
    }
}
