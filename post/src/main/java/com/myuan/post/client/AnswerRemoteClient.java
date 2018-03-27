package com.myuan.post.client;

import com.myuan.post.client.Hystrix.AnswerStrix;
import com.myuan.post.entity.MyAnswer;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*
 * @author liuwei
 * @date 2018/3/9 18:38
 *  回复远程调用
 */
@FeignClient(value = "myuan-answer", fallback = AnswerStrix.class)
public interface AnswerRemoteClient {

    @RequestMapping(value = "api/answer/{id}", method = RequestMethod.GET)
    MyAnswer getAnswerById(@PathVariable("id") Long id);
}
