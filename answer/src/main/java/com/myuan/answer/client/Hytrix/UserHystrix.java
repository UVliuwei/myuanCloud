package com.myuan.answer.client.Hytrix;
/*
 * @author liuwei
 * @date 2018/3/23 20:08
 * 断路器
 */

import com.myuan.answer.client.UserRemoteClient;
import com.myuan.answer.entity.MyUser;
import org.springframework.stereotype.Component;

@Component
public class UserHystrix implements UserRemoteClient{

    @Override
    public MyUser getUser(Long id) {
        return null;
    }

    @Override
    public MyUser getUserByName(String name) {
        return null;
    }
}
