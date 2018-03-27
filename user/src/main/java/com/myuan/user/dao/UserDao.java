package com.myuan.user.dao;
/*
 * @author liuwei
 * @date 2018/1/19 18:00
 * user持久层
 */

import com.myuan.user.entity.MyUser;
import java.util.Date;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserDao extends BaseDao<MyUser> {

    MyUser findMyUsersByName(String name);

    MyUser findMyUsersByEmail(String email);

    MyUser findMyUsersById(Long id);

    @Modifying
    @Transactional
    @Query("update MyUser user set user.password = ?2 where user .id = ?1")
    void updateMyUserPass(Long id, String pass);

    @Modifying
    @Transactional
    @Query("update MyUser user set user.img = ?2 where user .id = ?1")
    void updateMyUserImg(Long id, String img);

    @Modifying
    @Transactional
    @Query("update MyUser user set user.name = ?2, user.sex = ?3, user.city = ?4, user.description = ?5, user.updateDate = ?6 where user.id = ?1")
    void updateUserInfo(Long id, String name, String sex, String city, String description, Date updateDate);

    @Modifying
    @Transactional
    @Query("update MyUser user set user.kiss = user.kiss + ?2 where user.id = ?1")
    void addUserKiss(Long id, Integer kiss);
}
