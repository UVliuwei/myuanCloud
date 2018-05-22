package com.myuan.login.client;

import com.myuan.login.client.Hystrix.UserHystrix;
import com.myuan.login.entity.MyUser;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/*
 * @author liuwei
 * @date 2018/3/9 18:29
 * 用户模块远程调用
 */
@FeignClient(name = "myuan-user", fallback = UserHystrix.class)
public interface UserRemoteClient {

    @RequestMapping(value = "api/user/email", method = RequestMethod.GET)
    MyUser getUserByEmail(@RequestParam(value = "email") String email);

    @RequestMapping(value = "api/user/{id}", method = RequestMethod.GET)
    MyUser getUser(@PathVariable("id") Long id);
}
