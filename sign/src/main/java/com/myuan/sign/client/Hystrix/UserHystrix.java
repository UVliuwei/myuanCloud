package com.myuan.sign.client.Hystrix;
/*
 * @author liuwei
 * @date 2018/3/24 9:07
 *
 */

import com.myuan.sign.client.UserRemoteClient;
import com.myuan.sign.entity.MyResult;
import com.myuan.sign.entity.MyUser;
import org.springframework.stereotype.Component;

@Component
public class UserHystrix implements UserRemoteClient{

    @Override
    public MyResult addKiss(Long id, Integer kiss) {
        return MyResult.error("系统错误");
    }

    @Override
    public MyUser getUser(Long id) {
        return null;
    }
}
