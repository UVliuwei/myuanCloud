package com.myuan.answer.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

/*
 * @author liuwei
 * @date 2018/3/1 8:40
 * 点赞
 */
@Data
@Entity
public class MyZan extends BaseEntity implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //自增长主键
    private Long id;

    private Long userId;

    private String AnswerIds;
}
