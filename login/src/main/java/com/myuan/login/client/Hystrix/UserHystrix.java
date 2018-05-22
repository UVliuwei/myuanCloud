package com.myuan.login.client.Hystrix;
/*
 * @author liuwei
 * @date 2018/3/24 8:58
 *
 */

import com.myuan.login.client.UserRemoteClient;
import com.myuan.login.entity.MyUser;
import org.springframework.stereotype.Component;

@Component
public class UserHystrix implements UserRemoteClient{

    @Override
    public MyUser getUserByEmail(String email) {
        return null;
    }

    @Override
    public MyUser getUser(Long id) {
        return null;
    }
}
