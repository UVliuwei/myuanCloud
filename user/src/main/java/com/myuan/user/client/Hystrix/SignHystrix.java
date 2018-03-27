package com.myuan.user.client.Hystrix;
/*
 * @author liuwei
 * @date 2018/3/24 9:10
 *
 */

import com.myuan.user.client.SignRemoteClient;
import com.myuan.user.entity.MyResult;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

@Component
public class SignHystrix implements SignRemoteClient{

    @Override
    public MyResult addSign(@PathVariable("userid") Long userid) {
        return MyResult.error("系统错误");
    }
}
