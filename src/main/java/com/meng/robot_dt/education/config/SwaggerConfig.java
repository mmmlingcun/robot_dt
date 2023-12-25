package com.meng.robot_dt.education.config;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.domain.Pageable;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.AlternateTypeRuleConvention;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

import static springfox.documentation.schema.AlternateTypeRules.newRule;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String AUTHORIZATION = "Authorization";

    @Bean
    public Docket commonDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("通用API接口文档")
                .apiInfo(apiInfo())
                .pathMapping("/")
                .select()
                // 指向自己的controller即可
                .apis(RequestHandlerSelectors.basePackage("com.huibo"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    /**
     * 设置文档信息
     *
     * @return ApiInfo
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("接口地址")
                //设置作者联系方式,可有可无
                .contact(new Contact("huibo", "www.huiborobot.com", "orion@huiborobot.com"))
                //版本号
                .version("1.0.0")
                //描述
                .description("API 描述")
                .build();

    }

    /**
     * add authorization field to request header
     *
     * @return list of ApiKey
     */
    private List<ApiKey> securitySchemes() {
        return Lists.newArrayList(new ApiKey(AUTHORIZATION, AUTHORIZATION, "header"));
    }

    private List<SecurityContext> securityContexts() {
        return Lists.newArrayList(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.any())
                        .build()
        );
    }

    /**
     * create default auth, set authorization scope as global
     *
     * @return list of SecurityReference
     */
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = {authorizationScope};
        return Lists.newArrayList(new SecurityReference(AUTHORIZATION, authorizationScopes));
    }


    @Bean
    public AlternateTypeRuleConvention pageableConvention(final TypeResolver resolver) {
        return new AlternateTypeRuleConvention() {
            @Override
            public int getOrder() {
                return Ordered.LOWEST_PRECEDENCE;
            }

            @Override
            public List<AlternateTypeRule> rules() {
                List<AlternateTypeRule> list = new ArrayList<>();
                AlternateTypeRule alternateTypeRule = newRule(resolver.resolve(Pageable.class), resolver.resolve(PageData.class));
                list.add(alternateTypeRule);
                return list;
            }
        };
    }

    @ApiModel
    @Data
    static class PageData {
        @ApiModelProperty("第page页,从0开始计数")
        private Integer page;

        @ApiModelProperty("每页数据数量")
        private Integer size;

        @ApiModelProperty("按属性排序,格式:属性(,asc|desc)")
        private List<String> sort;
    }
}
