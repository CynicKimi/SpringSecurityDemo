package com.xxxx.config;

import com.xxxx.filter.JwtAuthenticationTokenFilter;
import com.xxxx.filter.MobileAuthenticationFilter;
import com.xxxx.handler.AuthAccessDeniedHandler;
import com.xxxx.handler.AuthLogoutSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
//@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Resource
    private MobileAuthenticationFilter mobileAuthenticationFilter;

    @Resource
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Resource
    private AuthenticationFailureHandler authenticationFailureHandler;

    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Resource
    private AuthAccessDeniedHandler authAccessDeniedHandler;

    @Resource
    private AuthLogoutSuccessHandler authLogoutSuccessHandler;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**", "/css/**","/images/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated();
        //??????csrf
        http.csrf().disable();
        //??????????????????
        http.cors();
        //??????????????????session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.formLogin().permitAll()
                .successHandler(authenticationSuccessHandler)//????????????????????????
                .failureHandler(authenticationFailureHandler);//????????????????????????

        //????????????????????????????????????
        http.exceptionHandling()
                .accessDeniedHandler(authAccessDeniedHandler)//??????????????????????????????
                .authenticationEntryPoint(authenticationEntryPoint);//???????????????????????????

        //??????
        http.logout().permitAll()
                .logoutSuccessHandler(authLogoutSuccessHandler);

        //JWTtoken???????????????
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        //??????????????????????????????
        http.addFilterBefore(mobileAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
}
