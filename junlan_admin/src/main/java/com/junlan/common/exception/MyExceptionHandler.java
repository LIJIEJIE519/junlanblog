package com.junlan.common.exception;

import com.alibaba.fastjson.JSON;
import com.junlan.common.result.ApiCode;
import com.junlan.common.result.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * @Author LJ
 * @Date 2020/12/2
 * msg
 */
@Slf4j
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

    /**
     * shiro 权限授权异常处理
     * @param exception
     * @return
     */
    @ExceptionHandler(value = AuthorizationException.class)
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<String> authorizationExceptionHandler(AuthorizationException exception) {
        return ApiResult.fail(ApiCode.UNAUTHORIZED_EXCEPTION, exception.getMessage());
    }


    /**
     * Validator非法参数验证异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public ApiResult<List<String>> handleMethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<String> list = new ArrayList<>();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            list.add(fieldError.getDefaultMessage());
        }
        Collections.sort(list);
        log.error(ApiCode.PARAMETER_EXCEPTION.getCode() + ":" + JSON.toJSONString(list));
        return ApiResult.fail(ApiCode.PARAMETER_EXCEPTION, list);
    }

    /**
     * 自定义异常
     * @param exception
     * @return
     */
    @ExceptionHandler(value = BaseException.class)
    @ResponseStatus(HttpStatus.OK)      //  标记方法或异常类，用应返回的状态
    public ApiResult<String> baseExceptionHandler(BaseException exception) {
        return ApiResult.fail(ApiCode.PARAMETER_EXCEPTION, exception.getMessage());
    }




    /**
     * 默认的异常处理
     *
     * @param exception
     * @return
     */
//    @ExceptionHandler(value = Exception.class)
//    @ResponseStatus(HttpStatus.OK)
    public ApiResult<String> exceptionHandler(Exception exception) {
        return ApiResult.fail(ApiCode.SYSTEM_EXCEPTION, exception.getMessage());
    }
}
