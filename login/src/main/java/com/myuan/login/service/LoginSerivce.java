package com.myuan.login.service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.myuan.login.client.UserRemoteClient;
import com.myuan.login.entity.MyResult;
import com.myuan.login.entity.MyRole;
import com.myuan.login.entity.MyUser;
import com.myuan.login.utils.JWTUtil;
import com.myuan.login.utils.SaltPasswordUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 * @author liuwei
 * @date 2018/3/15 19:50
 *
 */
@Service
public class LoginSerivce {

    @Autowired
    RoleService roleService;
    @Autowired
    UserRemoteClient userRemoteClient;
    @Transactional(rollbackFor = Exception.class)
    public MyResult login(String email, String password) {
        MyUser user = userRemoteClient.getUserByEmail(email);
        if(user == null || !user.getPassword().equals(SaltPasswordUtil.getNewPass(password))) {
            return MyResult.error("用户名或密码错误");
        }
        if("1".equals(user.getLocked())) {
            return MyResult.error("账号已被锁定,禁止登录");
        }
        List<MyRole> roles = roleService.findRoleByUserId(user.getId());
        String token = JWTUtil.createToken(user.getId(), roles.toArray(new String[0]));
        JSONObject object = new JSONObject();
        object.put("name", user.getName());
        object.put("img", user.getImg());
        object.put("id", user.getId().toString());
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("info", object);
        return MyResult.data(map);
    }
}
