package com.myuan.post.dao;
/*
 * @author liuwei
 * @date 2018/2/4 10:05
 * post持久层
 */

import com.myuan.post.entity.MyPost;
import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PostDao extends BaseDao<MyPost> {

    MyPost findMyPostById(Long id);

    @Modifying
    @Transactional
    @Query("update MyPost post set post.title = ?2, post.content = ?3, post.updateDate = ?4 where post.id = ?1")
    void updatePost(Long id, String title, String content, Date updateDate);

    Page<MyPost> findMyPostsByUserId(Long userId, Pageable pageable);

    void deleteById(Long id);

    @Modifying
    @Transactional
    @Query("update MyPost post set post.topped = ?2, post.updateDate = ?3 where post.id = ?1")
    void updateTop(Long id, String type, Date date);

    @Modifying
    @Transactional
    @Query("update MyPost post set post.boutiqued = ?2, post.updateDate = ?3 where post.id = ?1")
    void updateBoutique(Long id, String type, Date date);

    @Modifying
    @Transactional
    @Query("update MyPost post set post.acceptId = ?2, post.updateDate = ?3 , post.ended = '1' where post.id = ?1")
    void updateAccepted(Long postId, Long ansId, Date date);

    @Modifying
    @Transactional
    @Query("update MyPost post set post.ended = '0', post.ansnum = post.ansnum - 1 where post.acceptId = ?1")
    void removePostAccept(Long ansId);

    @Modifying
    @Transactional
    @Query("update MyPost post set post.ansnum = post.ansnum + 1 where post.id = ?1")
    void addPostAnsnum(Long id);

    @Modifying
    @Transactional
    @Query("update MyPost post set post.popularity = post.popularity + 1 where post.id = ?1")
    void addPostPopularity(Long id);

    Page<MyPost> findByCreateDateAfter(Date date, Pageable pageable);

    Page<MyPost> findMyPostsByTopped(String topped, Pageable pageable);

}
