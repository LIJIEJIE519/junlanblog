package com.junlan.controller;

import com.junlan.common.result.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author LJ
 * @Date 2020/11/26
 * msg
 */
@Api("测试")
@RequestMapping("/test")
@RestController
public class TestController {
    @ApiOperation(value = "测试测试", notes = "测试测试---详细信息")
    @GetMapping("/hello")
    public ApiResult<String> hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return ApiResult.ok(String.format("hello %s", name));
    }
}
