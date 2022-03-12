package com.xxxx.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxxx.pojo.RespBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AuthLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setCharacterEncoding("UTF-8");
        //设置内容类型为UTF-8
        httpServletResponse.setContentType("application/json;charset=utf-8");
        //获得打印流对象
        PrintWriter out = httpServletResponse.getWriter();
        //此打印流对象可向浏览器输出信息

        RespBean respBean = RespBean.success("注销登录成功");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.valueToTree(respBean);

        out.write(jsonNode.toString());
        //清理打印流
        out.flush();
    }
}
