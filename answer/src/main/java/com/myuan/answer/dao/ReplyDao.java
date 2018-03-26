package com.myuan.answer.dao;
/*
 * @author liuwei
 * @date 2018/2/12 10:01
 *
 */

import com.myuan.answer.entity.MyReply;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ReplyDao extends BaseDao<MyReply>{

    Integer countByReplyId(Long userId);

    List<MyReply> findMyRepliesByReplyId(Long replyId);

    @Modifying
    @Transactional
    void deleteById(Long id);

    @Modifying
    @Transactional
    void deleteAllByReplyId(Long replyId);
}
