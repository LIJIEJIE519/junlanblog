package com.junlan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.junlan.model.entity.SysUser;

/**
 * @Author LJ
 * @Date 2020/12/7
 * msg
 */

public interface SysUserService extends IService<SysUser> {
    /**
     * 保存
     *
     * @param sysUser
     * @return
     * @throws Exception
     */
    default boolean saveSysUser(SysUser sysUser) throws Exception {
        return save(sysUser);
    }

    /**
     * 修改
     *
     * @param sysUser
     * @return
     * @throws Exception
     */
    boolean updateSysUser(SysUser sysUser) throws Exception;

    /**
     * 删除
     *
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteSysUser(Long id) throws Exception;


    /**
     * 判断用户名是否存在
     *
     * @param username
     * @return
     * @throws Exception
     */
    boolean isExistsByUsername(String username) throws Exception;
}
