package com.yff.xim.exception;

import com.yff.xim.model.Result;
import com.yff.xim.util.GlobalConstant;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {


    @ExceptionHandler(value = {BizException.class})
    public Result<Object> bizExceptionHandle(HttpServletRequest req, BizException ex) {
        Result<Object> result = new Result<>();
        ex.printStackTrace();
        result.setCode(ex.getCode());
        result.setMessage(ex.getMessage());
        return result;
    }
}
