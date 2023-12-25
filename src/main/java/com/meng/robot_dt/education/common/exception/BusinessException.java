package com.meng.robot_dt.education.common.exception;


import com.meng.robot_dt.education.common.result.ResultEnum;

/**
 * 业务异常
 *
 * @Author: TR
 */
public class BusinessException extends RuntimeException {

    private Integer code;

    public BusinessException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public BusinessException(ResultEnum resultEnum, Exception exception) {
        super(resultEnum.getMsg() + " : " + exception.getMessage());
        this.code = resultEnum.getCode();
    }

    public BusinessException(String string) {
        super(string);
        this.code = ResultEnum.FAIL.getCode();
    }

    public BusinessException(Integer code, String string) {
        super(string);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}
