package com.myuan.sign.dao;
/*
 * @author liuwei
 * @date 2018/3/2 10:26
 *
 */


import com.myuan.sign.entity.MySign;
import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface SignDao extends BaseDao<MySign> {

    MySign findByUserId(Long userId);

    Page<MySign> findByContinueNumGreaterThanAndUpdateDateAfter(int num, Date date, Pageable pageable);

    Page<MySign> findByContinueNumGreaterThan(int num, Pageable pageable);

    @Transactional
    @Modifying
    @Query("update MySign mysign set mysign.continueNum = ?2 where mysign.id = ?1")
    void addContinueNum(Long id, Integer continueNum);
}
