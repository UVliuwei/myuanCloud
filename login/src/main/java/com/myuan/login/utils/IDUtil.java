package com.myuan.login.utils;

import java.util.Random;
import java.util.UUID;

public class IDUtil {

    public static String genImageId() {

        long millis = System.currentTimeMillis();
        Random random = new Random();
        int end3 = random.nextInt(999);
        String str = millis + String.format("%03d", end3);
        return str;
    }

    public static String code() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        return uuid;
    }

    public static int randomer() {
        Random random = new Random();
        return random.nextInt(11);
    }
}
