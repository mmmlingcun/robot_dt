package com.meng.robot_dt.education.controller;

import com.meng.robot_dt.education.common.constant.SystemConst;
import com.meng.robot_dt.education.controller.dto.*;
import com.meng.robot_dt.education.controller.vo.PanUserVo;
import com.meng.robot_dt.education.entity.PanUser;
import com.meng.robot_dt.education.service.PanUserService;
import com.meng.robot_dt.education.util.kit.Base64Kit;
import com.meng.robot_dt.education.util.kit.ConvertKit;
import com.meng.robot_dt.education.util.kit.PasswordKit;
import com.meng.robot_dt.education.util.kit.TokenKit;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author taorun
 * @date 2023/1/16 11:14
 */
@Api(tags = "用户")
@RestController
@RequestMapping("/user")
public class PanUserController {

    @Resource
    private PanUserService panUserService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @ApiOperation("检查用户唯一性")
    @GetMapping("/check-unique")
    public ResponseEntity checkUniqueMQ(PanUserCheckDto checkDto) {
        return ResponseEntity.ok(panUserService.checkUnique(checkDto));
    }

    @ApiOperation("注册")
    @PostMapping("/register")
    public ResponseEntity registerMQ(@RequestBody @Valid PanUserAddDto addDto) {
        //校验密码复杂度
        String pwdRegErr = checkPwdRegex(addDto.getPassword());
        if (!StringUtils.isEmpty(pwdRegErr)) {
            return ResponseEntity.badRequest().body(pwdRegErr);
        }
        return ResponseEntity.ok(panUserService.register(addDto));
    }

    @ApiOperation(value = "登录", notes = "注：密码要先转换 base64 格式，示例：admin YWRtaW4= ; tr MTExMTEx")
    @PostMapping("/login")
    public ResponseEntity loginMQ(@RequestBody @Valid PanUserLoginDto loginDto, HttpServletRequest request) {
        //校验输入错误次数
        String keyStr = String.format(RedisConstant.LOGIN_ERROR_STR, loginDto.getUsername());
        if (stringRedisTemplate.hasKey(keyStr)) {
            int errorNum = Integer.valueOf(stringRedisTemplate.opsForValue().get(keyStr));
            if (errorNum == 5) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("登录失败五次，您的账号将被锁定十分钟!");
            }
        }
        //校验用户名密码
        PanUser user = panUserService.findByUsername(loginDto.getUsername());
        if (Objects.isNull(user)) {
            loginErrorRecord(loginDto.getUsername());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("用户名或密码错误!");
        }
        if (!user.getPassword().equals(PasswordKit.encrypt(Base64Kit.decode(loginDto.getPassword())))) {
            loginErrorRecord(loginDto.getUsername());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("用户名或密码错误！");
        }
        return ResponseEntity.ok(panUserService.login(loginDto));
    }

    @ApiOperation(value = "注销登录")
    @PostMapping("/logout")
    public ResponseEntity logoutMQ(HttpServletRequest request) {
        return ResponseEntity.ok(panUserService.logout(TokenKit.getUserId(request)));
    }

    @ApiOperation("获取用户")
    @GetMapping("/get/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        return ResponseEntity.ok(ConvertKit.convert(panUserService.findById(id), PanUserVo.class));
    }

    @ApiOperation("更新")
    @PutMapping("/update")
    public ResponseEntity updateMQ(@RequestBody @Valid PanUserUpdateDto updateDto) {
        return ResponseEntity.ok(panUserService.update(updateDto));
    }

    @ApiOperation("删除用户")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        return ResponseEntity.ok(panUserService.delete(id));
    }

    @ApiOperation("分页查询")
    @GetMapping("/page")
    public ResponseEntity findPage(PanUserQueryDto queryDto, @PageableDefault Pageable page) {
        return ResponseEntity.ok(panUserService.findPage(queryDto, page).map(panUser -> ConvertKit.convert(panUser, PanUserVo.class)));
    }

    @ApiOperation("分页查询")
    @GetMapping("/test")
    public ResponseEntity test() {
        panUserService.test();
        return ResponseEntity.ok().build();
    }

    @ApiOperation("重置密码")
    @PostMapping("/reset/password")
    public ResponseEntity resetPassword(@RequestBody @Valid ResetPasswordDto resetDto) {
        return ResponseEntity.ok(panUserService.resetPassword(resetDto));
    }

    /**
     * excel导入-单个sheet
     *
     * @param multipartFile 文件流
     * @return
     * @throws Exception
     */
    @PostMapping("/excelImport/user")
    @ApiOperation(value = "excel导入用户")
    public ResponseEntity<Object> excelImportUser(@RequestParam PanUser.Type type, @RequestParam("file") MultipartFile multipartFile) {
        panUserService.excelImport(type, multipartFile);
        return ResponseEntity.ok().build();
    }

    private void loginErrorRecord(String username) {
        String keyStr = String.format(RedisConstant.LOGIN_ERROR_STR, username);
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(keyStr))) {
            int errorNum = Integer.valueOf(stringRedisTemplate.opsForValue().get(keyStr));
            stringRedisTemplate.opsForValue().set(keyStr, String.valueOf(errorNum + 1), 10, TimeUnit.MINUTES);
        } else {
            stringRedisTemplate.opsForValue().set(keyStr, String.valueOf(1), 10, TimeUnit.MINUTES);
        }
    }

    private String checkPwdRegex(String pwd) {
        Pattern pattern = Pattern.compile(SystemConst.PWD_REG);
        Matcher matcher = pattern.matcher(pwd);
        return (!matcher.matches()) ? SystemConst.PWD_REG_WARNING : null;
    }
}
