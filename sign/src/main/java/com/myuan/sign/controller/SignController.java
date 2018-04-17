package com.myuan.sign.controller;


import com.myuan.sign.entity.MyResult;
import com.myuan.sign.service.SignService;
import com.myuan.sign.utils.JWTUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/*
 * @author liuwei
 * @date 2018/3/2 10:22
 * 签到
 */
@Api("用户签到")
@RestController
@RequestMapping("api")
public class SignController {

    @Autowired
    private SignService signService;

    @PostMapping("sign/{userId}")
    @ApiOperation(value = "用户签到", notes = "用户签到")
    public MyResult sign(@PathVariable("userId") Long userId) {
        return signService.sign(userId);
    }

    @GetMapping("sign/status")
    @ApiOperation(value = "用户签到状态", notes = "用户签到状态")
    public MyResult signStatus(@RequestHeader("token") String token) {
        MyResult result = signService.signStatus(JWTUtil.getUserId(token));
        return result;
    }

    @GetMapping("sign/rank")
    @ApiOperation(value = "签到排行", notes = "签到排行")
    public MyResult signRank() {
        return signService.signAllInfo();
    }

    @PostMapping("sign/user/{userid}")
    @ApiOperation(value = "创建签到表", notes = "创建签到表")
    public MyResult addSign(@PathVariable("userid") Long userid) {
       return signService.addSign(userid);
    }

}
