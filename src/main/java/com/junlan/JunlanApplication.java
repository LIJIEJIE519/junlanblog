package com.junlan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2     // Swagger支持
@MapperScan("com.junlan.mapper")    // mybatis-plus
//@ImportResource(locations = {"classpath:mapper/SysRolePermissionMapper.xml"})  // 导入xml资源
public class JunlanApplication {

    public static void main(String[] args) {
        SpringApplication.run(JunlanApplication.class, args);
    }
}
