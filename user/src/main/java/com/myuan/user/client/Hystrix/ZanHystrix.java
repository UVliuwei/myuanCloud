package com.myuan.user.client.Hystrix;
/*
 * @author liuwei
 * @date 2018/3/24 9:11
 *
 */

import com.myuan.user.client.ZanRemoteClient;
import com.myuan.user.entity.MyResult;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

@Component
public class ZanHystrix implements ZanRemoteClient{

    @Override
    public MyResult addUserZan(@PathVariable("userid") Long userid) {
        return MyResult.error("系统错误");
    }
}
