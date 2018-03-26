package com.myuan.answer.client;

import com.myuan.answer.client.Hytrix.PostHystrix;
import com.myuan.answer.entity.MyPost;
import com.myuan.answer.entity.MyResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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

    @RequestMapping(value = "api/post/{id}/ansnum", method = RequestMethod.PUT)
    MyResult addPostAnsnum(@PathVariable("id") Long id);

    @RequestMapping(value = "api/post/{id}/accept", method = RequestMethod.DELETE)
    MyResult removeAccept(@PathVariable("id") Long id);

}
