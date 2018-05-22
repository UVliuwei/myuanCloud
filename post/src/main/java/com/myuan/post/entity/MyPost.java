package com.myuan.post.entity;
/*
 * @author liuwei
 * @date 2018/1/19 17:31
 * 发帖类
 */

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class MyPost extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 8830292656435150109L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    //自增长主键
    private Long id;
    @Column(updatable = false)
    private Long userId;

    @Size(min = 5, max = 30, message = "标题在5到30个字之间")
    private String title;
    //类型
    private String ptype;
    //版本
    private String version;

    //专栏
    @NotNull
    private String pcolumn;
    @NotNull
    private Integer kiss;
    //回答数
    private Integer ansnum;
    //人气
    private Integer popularity;

    //加精
    private String boutiqued;
    //置顶
    private String topped;
    //完结
    private String ended;

    private Long acceptId;
    @Size(min = 10, message = "问题描述太短啦")
    private String content;

}
