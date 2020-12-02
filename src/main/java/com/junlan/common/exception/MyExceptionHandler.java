package com.junlan.common.exception;

import com.junlan.common.result.ApiCode;
import com.junlan.common.result.ApiResult;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @Author LJ
 * @Date 2020/12/2
 * msg
 */

@RestControllerAdvice       // 异常处理类
public class MyExceptionHandler {


    /**
     * shiro 登录授权异常处理
     * @param exception
     * @return
     */
    @ExceptionHandler(value = AuthenticationException.class)
    @ResponseStatus(HttpStatus.OK)      //  标记方法或异常类，用应返回的状态
    public ApiResult<String> authenticationExceptionHandler(AuthenticationException exception) {

        return ApiResult.fail(ApiCode.AUTHENTICATION_EXCEPTION, exception.getMessage());
    }
}
