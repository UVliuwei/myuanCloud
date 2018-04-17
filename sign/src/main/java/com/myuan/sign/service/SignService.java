package com.myuan.sign.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.myuan.sign.client.UserRemoteClient;
import com.myuan.sign.dao.SignDao;
import com.myuan.sign.entity.MyResult;
import com.myuan.sign.entity.MySign;
import com.myuan.sign.entity.MyUser;
import com.myuan.sign.utils.DateUtil;
import com.myuan.sign.utils.SwitchUtil;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 * @author liuwei
 * @date 2018/3/2 10:25
 *  签到业务层
 */
@Service
public class SignService {

    @Autowired
    private SignDao signDao;
    @Autowired
    private UserRemoteClient userRemoteClient;

    @Transactional(rollbackFor = Exception.class)
    /**
     * <liuwei> [2018/3/2 10:50] 签到
     */
    public MyResult sign(Long userId) {
        MySign sign = signDao.findByUserId(userId);
        Integer kiss = 0;
        if (sign.getContinueNum() == 0) {
            sign.setContinueNum(1);
            MyResult result = userRemoteClient.addKiss(userId, 5);
            if("-1".equals(result)) {
                throw new RuntimeException();
            }
            kiss = 5;
        } else {
            if (DateUtil.checkDay(sign.getUpdateDate())) {
                sign.setContinueNum(sign.getContinueNum() + 1);
            } else {
                sign.setContinueNum(1);
            }
            kiss = SwitchUtil.switchDayKiss(sign.getContinueNum());
            MyResult result = userRemoteClient.addKiss(userId, kiss);
            if("-1".equals(result)) {
                throw new RuntimeException();
            }
        }
        sign.setUpdateDate(new Date());
        JSONObject object = new JSONObject();
        object.put("kiss", kiss.toString());
        object.put("days", sign.getContinueNum());
        object.put("signed", true);
        return MyResult.data(JSON.toJSONString(object));
    }

    /*
       <liuwei> [2018/3/2 10:50] 签到表
     */
    public MyResult addSign(Long userId) {
        MySign sign = new MySign();
        sign.setUserId(userId);
        sign.setContinueNum(0);
        sign.preInsert();
        signDao.save(sign);
        return MyResult.ok("");
    }

    /**
     * <liuwei> [2018/3/2 11:10]签到状态
     */
    public MyResult signStatus(Long userId) {
        MySign sign = signDao.findByUserId(userId);
        if(sign == null) {
            addSign(userId);
            sign = signDao.findByUserId(userId);
        }
        Date date = sign.getUpdateDate();
        Calendar c1 = new GregorianCalendar();
        c1.setTime(date);
        Calendar c2 = new GregorianCalendar();
        JSONObject object = new JSONObject();
        if (c2.get(Calendar.DATE) == c1.get(Calendar.DATE) && sign.getContinueNum() != 0) {
            object.put("signed", true);
        } else {
            object.put("signed", false);
        }
        object.put("kiss", SwitchUtil.switchDayKiss(sign.getContinueNum()));
        object.put("days", sign.getContinueNum());
        return MyResult.data(JSON.toJSONString(object));
    }

    /**
     * <liuwei> [2018/3/2 20:49] 最新签到
     */
    public List<MySign> lastSign() {
        Sort sort = new Sort(Direction.DESC, "updateDate");
        Pageable pageable = new PageRequest(0, 20, sort);
        //本日
        Calendar c = new GregorianCalendar();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        Page<MySign> signs = signDao.findByContinueNumGreaterThanAndUpdateDateAfter(0, c.getTime(), pageable);
        return signs.getContent();
    }

    /**
     * <liuwei> [2018/3/2 22:28] 最快签到
     */
    public List<MySign> fastSign() {
        Sort sort = new Sort(Direction.ASC, "updateDate");
        Pageable pageable = new PageRequest(0, 20, sort);
        Calendar c = new GregorianCalendar();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        Page<MySign> signs = signDao.findByContinueNumGreaterThanAndUpdateDateAfter(0, c.getTime(), pageable);
        return signs.getContent();
    }

    /**
     * <liuwei> [2018/3/2 22:32] 签到总榜
     */
    public List<MySign> topSign() {
        Sort sort = new Sort(Direction.DESC, "continueNum");
        Pageable pageable = new PageRequest(0, 20, sort);
        Page<MySign> signs = signDao.findByContinueNumGreaterThan(0, pageable);
        return signs.getContent();
    }

    /**
     * <liuwei> [2018/3/2 22:34] 签到活跃榜
     */
    public MyResult signAllInfo() {
        List<MySign> lastList = lastSign();
        List<MySign> fastList = fastSign();
        List<MySign> topList = topSign();
        List<JSONArray> reList = Lists.newArrayList();
        reList.add(handleReList(lastList));
        reList.add(handleReList(fastList));
        reList.add(handleReList(topList));
        return MyResult.data(reList);
    }

    private JSONArray handleReList(List<MySign> list) {
        JSONArray jsonArray = new JSONArray();
        JSONObject object = null;
        for (MySign sign : list) {
            object = new JSONObject();
            MyUser user = userRemoteClient.getUser(sign.getUserId());
            object.put("uid", sign.getUserId());
            object.put("days", sign.getContinueNum());
            object.put("time", sign.getUpdateDate());
            object.put("user", user);
            jsonArray.add(object);
        }
        return jsonArray;
    }
}
