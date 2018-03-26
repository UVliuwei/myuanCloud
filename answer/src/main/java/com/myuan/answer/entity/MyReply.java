package com.myuan.answer.entity;
/*
 * @author liuwei
 * @date 2018/1/19 17:32
 * 回复类
 */

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
public class MyReply extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -1568115594503286370L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //自增长主键
    private Long id;
    @NotNull
    //回复人的id
    private Long userId;

    private String userName;
    @NotNull
    private Long postId;

    private String postName;
    //被回复人的id
    private Long replyId;
    @NotNull
    private String replyName;

}
