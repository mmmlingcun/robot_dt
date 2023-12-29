package com.meng.robot_dt.education.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.meng.robot_dt.education.common.result.ILableResult;
import com.meng.robot_dt.education.config.ILabXProperties;
import com.meng.robot_dt.education.controller.dto.ilab.DataUploadDto;
import com.meng.robot_dt.education.controller.dto.ilab.GetDataDto;
import com.meng.robot_dt.education.controller.vo.ilab.AccessTokenVo;
import com.meng.robot_dt.education.controller.vo.ilab.GetDataVo;
import com.meng.robot_dt.education.service.ILabService;
import com.meng.robot_dt.education.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import static com.meng.robot_dt.education.common.constant.ILabXConstant.*;

/**
 * @author cola
 * @date 2023/1/16 11:17
 */
@Slf4j
@Service
public class ILabServiceImpl implements ILabService {

    @Autowired
    private ILabXProperties iLabXProperties;

    @Override
    public AccessTokenVo getAccessToken(String ticket) {
        AccessTokenVo accessTokenVo = new AccessTokenVo();
        try {
            //获取平台的回调ticket并且加密获取signature
            String signature = SecureUtil.md5(URLDecoder.decode(ticket, "UTF-8") + iLabXProperties.getAppId() + iLabXProperties.getSecret()).toUpperCase();
            //通过signature获取accessToken
            String response = HttpUtils.sendGetRequest(String.format(GET_ACCESS_TOKEN, iLabXProperties.getUrl(), ticket, iLabXProperties.getAppId(), signature));
            accessTokenVo = JSON.parseObject(response, AccessTokenVo.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return accessTokenVo;
    }

    @Override
    public ILableResult dataUpload(DataUploadDto dto, String accessToken) {
        String tokenEncode = tokenEncode(accessToken);
        String response = HttpUtils.sendPostRequest(String.format(POST_DATA_UPLOAD, iLabXProperties.getUrl(), tokenEncode), JSON.toJSONString(dto));
        return JSON.parseObject(response, ILableResult.class);
    }

    @Override
    public GetDataVo getData(GetDataDto dto) {
        String tokenEncode = tokenEncode(dto.getAccessToken());
        String signature = SecureUtil.md5(dto.getAccessToken() + iLabXProperties.getAppId() + iLabXProperties.getSecret()).toUpperCase();
        String response = HttpUtils.sendGetRequest(getDataUrl(dto, tokenEncode, signature));
        return JSON.parseObject(response, GetDataVo.class);
    }

    @Override
    public AccessTokenVo getRefreshToken(String token) {
        String tokenEncode = tokenEncode(token);
        String signature = SecureUtil.md5(token + iLabXProperties.getAppId() + iLabXProperties.getSecret()).toUpperCase();
        String response = HttpUtils.sendGetRequest(String.format(GET_REFRESH_TOKEN, iLabXProperties.getUrl(), tokenEncode, iLabXProperties.getAppId(), signature));
        return JSON.parseObject(response, AccessTokenVo.class);
    }

    private String getDataUrl(GetDataDto dto, String token, String signature) {
        String url = String.format(GET_DATA_GET, iLabXProperties.getUrl(), token, iLabXProperties.getAppId(), signature);
        if (dto.getStart() != null && dto.getStart() != 0 && dto.getStart() > 0) {
            url = url + "&start=" + dto.getStart();
        }
        if (!ObjectUtils.isEmpty(dto.getGroupId())) {
            url = url + "&group_id=" + dto.getGroupId();
        }
        if (!ObjectUtils.isEmpty(dto.getOriginId())) {
            url = url + "&originId=" + dto.getOriginId();
        }
        if (!ObjectUtils.isEmpty(dto.getUsername())) {
            url = url + "&username=" + dto.getUsername();
        }
        return url;
    }
}
