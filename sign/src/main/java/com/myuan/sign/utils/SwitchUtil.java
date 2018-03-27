package com.myuan.sign.utils;
/*
 * @author liuwei
 * @date 2018/2/27 10:59
 * 转换工具类
 */

import java.util.HashMap;
import java.util.Map;

public class SwitchUtil {

    private static Map<String, String> columnMap = new HashMap<>();
    static HashMap<Integer, String> hashMap = new HashMap<>();

    static {
        columnMap.put("all", "全部");
        columnMap.put("quiz", "提问");
        columnMap.put("share", "分享");
        columnMap.put("discuss", "讨论");
        columnMap.put("suggest", "建议");
        columnMap.put("notice", "公告");
    }


    public static final String switchColumn(String column) {
        return columnMap.get(column);
    }
    public static final Integer switchDayKiss(Integer day) {
        Integer kiss = 0;
        if (day < 5) {
            kiss = 5;
        } else if (day < 15) {
            kiss = 10;
        } else if (day < 30) {
            kiss = 15;
        } else {
            kiss = 20;
        }
        return kiss;
    }

}
