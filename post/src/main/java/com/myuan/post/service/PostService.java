package com.myuan.post.service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.myuan.post.client.AnswerRemoteClient;
import com.myuan.post.client.UserRemoteClient;
import com.myuan.post.dao.PostDao;
import com.myuan.post.entity.MyAnswer;
import com.myuan.post.entity.MyPage;
import com.myuan.post.entity.MyPost;
import com.myuan.post.entity.MyResult;
import com.myuan.post.entity.MyUser;
import com.myuan.post.entity.UserPost;
import com.myuan.post.utils.DateUtil;
import com.myuan.post.utils.SwitchUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 * @author liuwei
 * @date 2018/2/4 10:06
 * post业务层
 */
@Service
@Log4j
@Transactional(rollbackFor = Exception.class)
public class PostService {

    @Autowired
    private PostDao postDao;
    @Autowired
    private UserRemoteClient userRemoteClient;
    @Autowired
    private AnswerRemoteClient answerRemoteClient;

    public MyResult savePost(MyPost post) {

            post.preInsert();
            post.setAnsnum(0);
            post.setBoutiqued("0");
            post.setTopped("0");
            post.setPopularity(0);
            post.setAnsnum(0);
            post.setEnded("0");
            postDao.save(post);
            log.info("发帖成功");
            return MyResult.ok("求解发布成功");
    }

    public MyPost getPostById(Long id) {
        return postDao.findMyPostById(id);
    }

    public MyResult editPost(MyPost post) {
        post.preUpdate();
        postDao.updatePost(post.getId(), post.getTitle(), post.getContent(), post.getUpdateDate());
        return MyResult.ok("问题编辑成功");


    }

    /**
     * <liuwei> [2018/2/10 11:16] post分页 pageable 分页默认第0页开始
     */
    public JSONObject findUserPosts(Long userId, Integer page, Integer limit) {
        JSONObject object = new JSONObject();
        Sort sort = new Sort(Direction.DESC, "createDate");
        Pageable pageable = new PageRequest(page - 1, limit, sort);
        Page<MyPost> postPage = postDao.findMyPostsByUserId(userId, pageable);
        if (postPage.getTotalElements() == 0) {
            object.put("code", "0");
            object.put("msg", "");
            object.put("count", "0");
            object.put("data", null);
            return object;
        }
        object.put("code", "0");
        object.put("msg", "");
        object.put("count", postPage.getTotalElements() + "");
        object.put("data", postPage.getContent());
        return object;
    }

    public MyResult deletePost(Long id) {
        postDao.deleteById(id);
        return MyResult.ok("帖子删除成功");
    }

    /**
     * <liuwei> [2018/2/11 13:58] 置顶，取消置顶
     */
    public MyResult updateTop(Long id, String type) {
        postDao.updateTop(id, type, new Date());
        return MyResult.ok("");
    }

    /**
     * <liuwei> [2018/2/11 14:01] 加精，取消加精
     */
    public MyResult updateBoutique(Long id, String type) {
        postDao.updateBoutique(id, type, new Date());
        return MyResult.ok("");
    }

    /**
     * <liuwei> [2018/2/25 15:06] 采纳最佳答案
     */
    public MyResult updateAccepted(Long postId, Long ansId) {
        postDao.updateAccepted(postId, ansId, new Date());
        MyAnswer answer = answerRemoteClient.getAnswerById(ansId);
        MyPost post = postDao.findMyPostById(postId);
        MyResult result = userRemoteClient.addKiss(answer.getUserId(), post.getKiss());
        if ("-1".equals(result.getStatus())) {
            throw new RuntimeException();
        }
        return MyResult.ok("");
    }

    /**
     * <liuwei> [2018/2/26 9:58] 移除采纳
     */
    public MyResult removeAccept(Long ansId) {
        postDao.removePostAccept(ansId);
        return MyResult.ok("");
    }

    /**
     * <liuwei> [2018/2/26 9:55] 增加人气
     */
    public MyResult addPostPopularity(Long id) {
        postDao.addPostPopularity(id);
        return MyResult.ok("");
    }

    /**
     * <liuwei> [2018/2/26 9:55] 增加回答
     */
    public MyResult addPostAnsNum(Long id) {
        postDao.addPostAnsnum(id);
        return MyResult.ok("");
    }

    /**
     * <liuwei> [2018/2/26 10:45] 本周热议
     */
    public List<MyPost> getWeekTopPost() {
        Sort sort = new Sort(Direction.DESC, "ansnum");
        Pageable pageable = new PageRequest(0, 15, sort);
        Date date = DateUtil.getThisWeek();
        Page<MyPost> posts = postDao.findByCreateDateAfter(date, pageable);
        return posts.getContent();
    }

    /**
     * <liuwei> [2018/2/27 10:19] 置顶帖
     */
    @Transactional
    public List<UserPost> getTopPost() {
        Sort sort = new Sort(Direction.DESC, "createDate");
        Pageable pageable = new PageRequest(0, 5, sort);
        Page<MyPost> posts = postDao.findMyPostsByTopped("1", pageable);
        return getUserPost(posts);
    }

    /**
     * <liuwei> [2018/2/27 15:38] 所有帖子 value : all - 综合， unsolved - 已结， ununsolved - 未结， wonderful -精华 动态查询语句
     */
    public MyPage<UserPost> getAllPost(Integer page, Integer limit, final String column, final String value) {
        Sort sort = new Sort(Direction.DESC, "createDate");
        Pageable pageable = new PageRequest(page - 1, limit, sort);
        Specification<MyPost> specification = new Specification<MyPost>() {
            @Override
            public Predicate toPredicate(Root<MyPost> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = Lists.newArrayList();
                if ("all".equals(column)) {
                    predicates.add(root.<String>get("pcolumn").in("提问", "分享", "讨论", "建议", "公告"));
                } else {
                    predicates.add(cb.equal(root.<String>get("pcolumn"), SwitchUtil.switchColumn(column)));
                }
                if ("solved".equals(value)) {
                    predicates.add(cb.equal(root.<String>get("ended"), "1"));
                }
                if ("unsolved".equals(value)) {
                    predicates.add(cb.equal(root.<String>get("ended"), "0"));
                }
                if ("wonderful".equals(value)) {
                    predicates.add(cb.equal(root.<String>get("boutiqued"), "1"));
                }
                return query.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
            }
        };
        Page<MyPost> posts = postDao.findAll(specification, pageable);
        List<UserPost> userPosts = getUserPost(posts);
        MyPage<UserPost> myPage = new MyPage<>();
        if (userPosts == null) {
            myPage.setCount(0L);
            myPage.setCurrentPage(page);
            myPage.setPageNum(0);
            myPage.setList(new ArrayList<UserPost>());
        } else {
            myPage.setCount(posts.getTotalElements());
            myPage.setCurrentPage(page);
            myPage.setPageNum(posts.getTotalPages());
            myPage.setList(userPosts);
        }
        return myPage;
    }

    /**
     * <liuwei> [2018/2/27 15:50]
     */
    private List<UserPost> getUserPost(Page<MyPost> posts) {
        List<UserPost> userPostList = Lists.newArrayList();
        UserPost userPost = null;
        for (MyPost post : posts.getContent()) {
            userPost = new UserPost();
            userPost.setPost(post);
            userPost.setUser(userRemoteClient.getUser(post.getUserId()));
            userPostList.add(userPost);
        }
        return userPostList.size() == 0 ? null : userPostList;
    }
}
