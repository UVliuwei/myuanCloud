package com.myuan.collection.entity;

import lombok.Data;

/*
 * @author liuwei
 * @date 2018/1/20 8:35
 * 结果类
 */
@Data
public class MyResult {

    //-1 失败 、 1 成功 、2 无权限、 0 认证失败(未登录)
    private String status;
    private String msg;
    private Object data;

    //必须有空的构造方法，否则报错
    public MyResult() { }

    public MyResult(String status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static MyResult ok(String msg) {
        return new MyResult("1", msg, null);
    }

    public static MyResult data(Object data) {
        return new MyResult("1", null, data);
    }

    public static MyResult error(String msg) {
        return new MyResult("-1", msg, null);
    }

    public static MyResult noAuth() {return new MyResult("2", "无权限", null);}

    public static MyResult noLogin() {return new MyResult("0", "未登录", null);}
}
