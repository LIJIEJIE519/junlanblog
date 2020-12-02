package com.junlan.controller;

import com.junlan.common.result.ApiResult;
import com.junlan.mapper.user.LoginMapper;
import com.junlan.shiro.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @Author LJ
 * @Date 2020/11/26
 * msg
 */
@Api("登录类")
@RestController
public class LoginController {

    @Autowired(required = false)
    private LoginMapper loginMapper;

    @ApiOperation(value = "用户登录", notes = "")
    @PostMapping("/login")
    public ApiResult<String> login(@RequestParam("username") String username,
                           @RequestParam("password") String password) {

        String realPassword = loginMapper.getPassword(username);
        String jwtToken = JwtUtil.createToken(username);

        return ApiResult.ok(jwtToken, "登录成功");
    }

}
