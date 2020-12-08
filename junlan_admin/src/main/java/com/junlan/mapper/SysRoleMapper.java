package com.junlan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.junlan.model.entity.SysRole;
import org.springframework.stereotype.Repository;

/**
 * @Author LJ
 * @Date 2020/12/3
 * msg Base-->mybatis-plus
 */

@Repository // DAO层
public interface SysRoleMapper extends BaseMapper<SysRole> {

}
