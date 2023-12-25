package com.meng.robot_dt.education.config.interceptor;

import com.meng.robot_dt.education.common.constant.ErrorCode;
import com.meng.robot_dt.education.common.constant.RedisConstant;
import com.meng.robot_dt.education.common.constant.SystemConst;
import com.meng.robot_dt.education.common.exception.BusinessException;
import com.meng.robot_dt.education.common.result.ResultEnum;
import com.meng.robot_dt.education.util.ServletUtils;
import com.meng.robot_dt.education.util.kit.Base64Kit;
import com.meng.robot_dt.education.util.kit.StringKit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求拦截器
 *
 * @author taorun
 * @date 2023/05/25
 **/
@Component
public class Interceptor implements AsyncHandlerInterceptor {

    @Value("${interceptor.enable}")
    private Boolean interceptorEnable;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 返回 true 继续向下执行，返回 false 取消当前请求
     **/
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!interceptorEnable || !(handler instanceof HandlerMethod)) {
            return true;
        }
        try {
            String token = request.getHeader(SystemConst.AUTHORIZATION);
            if (StringKit.isBlank(token) || !token.startsWith("Bearer ")) {
                throw new BusinessException(ResultEnum.NO_LOGIN);
            }
            // 针对此 token 特殊处理，直接放行，用于展厅展示
            if ("Bearer Mi5hZG1pbi40MGJiYTcyYS1lNDQ5LTRlNzktOWM3Yi04YzdmNmEwNGJkZWU=".equals(token)) {
                return true;
            }
            String userId = Base64Kit.decode(token.substring(7)).split("\\.")[0];
            if (token.substring(7).equals(stringRedisTemplate.opsForValue().get(RedisConstant.TOKEN + userId))) {
                return true;
            }
        } catch (Exception e) {
            ServletUtils.renderString(
                    response,
                    ErrorCode.NOT_AUTHORIZED
            );
        }
        ServletUtils.renderString(
                response,
                ErrorCode.NOT_AUTHORIZED
        );
        return false;
    }

}
