package com.myuan.answer.controller;

import com.myuan.answer.entity.MyAnswer;
import com.myuan.answer.entity.MyPage;
import com.myuan.answer.entity.MyResult;
import com.myuan.answer.entity.PostAnswer;
import com.myuan.answer.service.AnswerService;
import com.myuan.answer.entity.UserAnswer;
import com.myuan.answer.utils.JWTUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/*
 * @author liuwei
 * @date 2018/2/12 8:58
 * 回复接口层
 */
@RestController
@Api("回复接口层")
@RequestMapping("api")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @PostMapping("answer")
    @ApiOperation(value = "添加回复", notes = "添加回复")
    public MyResult addReply(String content, Long postId, @RequestHeader("token") String token) {
        Long userId = JWTUtil.getUserId(token);
        MyResult result = answerService.addAnswer(userId, postId, content);
        return result;
    }

    @ApiOperation(value = "获取回复", notes = "获取回复")
    @GetMapping("answer/{id}")
    public MyAnswer getAnswerById(@PathVariable("id") Long id) {
        return answerService.getAnswerById(id);
    }

    @ApiOperation(value = "未读消息条数", notes = "未读消息条数")
    @GetMapping("message/nums")
    public MyResult messageNum(@RequestHeader("token") String token) {
        Integer num = answerService.getMessageNums(JWTUtil.getUserId(token));
        return MyResult.data(num.toString());
    }

    @ApiOperation(value = "回答", notes = "回答")
    @GetMapping("answer/post/{id}/answers")
    public MyPage<MyAnswer> findPostAnswers(@PathVariable("id") Long postId,
        @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
        @RequestParam(value = "limit", required = false, defaultValue = "6") Integer limit,
        @RequestHeader("token") String token) {

        Long userId = -1L;
        if(JWTUtil.verify(token)) {
           userId = JWTUtil.getUserId(token);
        }
        return answerService.findAnswers(postId, page, limit, userId);
    }

    @ApiOperation(value = "未读消息", notes = "未读消息")
    @GetMapping("message/user/{id}")
    public MyResult message(@RequestHeader("token") String token) {
        return answerService.findUserMessage(JWTUtil.getUserId(token));
    }

    @ApiOperation(value = "获取用户回答", notes = "获取用户回答")
    @GetMapping("answer/user/{userid}/answers")
    public List<PostAnswer> getUserAnswer(@PathVariable("userid") Long userid) {
        return answerService.findUserAnswers(userid);
    }

    @ApiOperation(value = "删除消息", notes = "删除消息")
    @DeleteMapping("message/{id}")
    public MyResult deleteMessage(@PathVariable("id") Long id) {
        return answerService.deleteMessage(id);
    }

    @ApiOperation(value = "删除用户全部消息", notes = "删除用户全部消息")
    @DeleteMapping("message/user/{id}")
    public MyResult deleteUserMessages(@RequestHeader("token") String token) {
        return answerService.deleteMessages(JWTUtil.getUserId(token));
    }

    @ApiOperation(value = "删除回答", notes = "删除回答")
    @DeleteMapping("answer/{id}/{flag}")
    public MyResult deleteAnswer(@PathVariable("id") Long id, @PathVariable("flag") String flag) {
        return answerService.deleteAnswer(id, flag);
    }

    @ApiOperation(value = "回帖周榜", notes = "回帖周榜")
    @GetMapping("answer/top/users")
    public List<UserAnswer> getTopUsers() {
        return answerService.findTopAnswerUsers();
    }
}
