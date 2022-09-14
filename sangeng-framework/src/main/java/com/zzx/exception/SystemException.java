package com.zzx.exception;

import com.zzx.enums.AppHttpCodeEnum;

/**
 * @program: SGBlog
 * @Description 自定义系统异常
 * @Author: 那个小楠瓜
 * @create: 2022-02-25 13:13
 **/
public class SystemException extends RuntimeException{

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.message = httpCodeEnum.getMsg();
    }

    public SystemException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
