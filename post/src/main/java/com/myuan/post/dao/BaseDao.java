package com.myuan.post.dao;
/*
 * @author liuwei
 * @date 2018/1/20 8:32
 * 基准dao
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean //base接口不需要创建bean
public interface BaseDao<T> extends JpaRepository<T,Long>, JpaSpecificationExecutor<T> {

}
