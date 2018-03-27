package com.myuan.user.client;

import com.myuan.user.client.Hystrix.ZanHystrix;
import com.myuan.user.entity.MyResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*
 * @author liuwei
 * @date 2018/3/9 16:21
 * 点赞远程调用
 */
@FeignClient(name = "myuan-answer", fallback = ZanHystrix.class)
public interface ZanRemoteClient {

    //创建用户点赞表
    @RequestMapping(value = "api/zan/user/{userid}", method = RequestMethod.PUT)
    MyResult addUserZan(@PathVariable("userid") Long userid);
}
