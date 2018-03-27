package com.myuan.login.service;

import com.myuan.login.dao.AuthDao;
import com.myuan.login.dao.RoleAuthDao;
import com.myuan.login.entity.MyAuthority;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 * @author liuwei
 * @date 2018/1/28 8:56
 * 权限业务层
 */
@Service
public class AuthService {

    @Autowired
    private AuthDao authDao;
    @Autowired
    private RoleAuthDao roleAuthDao;

    /**
     * 获取劫色权限 <liuwei> [2018/1/28 9:51]
     */
    @Transactional(readOnly = true)
    public List<MyAuthority> findAuthByRoleId(Long roleId) {
        List<Long> idList = roleAuthDao.findAuthIdByRoleId(roleId);
        return authDao.findMyAuthoritiesByIdIn(idList);
    }
}
