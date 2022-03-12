package com.xxxx.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespBean {
    private  long code;
    private String msg;
    private Object result;

    /**
     * 成功返回结果
     * @param message
     * @return
     */
    public static RespBean success(String message){
        return new RespBean(200, message, null);
    }

    public static RespBean success(String message, Object result){
        return new RespBean(200, message, result);
    }

    /**
     * 失败返回结果
     * @param message
     * @return
     */
    public static RespBean error(String message){
        return new RespBean(500, message, null);
    }

    public static RespBean error(String message, Object result){
        return new RespBean(500, message, result);
    }
}
