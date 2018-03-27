package com.myuan.post.entity;

import java.util.List;
import lombok.Data;

/*
 * @author liuwei
 * @date 2018/2/24 15:51
 *
 */
@Data
public class MyPage<T> {
    private Long count;
    private Integer currentPage;
    private Integer pageNum;
    private List<T> list;
}
