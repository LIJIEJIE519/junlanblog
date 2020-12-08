package com.junlan.common.exception;

import com.junlan.common.result.ApiCode;
import lombok.Data;

/**
 * @Author LJ
 * @Date 2020/12/7
 * msg 自定义异常基类
 */

@Data
public class BaseException extends RuntimeException{

    private static final long serialVersionUID = 2448593440017466071L;
    // 异常码
    private Integer errCode;

    // 异常信息
    private String message;

    public BaseException(){}

    public BaseException(String message) {
        this.message = message;
    }

    public BaseException(Integer errCode, String message) {
        this.errCode = errCode;
        this.message = message;
    }

    public BaseException(ApiCode apiCode) {
        this.errCode = apiCode.getCode();
        this.message = apiCode.getMessage();
    }

    public BaseException(Throwable cause, Integer errCode, String message) {
        super(cause);
        this.errCode = errCode;
        this.message = message;
    }
}
