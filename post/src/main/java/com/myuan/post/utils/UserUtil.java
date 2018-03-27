package com.myuan.post.utils;
/*
 * @author liuwei
 * @date 2018/1/27 14:36
 *
 */

import com.myuan.post.entity.MyUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

public class UserUtil {

    /**获取当前用户
     *
     * @return
     */
    public static final MyUser getCurrentUser() {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession(true);
        if(session.getAttribute("user")!=null) {
            MyUser user = (MyUser) session.getAttribute("user");
            return user;
        }
        return null;
    }
    /**
     * 用户是否登录
     * @return
     */
    public static boolean isLogin() {

        MyUser user = getCurrentUser();
        if(user == null) {
            return false;
        }
        return true;
    }
}
