package com.myuan.answer.service;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.myuan.answer.dao.ZanDao;
import com.myuan.answer.entity.MyReply;
import com.myuan.answer.entity.MyResult;
import com.myuan.answer.entity.MyZan;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 * @author liuwei
 * @date 2018/3/1 8:45
 * 点赞业务层
 */
@Service
@Transactional(readOnly = false)
public class ZanService {

    @Autowired
    private ZanDao zanDao;
    @Autowired
    private AnswerService answerService;

    public MyResult addUserZan(Long userId) {
        MyZan zan = new MyZan();
        zan.setUserId(userId);
        zan.preInsert();
        zanDao.save(zan);
        return MyResult.ok("");
    }

    /**
     * 查询出的实体类处于托管状态，直接setXX（）可修改数据库内容
     */
    public void removeZan(Long userId, Long answerId) {
        MyZan zan = zanDao.findByUserId(userId);
        if (StringUtils.isNotBlank(zan.getAnswerIds())) {
            List<Long> idList = JSON.parseArray(zan.getAnswerIds(), Long.class);
            Iterator<Long> iterator = idList.iterator();
            while (iterator.hasNext()) {
                Long value = iterator.next();
                if (Objects.equal(answerId, value)) {
                    iterator.remove();
                    answerService.addAnswerZanNum(answerId, -1);
                    break;
                }
            }
            String ids = JSON.toJSONString(idList);
            zan.setAnswerIds(ids);
        }
    }

    /*
        <liuwei> [2018/3/24 8:41]
        添加 赞表
     */
    public void addZan(Long userId, Long answerId) {
        MyZan zan = zanDao.findByUserId(userId);
        if(zan == null) {
            addUserZan(userId);
            zan = zanDao.findByUserId(userId);
        }
        if (StringUtils.isNotBlank(zan.getAnswerIds())) {
            List<Long> idList = JSON.parseArray(zan.getAnswerIds(), Long.class);
            idList.add(answerId);
            String ids = JSON.toJSONString(idList);
            zan.setAnswerIds(ids);
        } else {
            List<Long> idList = Lists.newArrayList(answerId);
            String ids = JSON.toJSONString(idList);
            zan.setAnswerIds(ids);
        }
        answerService.addAnswerZanNum(answerId, 1);
    }

    /**
     * <liuwei> [2018/3/1 9:22] 用户是否点赞
     */
    public String checkZan(Long userId, Long answerId) {
        MyZan zan = zanDao.findByUserId(userId);
        if (zan !=null && StringUtils.isNotBlank(zan.getAnswerIds())) {
            List<Long> idList = JSON.parseArray(zan.getAnswerIds(), Long.class);
            for (Long id : idList) {
                if(id.equals(answerId)) {
                    return "true";
                }
            }
        }
        return "false";
    }
}
