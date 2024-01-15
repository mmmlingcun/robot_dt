package com.meng.robot_dt.education.service.impl;

import com.google.common.collect.Lists;
import com.meng.robot_dt.education.common.exception.BusinessException;
import com.meng.robot_dt.education.common.exception.NoEntityFoundException;
import com.meng.robot_dt.education.controller.dto.*;
import com.meng.robot_dt.education.controller.vo.PanUserVo;
import com.meng.robot_dt.education.controller.vo.UserExcelVo;
import com.meng.robot_dt.education.entity.PanUser;
import com.meng.robot_dt.education.handler.ExcelHandler;
import com.meng.robot_dt.education.repository.PanUserRepository;
import com.meng.robot_dt.education.service.PanUserService;
import com.meng.robot_dt.education.util.kit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.toList;

/**
 * @author taorun
 * @date 2023/1/16 11:17
 */
@Service
public class PanUserServiceImpl implements PanUserService {

    @Resource
    private PanUserRepository panUserRepository;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ExcelHandler excelHandler;

    @Override
    public PanUserVo register(PanUserAddDto addDto) {
        if (Objects.nonNull(findByUsername(addDto.getUsername()))) {
            throw new BusinessException("用户名已存在");
        }
        if (Objects.nonNull(findByPhone(addDto.getPhone()))) {
            throw new BusinessException("该手机号已注册");
        }
        addDto.setPassword(PasswordKit.encrypt(addDto.getPassword()));
        PanUser user = ConvertKit.convert(addDto, PanUser.class);
        user.setIsAdmin(false);
        return ConvertKit.convert(panUserRepository.save(user), PanUserVo.class);
    }

    @Transactional
    @Override
    public PanUserVo login(PanUserLoginDto loginDto) {
        String keyStr = String.format(RedisConstant.LOGIN_ERROR_STR, loginDto.getUsername());
        PanUser user = findByUsername(loginDto.getUsername());
        // 存储 token
        PanUserVo userVo = ConvertKit.convert(user, PanUserVo.class);
        userVo.setToken(Base64Kit.encode(user.getId() + "." + user.getUsername() + "." + UUID.randomUUID()));
        // token有效时长：30分钟
        stringRedisTemplate.opsForValue().set(RedisConstant.TOKEN + user.getId(), userVo.getToken(), 30, TimeUnit.MINUTES);
        stringRedisTemplate.delete(keyStr);
        return userVo;
    }

    @Override
    public Boolean logout(Long userId) {
        return stringRedisTemplate.delete(RedisConstant.TOKEN + userId);
    }

    @Override
    public PanUser findById(Long id) {
        return panUserRepository.findById(id).orElseThrow(() -> new NoEntityFoundException("No user find by id = " + id));
    }

    @Override
    public PanUser findByPhone(String phone) {
        return panUserRepository.findByPhone(phone).orElse(null);
    }

    @Override
    public PanUser findByUsername(String username) {
        return panUserRepository.findByUsername(username).orElse(null);
    }

    @Override
    @Transactional
    public boolean update(PanUserUpdateDto updateDto) {
        if (StringKit.isNotBlank(updateDto.getPhone())) {
            PanUser userByPhone = findByPhone(updateDto.getPhone());
            if (Objects.nonNull(userByPhone) && userByPhone.getId() != updateDto.getId()) {
                throw new BusinessException("该手机号已存在");
            }
        }
        PanUser user = findById(updateDto.getId());
        BeanKit.copyPropertiesIgnoreNull(updateDto, user);
        return true;
    }

    @Transactional
    @Override
    public boolean delete(Long id) {
        PanUser user = findById(id);
        user.setDeleted(true);
        return true;
    }

    @Override
    public PanUserVo resetPassword(ResetPasswordDto resetDto) {
        PanUser user = findByPhone(resetDto.getPhone());
        if (Objects.isNull(user)) {
            throw new BusinessException("手机号未注册");
        }
        if (!PasswordKit.encrypt(resetDto.getPastPassword()).equals(user.getPassword())) {
            throw new BusinessException("原密码错误");
        }
        user.setPassword(PasswordKit.encrypt(resetDto.getNewPassword()));
        return ConvertKit.convert(panUserRepository.save(user), PanUserVo.class);
    }

    @Override
    public Page<PanUser> findPage(PanUserQueryDto queryDto, Pageable page) {
        return panUserRepository.findAll(getSpecification(queryDto), page);
    }

    @Override
    public void test() {
        List<PanUser> users = panUserRepository.findAll();
        AtomicInteger i = new AtomicInteger(1000000);
        users.forEach(item -> {
            item.setUserCode(String.valueOf(i.get() + 1));
            item.setUserStudentId(String.valueOf(i.get() + 1));
            i.getAndIncrement();
        });
        panUserRepository.saveAll(users);
    }

    @Override
    public boolean checkUnique(PanUserCheckDto checkDto) {
        if (StringKit.isNotBlank(checkDto.getPhone()) && Objects.nonNull(findByPhone(checkDto.getPhone()))) {
            return false;
        }
        if (StringKit.isNotBlank(checkDto.getUsername()) && Objects.nonNull(findByUsername(checkDto.getUsername()))) {
            return false;
        }
        return true;
    }

    @Override
    public void excelImport(MultipartFile multipartFile) {
        try {
            List<PanUser> users = excelHandler.importExcel(multipartFile, UserExcelVo.class, null, null).stream().map(UserExcelVo::getPanUser).collect(toList());
            panUserRepository.saveAll(users);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Specification<PanUser> getSpecification(PanUserQueryDto queryDto) {
        return (root, query, builder) -> {
            List<Predicate> predicates = Lists.newArrayList();
            predicates.add(builder.isFalse(root.get("isDeleted")));
            if (StringKit.isNotBlank(queryDto.getUsername())) {
                predicates.add(builder.like(root.get("username"), "%".concat(queryDto.getUsername()).concat("%")));
            }
            if (StringKit.isNotBlank(queryDto.getPhone())) {
                predicates.add(builder.like(root.get("phone"), "%".concat(queryDto.getPhone()).concat("%")));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
