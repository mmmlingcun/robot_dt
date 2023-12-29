package com.meng.robot_dt.education.controller.vo.ilab;

import lombok.Data;

import java.util.List;

/**
 * @author ：cola
 * @description：
 * @date ：2023/12/28 15:41
 */
@Data
public class GetDataVo {

    private Integer start;
    private Integer total;
    private Integer size;
    private Integer code;
    private String msg;
    private List<GetDataDetailVo> data;
}
