package com.myuan.collection.entity;

import java.io.Serializable;
import lombok.Data;

/*
 * @author liuwei
 * @date 2018/2/27 10:48
 * 用户帖子视图类
 */
@Data
public class UserPost implements Serializable{

    private MyPost post;
    private MyUser user;
}
