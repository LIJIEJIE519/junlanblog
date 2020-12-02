package com.junlan.controller;

import com.junlan.common.result.ApiResult;
import com.junlan.mapper.user.UserMapper;
import com.junlan.model.user.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author LJ
 * @Date 2020/11/26
 * msg
 */
@Api("用户类")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired(required = false)
    private UserMapper userMapper;

    @ApiOperation(value = "获取所有用户")
    @GetMapping("/getAll")
    public ApiResult<List<User>> getAll() {
        List<User> users = userMapper.selectList(null);
        return ApiResult.ok(users);
    }
}
