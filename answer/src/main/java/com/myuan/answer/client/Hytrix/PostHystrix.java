package com.myuan.answer.client.Hytrix;
/*
 * @author liuwei
 * @date 2018/3/23 22:10
 *
 */

import com.myuan.answer.client.PostRemoteClient;
import com.myuan.answer.entity.MyPost;
import com.myuan.answer.entity.MyResult;
import org.springframework.stereotype.Component;

@Component
public class PostHystrix implements PostRemoteClient{

    @Override
    public MyPost getPostById(Long id) {
        return null;
    }

    @Override
    public MyResult addPostAnsnum(Long id) {
        return MyResult.error("系统错误");
    }

    @Override
    public MyResult removeAccept(Long id) {
        return MyResult.error("系统错误");
    }
}
