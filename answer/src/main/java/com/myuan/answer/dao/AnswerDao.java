package com.myuan.answer.dao;

import com.myuan.answer.entity.MyAnswer;
import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/*
 * @author liuwei
 * @date 2018/2/12 10:38
 *
 */
@Repository
public interface AnswerDao extends BaseDao<MyAnswer>{

    Page<MyAnswer> findMyAnswersByPostId(Long postId, Pageable pageable);

    @Modifying
    @Transactional
    void deleteById(Long id);

    Page<MyAnswer> findMyAnswersByUserId(Long userId, Pageable pageable);

    @Query("select answer.userId from MyAnswer answer where answer.createDate > ?1 group by answer.userId order by count(answer) DESC")
    Page<Long> findTopAnswerUsers(Date date, Pageable pageable);

    Integer countByUserIdAndCreateDateAfter(Long userId, Date date);

}
