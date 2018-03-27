package com.myuan.login.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

/*
 * @author liuwei
 * @date 2018/1/19 16:58
 *  权限类
 */
@Data
@Entity
public class MyAuthority extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 4710757893851847835L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //自增长主键
    private Long id;

    private String name;

    private String auth;
}
