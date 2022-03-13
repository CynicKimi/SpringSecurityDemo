package com.xxxx.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 自定义SpringSecurity认证异常
 */
public class SecurityException extends AuthenticationException{
    public SecurityException(String msg, Throwable t) {
        super(msg, t);
    }

    public SecurityException(String msg) {
        super(msg);
    }
}
