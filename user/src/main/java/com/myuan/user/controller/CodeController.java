package com.myuan.user.controller;

import com.myuan.user.service.CodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * @author liuwei
 * @date 2018/3/1 19:03
 * code
 */
@Api("验证码")
@RestController
@RequestMapping("api")
public class CodeController {

    @Autowired
    private CodeService codeService;

    @ApiOperation(value = "验证码", notes = "验证码")
    @GetMapping("/{path}/code")
    public void createCode(@PathVariable("path") String path, HttpServletRequest request, HttpServletResponse response) {
        codeService.createCode(request, response);
    }
}
