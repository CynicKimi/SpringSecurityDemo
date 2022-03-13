package com.xxxx.filter;

import com.xxxx.handler.AuthFailureHandler;
import com.xxxx.handler.AuthSuccessHandler;
import com.xxxx.service.impl.UserDetailsServiceImpl;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MobileAuthenticationFilter extends OncePerRequestFilter {
    @Resource
    private UserDetailsServiceImpl userDetailsService;

    @Resource
    private AuthFailureHandler authFailureHandler;

    @Resource
    private AuthSuccessHandler authSuccessHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        AntPathRequestMatcher antPathRequestMatcher = new AntPathRequestMatcher("/mobileLogin", "POST");
        if(antPathRequestMatcher.matches(httpServletRequest)){
            String mobile = this.obtainMobile(httpServletRequest);
            if (mobile == null) {
                mobile = "";
            }

            mobile = mobile.trim();
            try {
                //手机号码数据库获取
                UserDetails userDetails = userDetailsService.loadUserByMobile(mobile);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                authSuccessHandler.onAuthenticationSuccess(httpServletRequest, httpServletResponse, authenticationToken);
            } catch (AuthenticationException e){
                authFailureHandler.onAuthenticationFailure(httpServletRequest,httpServletResponse,e);
                return;
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    @Nullable
    protected String obtainMobile(HttpServletRequest request) {
        String mobileParameter = "mobile";
        return request.getParameter(mobileParameter);
    }
}
