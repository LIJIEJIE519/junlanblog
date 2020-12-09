package com.junlan.common.exception;

import com.junlan.common.result.ApiCode;

/**
 * @Author LJ
 * @Date 2020/12/9
 * msg  方法参数异常
 */

public class MethodArgumentException extends BaseException{

    public MethodArgumentException(String message) {
        super(message);
    }

    public MethodArgumentException(ApiCode apiCode) {
        super(apiCode);
    }
}
