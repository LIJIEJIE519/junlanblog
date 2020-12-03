package com.junlan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.junlan.model.entity.SysPermission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @Author LJ
 * @Date 2020/12/3
 * msg  Base-->mybatis-plus
 */

@Repository
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    /**
     * 根据角色id获取权限编码
     *
     * @param roleId
     * @return
     */
    Set<String> getPermissionCodesByRoleId(@Param("roleId") Long roleId);
}
