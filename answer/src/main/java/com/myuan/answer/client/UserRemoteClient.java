package com.myuan.answer.client;

import com.myuan.answer.client.Hytrix.UserHystrix;
import com.myuan.answer.entity.MyResult;
import com.myuan.answer.entity.MyUser;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
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

    @RequestMapping(value = "api/user/{id}", method = RequestMethod.GET)
    MyUser getUser(@PathVariable("id") Long id);

    @RequestMapping(value = "api/user/name", method = RequestMethod.GET)
    MyUser getUserByName(@RequestParam(value = "name") String name);
}
