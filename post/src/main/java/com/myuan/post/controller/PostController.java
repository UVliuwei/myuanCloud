package com.myuan.post.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.Constants;
import com.google.common.base.Objects;
import com.myuan.post.entity.MyPage;
import com.myuan.post.entity.MyPost;
import com.myuan.post.entity.MyResult;
import com.myuan.post.entity.UserPost;
import com.myuan.post.service.PostService;
import com.myuan.post.service.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
 * @author liuweicd
 * @date 2018/2/4 9:41
 * 帖子接口层
 */
@RestController
@Api("帖子接口层")
@RequestMapping("api")
public class PostController extends BaseController {

    @Autowired
    private PostService postService;
    @Autowired
    private RedisService redisService;

    @PostMapping("post")
    @ApiOperation(value = "发表求解", notes = "发表求解")
    public MyResult addPost(@Valid MyPost post, BindingResult bindingResult, String vercode, String codetoken) {
        String code = redisService.get(codetoken);
        if(StringUtils.isBlank(codetoken) || !Objects.equal(code, vercode)) {
            return MyResult.error("验证码错误");
        }
        if (bindingResult.hasErrors()) {
            return validForm(bindingResult);
        }
        MyResult result = postService.savePost(post);
        return result;
    }

    @ApiOperation(value = "增加浏览", notes = "增加浏览")
    @PutMapping("post/{id}/popularity")
    public MyResult addPostPopularity(@PathVariable("id") Long id) {
        return postService.addPostPopularity(id);
    }

    @PutMapping("post")
    @ApiOperation(value = "编辑求解", notes = "编辑求解")
    public MyResult editPost(@Valid MyPost post, BindingResult bindingResult, String vercode, String codetoken) {
        String code = redisService.get(codetoken);
        if(StringUtils.isBlank(codetoken) || !Objects.equal(code, vercode)) {
            return MyResult.error("验证码错误");
        }
        if (bindingResult.hasErrors()) {
             return validForm(bindingResult);
        }
        MyResult result = postService.editPost(post);
        return result;
    }

    @ApiOperation(value = "获取类型帖", notes = "获取类型帖")
    @GetMapping(value = {"/post/{column}/{value}/{page}", "/post/{column}/{value}"})
    public MyPage<UserPost> getAllPost(@PathVariable("column") String column
        , @PathVariable("value") String value
        , @PathVariable(value = "page", required = false) String page) {
        if (StringUtils.isBlank(page) || !StringUtils.isNumeric(page)) {
            page = "1";
        }
        return postService.getAllPost(Integer.valueOf(page), 30, column, value);

    }

    @DeleteMapping("post/{id}")
    @ApiOperation(value = "删除求解", notes = "删除求解")
    public MyResult deletePost(@PathVariable("id") Long id) {
        return postService.deletePost(id);
    }

    @GetMapping("post/{id}")
    @ApiOperation(value = "获取求解", notes = "获取求解")
    public MyPost getPostById(@PathVariable("id") Long id) {
        return postService.getPostById(id);
    }

    @PutMapping("post/{id}/ansnum")
    @ApiOperation(value = "增加回复数", notes = "增加回复数")
    public MyResult addPostAnsnum(@PathVariable("id") Long id) {
        return postService.addPostAnsNum(id);
    }

    @DeleteMapping("post/{id}/accept")
    @ApiOperation(value = "删除采纳", notes = "删除采纳")
    public MyResult removeAccept(@PathVariable("id") Long id) {
        return postService.removeAccept(id);
    }

    @GetMapping("post/user/{id}")
    @ApiOperation(value = "用户所有求解", notes = "用户所有求解")
    public JSONObject getUserPosts(
        @PathVariable("id") Long userId,
        @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
        @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {

        return postService.findUserPosts(userId, page, limit);
    }

    @PutMapping("post/{id}/topped/{type}")
    @ApiOperation(value = "求解置顶", notes = "求解置顶")
    public MyResult updateTop(@PathVariable("id") Long id, @PathVariable("type") String type) {
        return postService.updateTop(id, type);
    }

    @PutMapping("post/{id}/boutiqued/{type}")
    @ApiOperation(value = "求解加精", notes = "求解加精")
    public MyResult updateBoutique(@PathVariable("id") Long id, @PathVariable("type") String type) {
        return postService.updateBoutique(id, type);
    }

    @PutMapping("post/{id}/accepted/{ansId}")
    @ApiOperation(value = "求解采纳", notes = "求解采纳")
    public MyResult updateAccepted(@PathVariable("id") Long postId, @PathVariable("ansId") Long ansId) {
        return postService.updateAccepted(postId, ansId);
    }

    @GetMapping("post/weektop")
    @ApiOperation(value = "本周热议", notes = "本周热议")
    public List<MyPost> getPostWeekTop() {
        return postService.getWeekTopPost();
    }

    @GetMapping("post/top")
    @ApiOperation(value = "置顶帖", notes = "置顶帖")
    public List<UserPost> getPostTop() {
        return postService.getTopPost();
    }

}
