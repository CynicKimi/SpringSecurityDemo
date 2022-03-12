package com.xxxx.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxxx.pojo.LoginUser;
import com.xxxx.pojo.RespBean;
import com.xxxx.utils.JwtTokenUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler {
    @Resource
    private JwtTokenUtils jwtTokenUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String jwtToken = jwtTokenUtils.generateToken(loginUser);
        HashMap<String, String> map = new HashMap<>();
        map.put("jwt_token", jwtToken);

        httpServletResponse.setCharacterEncoding("UTF-8");
        //设置内容类型为UTF-8
        httpServletResponse.setContentType("application/json;charset=utf-8");
        //获得打印流对象
        PrintWriter out = httpServletResponse.getWriter();
        //此打印流对象可向浏览器输出信息

        RespBean respBean = RespBean.success("登录成功", map);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.valueToTree(respBean);

        out.write(jsonNode.toString());
        //清理打印流
        out.flush();
    }
}
