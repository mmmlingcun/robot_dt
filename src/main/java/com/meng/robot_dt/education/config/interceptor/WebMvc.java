package com.meng.robot_dt.education.config.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器管理类
 *
 * @author taorun
 * @date 2023/05/25
 **/
@Configuration
public class WebMvc implements WebMvcConfigurer {

    @Autowired
    private Interceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        /**
         * 以下拦截器可以复制多个同时使用
         */
        registry.addInterceptor(interceptor)
                .addPathPatterns(        // 需要拦截的 url
                        new String[]{"/**"})
                .excludePathPatterns(    // 排除拦截的 url
                        new String[]{
                                "/swagger-resources/**", "/swagger-ui.html", "/v2/api-docs", "/webjars/**",
                                "/error",
                                "/user/login",
                                "/user/register",
                                "/user/reset/password",
                                "/user/check-unique",
                                "/video/course/images/**",
                                "/video/course/accessFile",
                                "/video/course/images/**",
                                "/video/course/playList/**",
                                "/video/course/key/**",
                                "/video/course/stream/**",
                                "/license/**",
                                "/password/**",
                                "/user/reset/password/apply",
                                "/user/get/apply"
                        });

    }

}
