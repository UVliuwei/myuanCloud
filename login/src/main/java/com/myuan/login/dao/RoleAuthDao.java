package com.myuan.login.dao;
/*
 * @author liuwei
 * @date 2018/1/20 22:30
 *
 */

import com.myuan.login.entity.MyRoleAuth;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleAuthDao extends BaseDao<MyRoleAuth> {

    @Query("select roleAuth.authId from MyRoleAuth roleAuth where roleAuth.roleId = ?1")
    List<Long> findAuthIdByRoleId(Long roleId);
}
