package com.junlan.service.serviceImp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.junlan.common.exception.BaseException;
import com.junlan.common.exception.MethodArgumentException;
import com.junlan.mapper.SysUserMapper;
import com.junlan.model.entity.SysUser;
import com.junlan.service.SysUserService;
import com.junlan.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author LJ
 * @Date 2020/12/7
 * msg
 */

@Slf4j
@Service
public class SysUserServiceImp extends ServiceImpl<SysUserMapper, SysUser>
        implements SysUserService {

    private static final int SaltNum = 16;
    private static final String DefaultPassword = "111111";

    @Autowired
    private SysUserMapper sysUserMapper;

    // 事务处理
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveSysUser(SysUser sysUser) throws Exception {
        if (StringUtils.isBlank(sysUser.getUsername())) {
            throw new MethodArgumentException("username不能为空");
        }
        if (isExistsByUsername(sysUser.getUsername())) {
            throw new BaseException("该用户已经存在，请登录");
        }
        // 生成秘药
        String salt = UserUtil.RandomString(SaltNum);
        String password = sysUser.getPassword();
        // 如果密码为空，则设置默认密码
        if (StringUtils.isBlank(password)) {
            password = DefaultPassword;
        }
        // 密码加密
        sysUser.setPassword(UserUtil.encrypt(password, salt));
        sysUser.setSalt(salt);

        return super.save(sysUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateSysUser(SysUser sysUser) throws Exception {
        SysUser user = getById(sysUser.getId());
        if (user == null) {
            throw new BaseException("用户不存在");
        }

        // 更新用户
        user.setUsername(sysUser.getUsername())
                .setUsernick(sysUser.getUsernick())
                .setRoleId(sysUser.getRoleId());
        return super.updateById(user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteSysUser(Long id) throws Exception {
        return super.removeById(id);
    }

    @Override
    public boolean isExistsByUsername(String username) throws Exception {
        return sysUserMapper.selectCount(new QueryWrapper<SysUser>()
                .lambda().eq(SysUser::getUsername, username)) > 0;
    }
}
