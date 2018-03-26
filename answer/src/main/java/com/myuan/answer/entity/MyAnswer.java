package com.myuan.answer.entity;
/*
 * @author liuwei
 * @date 2018/1/19 16:48
 *  回答类
 */

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import lombok.Data;

@Data
@Entity
public class MyAnswer extends BaseEntity implements Serializable{

    private static final long serialVersionUID = 3523832476535463710L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //自增长主键
    private Long id;

    private Long postId;

    private Long userId;

    private String userImg;

    private String userName;

    private Integer likes;

    private String content;

    @Transient
    private String isZan;
}
