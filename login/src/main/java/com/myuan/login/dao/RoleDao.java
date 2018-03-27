package com.myuan.login.dao;
/*
 * @author liuwei
 * @date 2018/1/20 22:08
 *
 */

import com.myuan.login.entity.MyRole;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends BaseDao<MyRole> {

    List<MyRole> findMyRolesByIdIn(List<Long> ids);

    MyRole findMyRoleByType(String type);
}
