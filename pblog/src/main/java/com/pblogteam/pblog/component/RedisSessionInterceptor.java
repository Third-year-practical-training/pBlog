package com.pblogteam.pblog.component;

import com.alibaba.druid.support.json.JSONUtils;
import com.fasterxml.jackson.databind.util.JSONPObject;
//import com.pblogteam.pblog.vo.ResultVO;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 登录检查, 在MyMvcConfig类中配置注册
 */

public class RedisSessionInterceptor implements HandlerInterceptor
{

    //@Autowired
    //StringRedisTemplate stringRedisTemplate;
    //最开始@Autowired不知为何注入为null，于是调用RedisBean工具类中的静态stringRedisTemplate对象
    StringRedisTemplate stringRedisTemplate = RedisBean.redis;

    //目标方法执行之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.setHeader("Access-Control-Allow-Credentials","true");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        HttpSession session = request.getSession();
        System.out.println("Interceptor: " + session.getId());
        Integer userId = (Integer)session.getAttribute("userId");
        System.out.println(session.getAttribute("userId"));
        if (userId != null)
        {
            String signedSessionId = stringRedisTemplate.opsForValue().get("User" + session.getAttribute("userId"));
            if (signedSessionId != null && signedSessionId.equals(session.getId()))
            {
                return true;
            }
        }
        response401(response);
        return false;
    }

    private void response401(HttpServletResponse response)
    {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        JSONObject result = new JSONObject();
        result.put("code", 401);
        result.put("msg", "用户未登录");
        try
        {
            response.getWriter().print(result);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


}
