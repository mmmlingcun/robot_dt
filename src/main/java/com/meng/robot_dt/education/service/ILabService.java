package com.meng.robot_dt.education.service;

import com.meng.robot_dt.education.common.result.ILableResult;
import com.meng.robot_dt.education.controller.dto.ilab.DataUploadDto;
import com.meng.robot_dt.education.controller.dto.ilab.GetDataDto;
import com.meng.robot_dt.education.controller.vo.ilab.AccessTokenVo;
import com.meng.robot_dt.education.controller.vo.ilab.GetDataVo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author ：cola
 * @description：
 * @date ：2023/12/28 14:30
 */
public interface ILabService {

    /**
     * 获取用户信息和access_token
     *
     * @param ticket
     * @return
     */
    AccessTokenVo getAccessToken(String ticket);

    /**
     * 实验结果数据回传接口
     *
     * @param dto
     * @return
     */
    ILableResult dataUpload(DataUploadDto dto, String accessToken);

    /**
     * 获取实验结果接口
     *
     * @param dto
     * @return
     */
    GetDataVo getData(GetDataDto dto);

    /**
     * 调用此接口获取的access_token，不支持再次通过此接口获取新的access_token，原access_token最多可有2次机会获取新的access_token。
     *
     * @param token
     * @return
     */
    AccessTokenVo getRefreshToken(String token);

    default String tokenEncode(String accessToken) {
        try {
            return URLEncoder.encode(accessToken, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
