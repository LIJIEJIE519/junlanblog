package com.junlan.service.serviceImp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.junlan.mapper.SysRoleMapper;
import com.junlan.model.entity.SysRole;
import com.junlan.service.SysRoleService;
import org.springframework.stereotype.Service;

/**
 * @Author LJ
 * @Date 2020/12/3
 * msg   mybatis-plus -- > ServiceImpl
 */

@Service
public class SysRoleServiceImp extends ServiceImpl<SysRoleMapper, SysRole>
        implements SysRoleService {


}
