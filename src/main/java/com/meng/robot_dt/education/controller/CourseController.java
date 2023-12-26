package com.meng.robot_dt.education.controller;

import com.meng.robot_dt.education.common.BaseController;
import com.meng.robot_dt.education.controller.dto.CourseAddDto;
import com.meng.robot_dt.education.controller.dto.CourseQueryDto;
import com.meng.robot_dt.education.controller.vo.CourseVo;
import com.meng.robot_dt.education.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author taorun
 * @date 2023/1/16 11:14
 */
@Api(tags = "课程")
@RestController
@RequestMapping("/course")
public class CourseController extends BaseController {

    @Autowired
    private CourseService courseService;

    @ApiOperation("新增")
    @PostMapping
    public ResponseEntity add(@RequestBody @Valid CourseAddDto addDto, BindingResult result) {
        return result.hasErrors() ? this.getErrorResponse(result) : ResponseEntity.ok(courseService.add(addDto));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除")
    public ResponseEntity delete(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.delete(id));
    }

    @GetMapping("/id")
    @ApiOperation("通过ID查询")
    public ResponseEntity findById(Long id) {
        return ResponseEntity.ok(new CourseVo(courseService.findById(id)));
    }

    @GetMapping("/list")
    @ApiOperation("查询列表")
    public ResponseEntity findList(CourseQueryDto queryDto) {
        return this.findByList(queryDto, x -> courseService.findAll(x), CourseVo::new);
    }

    @GetMapping("/page")
    @ApiOperation("分页查询")
    public ResponseEntity findPage(CourseQueryDto queryDto, @PageableDefault Pageable page) {
        return this.findByPage(queryDto, page, (x, y) -> courseService.findPage(x, y), CourseVo::new);
    }
}
