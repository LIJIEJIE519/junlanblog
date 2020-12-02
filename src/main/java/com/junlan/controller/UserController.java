package com.junlan.controller;

import com.junlan.common.result.ApiResult;
import com.junlan.mapper.user.SysUserMapper;
import com.junlan.model.entity.SysUser;
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
    private SysUserMapper sysUserMapper;

    @ApiOperation(value = "获取所有用户")
    @GetMapping("/getAll")
    public ApiResult<List<SysUser>> getAll() {
        List<SysUser> sysUsers = sysUserMapper.selectList(null);
        return ApiResult.ok(sysUsers);
    }
}
