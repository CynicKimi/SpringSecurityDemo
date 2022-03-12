package com.xxxx.controller;

import com.xxxx.pojo.RespBean;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotFund implements ErrorController {
    private static final String NOT_FOUND = "404";
    private static final String ERROR_PATH = "/error";

    @RequestMapping(value = ERROR_PATH)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public RespBean handleError() {
        return RespBean.error(NOT_FOUND);
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
