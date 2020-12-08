package com.junlan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.junlan.model.entity.SysRolePermission;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @Author LJ
 * @Date 2020/12/3
 * msg
 */
public interface SysRolePermissionService extends IService<SysRolePermission> {

    /**
     * 根据角色id获取权限id列表
     *
     * @param roleId
     * @return
     * @throws Exception
     */
    List<Long> getPermissionIdsByRoleId(Long roleId);

    /**
     * 根据角色id获取可用的权限编码
     *
     * @param roleId
     * @return
     * @throws Exception
     */
    Set<String> getPermissionCodesByRoleId(Long roleId);

}
