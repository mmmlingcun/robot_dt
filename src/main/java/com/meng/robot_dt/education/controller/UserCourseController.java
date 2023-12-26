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
}
