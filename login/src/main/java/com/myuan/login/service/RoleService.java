package com.myuan.login.service;


import com.myuan.login.dao.RoleDao;
import com.myuan.login.dao.UserRoleDao;
import com.myuan.login.entity.MyRole;
import com.myuan.login.entity.MyUserRole;
import java.util.List;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 * @author liuwei
 * @date 2018/1/27 14:41
 * 角色业务层
 */
@Service
@Log4j
@Transactional(readOnly = true)
public class RoleService {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private UserRoleDao userRoleDao;

    /**
     * 获取用户角色 <liuwei> [2018/1/28 9:04]
     */
    public List<MyRole> findRoleByUserId(Long userId) {
        log.info("获取id为 " + userId + " 的角色信息");
        List<Long> idList = userRoleDao.findRoleIdByUserId(userId);
        return roleDao.findMyRolesByIdIn(idList);
    }

    /**
     * 用户分配角色 <liuwei> [2018/1/28 10:24]
     */
    public void saveUserRole(Long userId, Long roleId) {
        MyUserRole userRole = new MyUserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        userRoleDao.save(userRole);
    }

    public MyRole findRoleById(Long id) {
        return roleDao.findOne(id);
    }

    public MyRole findRoleByType(String type) {
        return roleDao.findMyRoleByType(type);
    }
}
