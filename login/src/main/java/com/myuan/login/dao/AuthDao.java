package com.myuan.login.dao;
/*
 * @author liuwei
 * @date 2018/1/27 16:19
 */

import com.myuan.login.entity.MyAuthority;
import java.util.List;

public interface AuthDao extends BaseDao<MyAuthority> {

    List<MyAuthority> findMyAuthoritiesByIdIn(List<Long> ids);
}
