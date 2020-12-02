package com.junlan.mapper.user;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Author LJ
 * @Date 2020/11/28
 * msg
 */

@Mapper
public interface LoginMapper {

    @Select("SELECT password\n" +
            "        FROM user\n" +
            "        WHERE username = #{username}")
    String getPassword(String username);


    String getRoleCode(String username);
}
