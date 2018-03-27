package com.myuan.post.entity;
/*
 * @author liuwei
 * @date 2018/1/19 17:26
 * 收藏类
 */

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Data
@Entity
public class MyCollection extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -8218131001857860120L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //自增长主键
    private Long id;

    private Long userId;

    private Long postId;

}
