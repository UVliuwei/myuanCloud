package com.myuan.collection.service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.myuan.collection.client.PostRemoteClient;
import com.myuan.collection.dao.CollectionDao;
import com.myuan.collection.entity.MyCollection;
import com.myuan.collection.entity.MyPost;
import com.myuan.collection.entity.MyResult;
import java.util.List;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 * @author liuwei
 * @date 2018/2/11 14:38

 */
@Service
@Log4j
@Transactional(rollbackFor = Exception.class)
public class CollectionService {

    @Autowired
    private CollectionDao collectionDao;
    @Autowired
    private PostRemoteClient postRemoteClient;

    public MyCollection findCollection(Long userId, Long postId) {
        return collectionDao.findByUserIdAndPostId(userId, postId);
    }

    public MyResult addCollection(Long userId, Long postId) {
        MyCollection collection = new MyCollection();
        collection.setUserId(userId);
        collection.setPostId(postId);
        collection.preInsert();
        collectionDao.save(collection);
        return MyResult.ok("");
    }
    public MyResult deleteCollection(Long userId, Long postId) {
        collectionDao.deleteByUserIdAndPostId(userId, postId);
        return MyResult.ok("");
    }

    /**
     * <liuwei> [2018/2/11 15:56] collection分页 pageable 分页默认第0页开始
     */
    public JSONObject findUserCollections(Long userId, Integer page, Integer limit) {
        JSONObject object = new JSONObject();
        Sort sort = new Sort(Direction.DESC, "createDate");
        Pageable pageable = new PageRequest(page - 1, limit, sort);
        Page<MyCollection> collectionPage = collectionDao.findMyCollectionsByUserId(userId, pageable);
        List<MyPost> postList = Lists.newArrayList();
        if (collectionPage.getTotalElements() == 0) {
            object.put("code", "0");
            object.put("msg", "");
            object.put("count", "0");
            object.put("data", postList);
            return object;
        }
        for (MyCollection collection : collectionPage.getContent()) {
            postList.add(postRemoteClient.getPostById(collection.getPostId()));
        }
        object.put("code", "0");
        object.put("msg", "");
        object.put("count", collectionPage.getTotalElements() + "");
        object.put("data", postList);
        return object;
    }
}
