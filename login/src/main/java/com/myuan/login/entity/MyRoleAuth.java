package com.myuan.login.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

/*
 * @author liuwei
 * @date 2018/1/20 22:14
 *
 */
@Data
@Entity
public class MyRoleAuth {
    //自增长主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long roleId;

    private Long authId;
}
