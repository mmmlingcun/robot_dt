package com.meng.robot_dt.education.util.kit;

import com.meng.robot_dt.education.common.constant.JwtConstants;
import com.meng.robot_dt.education.common.exception.BusinessException;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @Author: TR
 * @Date: 2023/5/26
 */
public class TokenKit {

    public static Long getUserId(String token) {
        return Long.valueOf(Base64Kit.decode(token).split("\\.")[0]);
    }

    public static String getUsername(String token) {
        return Base64Kit.decode(token).split("\\.")[1];
    }

    public static Long getUserId(HttpServletRequest request) {
        String token = getTokenFromHeader(request);
        if (Objects.nonNull(token)) {
            return getUserId(token);
        } else throw new BusinessException("token 为空");
    }

    public static String getUsername(HttpServletRequest request) {
        String token = getTokenFromHeader(request);
        if (Objects.nonNull(token)) {
            return getUsername(token);
        } else throw new BusinessException("token 为空");
    }

    public static String getTokenFromHeader(HttpServletRequest request) {
        String tokenValue = null;
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(authorization) && authorization.startsWith(JwtConstants.JWT_PREFIX)) {
            tokenValue = authorization.substring(7);
        }
        return tokenValue;
    }

}
