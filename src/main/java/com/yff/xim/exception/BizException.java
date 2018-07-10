package com.yff.xim.exception;

public class BizException extends RuntimeException{
    private Integer code;
    public BizException(Integer code,String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
