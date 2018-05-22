package com.myuan.answer.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.myuan.answer.client.PostRemoteClient;
import com.myuan.answer.client.UserRemoteClient;
import com.myuan.answer.dao.AnswerDao;
import com.myuan.answer.dao.ReplyDao;
import com.myuan.answer.entity.*;
import com.myuan.answer.utils.DateUtil;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;


/*
 * @author liuwei
 * @date 2018/2/12 9:57
 *
 */
@Service
@Log4j
@Transactional(rollbackFor = Exception.class)
public class AnswerService {

    @Autowired
    private ReplyDao replyDao;
    @Autowired
    private AnswerDao answerDao;
    @Autowired
    private PostRemoteClient postRemoteClient;
    @Autowired
    private UserRemoteClient userRemoteClient;
    @Autowired
    private ZanService zanService;

    /**
     * <liuwei> [2018/2/24 9:34] 发表回复
     */
    public MyResult addAnswer(Long userId, Long postId, String content){
        MyUser user = userRemoteClient.getUser(userId);
        MyAnswer answer = new MyAnswer();
        answer.setContent(content.trim());
        answer.setPostId(postId);
        answer.setUserId(userId);
        answer.setLikes(0);
        answer.setUserImg(user.getImg());
        answer.setUserName(user.getName());
        answer.preInsert();
        answerDao.save(answer);
        String[] strings = content.trim().split(" ");
        String reg = "@.*";
        boolean matches = Pattern.matches(reg, strings[0]);
        String html = "<li><div class=\"detail-about detail-about-reply\"><a class=\"fly-avatar\" href=\"/user/" + user.getId()
            + "/info\" target=\"_blank\"><img src=\"/static/images/" + user.getImg() + "\" alt=\"" + user.getName()
            + "\"></a><div class=\"fly-detail-user\"><a href=\"/user/" + user.getId() + "/info\" target=\"_blank\" class=\"fly-link\"><cite>"
            + user.getName()
            + "</cite></a></div><div class=\"detail-hits\"><span>刚刚</span></div></div><div id='ahtml' class=\"detail-body jieda-body photos\">"
            + content + "</div></li>";
        if (matches) {
            MyReply reply = new MyReply();
            MyUser replyUser = userRemoteClient.getUserByName(strings[0].substring(1));
            reply.setPostId(postId);
            reply.setPostName(postRemoteClient.getPostById(postId).getTitle());
            reply.setUserId(userId);
            reply.setUserName(user.getName());
            if (replyUser == null) {
                reply.setReplyId(-1L);
            } else {
                reply.setReplyId(user.getId());
            }
            reply.setReplyName(strings[0].substring(1));
            reply.preInsert();
            replyDao.save(reply);
        }
        MyResult result = postRemoteClient.addPostAnsnum(postId);
        if("-1".equals(result.getStatus())) {
            throw new RuntimeException();
        }
        return MyResult.data(html);
    }

    public Integer getMessageNums(Long userId) {
        return replyDao.countByReplyId(userId);
    }

    public MyAnswer getAnswerById(Long ansId) {
        return answerDao.findOne(ansId);
    }

    public MyResult findUserMessage(Long id) {
        List<MyReply> replies = replyDao.findMyRepliesByReplyId(id);
        return MyResult.data(JSON.toJSONString(replies));
    }

    public MyResult deleteMessage(Long id) {
        replyDao.deleteById(id);
        return MyResult.ok("");
    }

    /**
     * <liuwei> [2018/3/1 9:29]清空全部消息
     */
    public MyResult deleteMessages(Long userId) {
        replyDao.deleteAllByReplyId(userId);
        return MyResult.ok("");
    }

    public MyResult deleteAnswer(Long id, String flag){
        answerDao.deleteById(id);
        if ("true".equals(flag)) {
            MyResult result = postRemoteClient.removeAccept(id);
            if("-1".equals(result.getStatus())) {
                throw new RuntimeException();
            }
        }
        return MyResult.ok("");
    }

    /**
     * <liuwei> [2018/2/24 14:08] 回复分页
     */
    public MyPage<MyAnswer> findAnswers(long postId, Integer page, Integer limit, long userId) {
        Sort sort = new Sort(Direction.ASC, "createDate");
        Pageable pageable = new PageRequest(page - 1, limit, sort);
        Page<MyAnswer> answers = answerDao.findMyAnswersByPostId(postId, pageable);
        MyPage<MyAnswer> myPage = new MyPage<>();
        if (answers.getTotalElements() == 0) {
            myPage.setCount(0L);
            myPage.setCurrentPage(page);
            myPage.setPageNum(0);
            myPage.setList(new ArrayList<MyAnswer>());
        } else {
            myPage.setCount(answers.getTotalElements());
            myPage.setCurrentPage(page);
            myPage.setPageNum(answers.getTotalPages());
            if (userId != -1l) {
                for (MyAnswer answer : answers.getContent()) {
                    answer.setIsZan(zanService.checkZan(userId, answer.getId()));
                }
            }
            myPage.setList(answers.getContent());
        }
        return myPage;
    }

    /**
     * <liuwei> [2018/2/26 8:36] 用户回答
     */
    public List<PostAnswer> findUserAnswers(Long userId) {
        Sort sort = new Sort(Direction.ASC, "createDate");
        Pageable pageable = new PageRequest(0, 8, sort);
        List<MyAnswer> content = answerDao.findMyAnswersByUserId(userId, pageable).getContent();
        List<PostAnswer> list = Lists.newArrayList();
        for (MyAnswer answer : content) {
            PostAnswer postAnswer = new PostAnswer();
            postAnswer.setPost(postRemoteClient.getPostById(answer.getPostId()));
            postAnswer.setAnswer(answer);
            list.add(postAnswer);
        }
        return list;
    }

    public MyAnswer findAnswerById(Long id) {
        return answerDao.findOne(id);
    }

    /**
     * <liuwei> [2018/2/27 14:13] 回帖周榜
     */
    public List<UserAnswer> findTopAnswerUsers() {
        Sort sort = new Sort(Direction.DESC, "createDate");
        Pageable pageable = new PageRequest(0, 12, sort);
        Date date = DateUtil.getThisWeek();
        Page<Long> userAnswers = answerDao.findTopAnswerUsers(date, pageable);
        List<UserAnswer> userList = Lists.newArrayList();
        UserAnswer userAnswer = null;
        System.out.println(userRemoteClient.getUser(29L));
        for (Long id : userAnswers.getContent()) {
            userAnswer = new UserAnswer();
            userAnswer.setUser(userRemoteClient.getUser(id));
            userAnswer.setCount(answerDao.countByUserIdAndCreateDateAfter(id, date));
            userList.add(userAnswer);
        }
        return userList;
    }

    /**
     * <liuwei> [2018/3/1 8:51] 点赞量修改
     */
    public void addAnswerZanNum(Long id, Integer num) {
        MyAnswer answer = answerDao.findOne(id);
        answer.setLikes(answer.getLikes() + num);
    }

}
