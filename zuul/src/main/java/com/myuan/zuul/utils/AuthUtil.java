package com.myuan.zuul.utils;
/*
 * @author liuwei
 * @date 2018/3/28 21:04
 * 权限
 */

import com.myuan.zuul.entity.MyResult;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class AuthUtil {

    private static Properties properties = null;

    public static MyResult checkAuth(HttpServletRequest request, String token) {
        try {
            String requestURI = request.getRequestURI().replaceFirst("/", "");
            String method = request.getMethod().toLowerCase();
            String[] strings = requestURI.split("/");
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < strings.length; i++) {
                if (StringUtils.isNumeric(strings[i])) {
                    sb.append("/*");
                    continue;
                }
                sb.append("/" + strings[i]);
            }
            String uriTemp = sb.toString().replaceFirst("/", "");
            String url = method + "|" + uriTemp;
            return getAuth(url, token);
        } catch (Exception e) {
            e.printStackTrace();
        }
       return MyResult.noAuth();
    }

    //权限验证
    private static MyResult getAuth(String url, String token) throws Exception {
        if(properties == null) {
            ClassPathResource resource = new ClassPathResource("bootstrap.properties");
            properties = PropertiesLoaderUtils.loadProperties(resource);
        }
        String auths = properties.getProperty(url);
        if(StringUtils.isBlank(auths)) {
            return MyResult.ok("");
        }
        String[] strings = auths.split(",");
        for (String auth : strings) {
            if(auth.equals("anno")) {
                return MyResult.ok("");
            } else if(auth.equals("user")) {
                if (token == null) {
                    return MyResult.noLogin();
                }
                if (JWTUtil.verify(token)) {
                    return MyResult.ok("");
                }
                return MyResult.noLogin();
            } else {
                if (token == null) {
                    return MyResult.noLogin();
                }
                String[] roles = JWTUtil.getUserRoles(token);
                for (String role : roles) {
                    if("admin".equals(role) || "superadmin".equals(role)) {
                        return MyResult.ok("");
                    }
                }
                return MyResult.noAuth();
            }
        }
        return MyResult.noAuth();
    }
}
