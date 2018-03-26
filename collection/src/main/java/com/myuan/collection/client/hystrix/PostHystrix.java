package com.myuan.collection.client.hystrix;

import com.myuan.collection.client.PostRemoteClient;
import com.myuan.collection.entity.MyPost;
import org.springframework.stereotype.Component;

@Component
public class PostHystrix implements PostRemoteClient {

    @Override
    public MyPost getPostById(Long id) {
        return null;
    }
}