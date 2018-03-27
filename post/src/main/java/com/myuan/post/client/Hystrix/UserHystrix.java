package com.myuan.post.client.Hystrix;
/*
 * @author liuwei
 * @date 2018/3/24 9:03
 *
 */

import com.myuan.post.client.UserRemoteClient;
import com.myuan.post.entity.MyResult;
import com.myuan.post.entity.MyUser;
import org.springframework.stereotype.Component;

@Component
public class UserHystrix implements UserRemoteClient{

    @Override
    public MyResult addKiss(Long id, Integer kiss) {
        return MyResult.error("系统异常");
    }

    @Override
    public MyUser getUser(Long id) {
        return null;
    }
}
