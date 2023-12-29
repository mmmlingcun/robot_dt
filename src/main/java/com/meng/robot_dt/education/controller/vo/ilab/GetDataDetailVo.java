package com.meng.robot_dt.education.controller.vo.ilab;

import com.meng.robot_dt.education.controller.dto.ilab.DataUploadStepsDto;
import lombok.Data;

import java.util.List;

/**
 * @author ：cola
 * @description：
 * @date ：2023/12/28 15:41
 */
@Data
public class GetDataDetailVo {

    private String username;
    private Integer status;
    private String groupId;
    private String title;
    private String extData;
    private String roleInGroup;
    private Integer timeUsed;
    private String projectTitle;
    private String groupName;
    private Integer score;
    private String attachment;
    private Long startTime;
    private Long appId;
    private Long endTime;
    private String originId;
    private List<DataUploadStepsDto> steps;
}
