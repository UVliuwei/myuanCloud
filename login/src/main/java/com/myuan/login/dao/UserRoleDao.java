package com.myuan.login.dao;
/*
 * @author liuwei
 * @date 2018/1/20 22:19
 *
 */

import com.myuan.login.entity.MyUserRole;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleDao extends BaseDao<MyUserRole> {

    @Query("select userRole.roleId from MyUserRole userRole where userRole.userId = ?1")
    List<Long> findRoleIdByUserId(Long userId);

}
