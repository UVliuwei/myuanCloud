package com.myuan.code.controller;


import com.alibaba.fastjson.JSONObject;
import com.myuan.code.service.CodeService;
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
@RestController
@RequestMapping("api")
public class CodeController {

    @Autowired
    private CodeService codeService;

    @GetMapping("code/{path}")
    public JSONObject createCode(@PathVariable("path") String path, HttpServletRequest request, HttpServletResponse response) {
        return codeService.createCode(request, response);
    }
}
