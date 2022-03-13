package com.xxxx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xxxx.exception.SecurityException;
import com.xxxx.mapper.UserMapper;
import com.xxxx.pojo.LoginUser;
import com.xxxx.pojo.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername:" + username);
        //查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, username);

        User user = userMapper.selectOne(queryWrapper);
        if(Objects.isNull(user)){
            throw new RuntimeException("用户名或密码错误");
        }

        //todo:权限设置
        ArrayList<String> authorityList = new ArrayList<>(Arrays.asList("test", "admin"));

        //把数据封装成UserDetails对象返回
        return new LoginUser(user, authorityList);
    }

    public UserDetails loadUserByMobile(String mobile){
        System.out.println("loadUserByMobile:" + mobile);
        //查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getMobile, mobile);

        User user = userMapper.selectOne(queryWrapper);
        if(Objects.isNull(user)){
            throw new SecurityException("手机号不存在");
        }

        //todo:权限设置
        ArrayList<String> authorityList = new ArrayList<>(Arrays.asList("test", "admin"));

        //把数据封装成UserDetails对象返回
        return new LoginUser(user, authorityList);
    }
}
