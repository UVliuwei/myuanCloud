package com.myuan.login.service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Objects;
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
        return createLoginInfo(user.getId());
    }

    public MyResult loginInfo(Long id) {
        return createLoginInfo(id);
    }

    //登陆信息 <liuwei> [2018/5/17 16:25]
    private MyResult createLoginInfo(Long id) {
        MyUser user = userRemoteClient.getUser(id);
        List<String> roleList = Lists.newArrayList();
        List<MyRole> roles = roleService.findRoleByUserId(id);
        for (MyRole role : roles) {
            roleList.add(role.getType());
        }
        String token = JWTUtil.createToken(id, roleList.toArray(new String[0]));
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        user.setPassword("");
        map.put("info", user);
        //是否是管理员
        String roleFlag = "false";
        for (String role : roleList) {
            if("admin".equals(role) || "superadmin".equals(role)) {
                roleFlag = "true";
            }
        }
        map.put("isAdmin", roleFlag);
        return MyResult.data(map);
    }
}
