package com.junlan.service.serviceImp;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.junlan.mapper.SysPermissionMapper;
import com.junlan.mapper.SysRolePermissionMapper;
import com.junlan.model.entity.SysRolePermission;
import com.junlan.service.SysRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @Author LJ
 * @Date 2020/12/3
 * msg
 */

@Service
public class SysRolePermissionServiceImp extends ServiceImpl<SysRolePermissionMapper, SysRolePermission>
        implements SysRolePermissionService {

    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;

    @Override
    public List<Long> getPermissionIdsByRoleId(Long roleId) {
         Wrapper wrapper = lambdaQuery()
                .select(SysRolePermission::getId)
                .eq(SysRolePermission::getRoleId, roleId)
                .getWrapper();
         return sysRolePermissionMapper.selectObjs(wrapper);
    }

    @Override
    public Set<String> getPermissionCodesByRoleId(Long roleId) {
        return sysPermissionMapper.getPermissionCodesByRoleId(roleId);
    }
}
