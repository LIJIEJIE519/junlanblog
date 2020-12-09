package com.junlan.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.junlan.common.result.ApiResult;
import com.junlan.common.validator.group.Add;
import com.junlan.common.validator.group.Update;
import com.junlan.model.entity.SysUser;
import com.junlan.model.vo.LoginSysUserVO;
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
public class SysUserController {

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

    @ApiOperation(value = "更新用户", notes = "更新系统用户")
    @PostMapping("/update")
    @RequiresPermissions("user:update")
    public ApiResult<Boolean> updateUser(@Validated(Update.class) @RequestBody SysUser sysUser) throws Exception{
        boolean res = sysUserService.updateSysUser(sysUser);
        return ApiResult.result(res);
    }

    @ApiOperation(value = "删除用户", notes = "删除系统用户")
    @PostMapping("/delete/{id}")
    @RequiresPermissions("user:delete")
    public ApiResult<Boolean> deleteUser(@PathVariable Long id) throws Exception{
        boolean res = sysUserService.deleteSysUser(id);
        return ApiResult.result(res);
    }

    @ApiOperation(value = "根据username查找用户")
    @PostMapping("/findByUsername")
    @RequiresPermissions("user:list")
    public ApiResult<String> findByUsernames(@RequestParam("username") String username) throws Exception{
        return ApiResult.ok("", "");
    }



}
