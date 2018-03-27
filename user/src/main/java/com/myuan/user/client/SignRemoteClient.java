package com.myuan.user.client;
/*
 * @author liuwei
 * @date 2018/3/18 11:46
 *
 */

import com.myuan.user.client.Hystrix.SignHystrix;
import com.myuan.user.entity.MyResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "myuan-sign", fallback = SignHystrix.class)
public interface SignRemoteClient {

    @RequestMapping(value = "sign/user/{userid}", method = RequestMethod.POST)
    MyResult addSign(@PathVariable("userid") Long userid);
}
