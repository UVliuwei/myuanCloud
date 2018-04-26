package com.myuan.zuul.filter;
/*
 * @author liuwei
 * @date 2018/3/28 19:01
 *
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.myuan.zuul.entity.MyResult;
import com.myuan.zuul.utils.AuthUtil;
import com.myuan.zuul.utils.JWTUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

@Component
@Log4j
public class TokenFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();
        String token = request.getHeader("token");
        //是否有管理员角色
        if("/api/user/roles".equals(request.getRequestURI())) {
            if(checkRoleUrl(request, token)) {
                return MyResult.ok("true");
            }
            return MyResult.ok("false");
        }
        MyResult result = AuthUtil.checkAuth(request, token);
        if("1".equals(result.getStatus())) {
            ctx.setRequest(request);
            ctx.setSendZuulResponse(true); //进行路由
        } else {
            try {
                response.setContentType("text/html;charset=UTF-8");
                PrintWriter writer = response.getWriter();
                writer.append(JSON.toJSONString(result));
                ctx.setResponse(response);
                ctx.setSendZuulResponse(false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static boolean checkRoleUrl(HttpServletRequest request, String token) {

        if(JWTUtil.verify(token)) {
            String[] userRoles = JWTUtil.getUserRoles(token);
            for (String role : userRoles) {
                if("admin".equals(role) ||"superadmin".equals(role)) {
                    return true;
                }
            }
        }
        return false;
    }
}
