package com.junlan.controller;

import com.junlan.common.result.ApiResult;
import com.junlan.common.validator.group.Add;
import com.junlan.model.entity.SysUser;
import com.junlan.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.groups.Default;
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

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation(value = "获取所有用户")
    @GetMapping("/getAll")
    @RequiresRoles("admin")
    public ApiResult<List<SysUser>> getAll() {
        List<SysUser> sysUsers = sysUserService.list(null);
        return ApiResult.ok(sysUsers);
    }

    @ApiOperation(value = "添加用户", notes = "添加系统用户")
    @PostMapping("/add")
    @RequiresPermissions("user:add")
    public ApiResult<Boolean> addUser(@Validated(Add.class) @RequestBody SysUser sysUser) throws Exception{
        boolean res = sysUserService.saveSysUser(sysUser);
        return ApiResult.result(res);
    }
}
