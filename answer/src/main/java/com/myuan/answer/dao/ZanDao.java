package com.myuan.answer.dao;

import com.myuan.answer.entity.MyZan;
import org.springframework.stereotype.Repository;

/*
 * @author liuwei
 * @date 2018/3/1 8:45
 *
 */
@Repository
public interface ZanDao extends BaseDao<MyZan>{

    MyZan findByUserId(Long userId);
}
