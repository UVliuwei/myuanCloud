package com.myuan.login.controller;

import com.google.common.base.Objects;
import com.myuan.login.entity.MyResult;
import com.myuan.login.service.LoginSerivce;
import com.myuan.login.service.RedisService;
import com.myuan.login.utils.JWTUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * @author liuwei
 * @date 2018/1/19 14:28
 *
 */
@Log4j
@Api("用户登录")
@RestController
@RequestMapping("api/login")
public class LoginController {

    @Autowired
    private LoginSerivce loginSerivce;
    @Autowired
    private RedisService redisService;
    /**
     * 登录 <liuwei> [2018/1/19 16:04]
     */
    @PostMapping("login")
    @ApiOperation(value = "用户登录", notes = "用户登录")
    public MyResult login(String email, String password, String vercode, String codetoken) {
        String code = redisService.get(codetoken);
        if(StringUtils.isBlank(codetoken) || !Objects.equal(code, vercode)) {
            return MyResult.error("验证码错误");
        }
        log.info(email + " : 登陆成功");
        return loginSerivce.login(email, password);
    }

    @GetMapping("info")
    @ApiOperation(value = "登陆信息", notes = "登陆信息")
    public MyResult loginInfo(@RequestHeader("token") String token) {
        return loginSerivce.loginInfo(JWTUtil.getUserId(token));
    }
}
