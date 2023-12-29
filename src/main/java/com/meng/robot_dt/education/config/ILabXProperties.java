package com.meng.robot_dt.education.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author ：cola
 * @description： 国家虚拟仿真实验教学课程共享平台接口
 * @date ：2023/12/28 11:51
 */
@Component
@ConfigurationProperties(prefix = "i-lab.config")
public class ILabXProperties {

    private String url;

    private String appId;

    private String secret;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
