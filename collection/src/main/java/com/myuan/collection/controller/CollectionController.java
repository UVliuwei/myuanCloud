package com.myuan.collection.controller;

import com.alibaba.fastjson.JSONObject;
import com.myuan.collection.entity.MyCollection;
import com.myuan.collection.entity.MyResult;
import com.myuan.collection.service.CollectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
 * @author liuwei
 * @date 2018/2/11 14:20
 * 收藏
 */
@RestController
@Api("收藏接口层")
@RequestMapping("api")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    @GetMapping("collection/post/{cid}/user/{uid}")
    @ApiOperation(value = "获取用户收藏", notes = "获取用户收藏")
    public MyCollection findCollection(@PathVariable("uid") Long uid, @PathVariable("cid") Long cid) {
        return collectionService.findCollection(uid, cid);
    }

    @PostMapping("collection/post/{cid}/user/{uid}")
    @ApiOperation(value = "添加用户收藏", notes = "添加用户收藏")
    public MyResult addCollection(@PathVariable("uid") Long uid, @PathVariable("cid") Long cid) {
        return collectionService.addCollection(uid, cid);
    }

    @DeleteMapping("collection/post/{cid}/user/{uid}")
    @ApiOperation(value = "删除用户收藏", notes = "删除用户收藏")
    public MyResult deleteCollection(@PathVariable("uid") Long uid, @PathVariable("cid") Long cid) {
        return collectionService.deleteCollection(uid, cid);
    }

    @GetMapping("collection/user/{id}")
    @ApiOperation(value = "用户所有收藏", notes = "用户所有收藏")
    public JSONObject getUserPosts(
        @PathVariable("id") Long userId,
        @RequestParam(required = false, defaultValue = "1") Integer page,
        @RequestParam(required = false, defaultValue = "10") Integer limit) {

        return collectionService.findUserCollections(userId, page, limit);
    }
}
