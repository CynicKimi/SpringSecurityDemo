package com.xxxx.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxxx.pojo.RespBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setCharacterEncoding("UTF-8");
        //设置内容类型为UTF-8
        httpServletResponse.setContentType("application/json;charset=utf-8");
        //获得打印流对象
        PrintWriter out = httpServletResponse.getWriter();
        //此打印流对象可向浏览器输出信息

        RespBean respBean = RespBean.error("尚未登录,禁止访问");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.valueToTree(respBean);

        out.write(jsonNode.toString());
        //清理打印流
        out.flush();
    }
}
