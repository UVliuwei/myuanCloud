package com.myuan.sign.utils;
/*
 * @author liuwei
 * @date 2018/3/14 20:12
 * tooken
 */

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class JWTUtil {

    // 过期时间5分钟
    private static final long EXPIRE_TIME = 2 * 24 * 60 * 60 * 1000;
    private static final String secret = "myuan";

    /**
     * 校验token是否正确
     *
     * @param token 密钥
     * @return 是否正确
     */
    public static boolean verify(String token) {
        try {
            Long expire = getExpire(token);
            if(expire == null || System.currentTimeMillis() > expire) {
                return false;
            }
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的用户id
     */
    public static Long getUserId(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("userid").asLong();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
    public static Long getExpire(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("expire").asLong();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    public static String[] getUserRoles(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("roles").asArray(String.class);
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名,3天后过期
     *
     * @param userid 用户ID
     * @return 加密的token
     */
    public static String createToken(Long userid, String[] roles) {
        try {
            long time = System.currentTimeMillis() + EXPIRE_TIME;
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("alg", "HS256");
            map.put("typ", "jwt");
            String token = JWT.create()
                .withHeader(map)
                .withClaim("userid", userid)
                .withArrayClaim("roles", roles)
                .withClaim("expire", time)
                .sign(Algorithm.HMAC256(secret));
            return token;
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static Long getUserIdByRequest(HttpServletRequest request) {
        String token = request.getHeader("token");
        if(token == null) {
            return -1L;
        }
        Long userId = getUserId(token);
        return userId;
    }
}
