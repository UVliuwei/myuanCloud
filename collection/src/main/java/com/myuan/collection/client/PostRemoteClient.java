package com.myuan.collection.client;

import com.myuan.collection.client.hystrix.PostHystrix;
import com.myuan.collection.entity.MyPost;
import com.myuan.collection.entity.MyResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*
 * @author liuwei
 * @date 2018/3/9 18:53
 *
 */
@FeignClient(name = "myuan-post", fallback = PostHystrix.class)
public interface PostRemoteClient {

    @RequestMapping(value = "api/post/{id}", method = RequestMethod.GET)
    MyPost getPostById(@PathVariable("id") Long id);

}
