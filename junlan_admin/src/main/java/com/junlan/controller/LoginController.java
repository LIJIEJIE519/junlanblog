package com.junlan.controller;

import com.junlan.common.result.ApiResult;
import com.junlan.model.vo.LoginSysUserTokenVO;
import com.junlan.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * @Author LJ
 * @Date 2020/11/26
 * msg
 */
@Api("登录类")
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @ApiOperation(value = "用户登录", notes = "")
    @PostMapping("/login")
    public ApiResult<LoginSysUserTokenVO> login(@RequestParam("username") String username,
                           @RequestParam("password") String password) throws Exception{
        LoginSysUserTokenVO login = loginService.login(username, password);
        return ApiResult.ok(login, "登录成功");
    }

    @ApiOperation(value = "用户登出")
    @PostMapping("/logout")
    public ApiResult<String> logout(HttpServletRequest request) {
        loginService.logout(request);
        return ApiResult.ok("登出成功");
    }

}
