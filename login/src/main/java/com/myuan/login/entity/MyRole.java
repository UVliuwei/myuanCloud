package com.myuan.login.entity;
/*
 * @author liuwei
 * @date 2018/1/19 17:34
 * 角色类
 */

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class MyRole extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 6227154819811973488L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //自增长主键
    private Long id;
    @Column(unique = true)
    private String name;

    private String type;
}
