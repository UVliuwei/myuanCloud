package com.myuan.post.client.Hystrix;
/*
 * @author liuwei
 * @date 2018/3/24 9:03
 *
 */

import com.myuan.post.client.AnswerRemoteClient;
import com.myuan.post.entity.MyAnswer;
import org.springframework.stereotype.Component;

@Component
public class AnswerStrix implements AnswerRemoteClient{

    @Override
    public MyAnswer getAnswerById(Long id) {
        return null;
    }
}
