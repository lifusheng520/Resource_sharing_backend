package com.sharing.exception;

import com.sharing.Utils.ResponseCode;
import com.sharing.Utils.ResultFormatUtil;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

/**
 * @author 李福生
 * @date 2022-2-25
 * @time 下午 03:10
 */

//@Log4j
//@RestControllerAdvice
public class GlobalExceptionHandler {

    // 设置响应状态码
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    // 接收处理运行时异常
    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public String handler(RuntimeException e) {
//        将异常输出到日志中
//        log.error("运行时异常：====>{}", e);
        return ResultFormatUtil.format(ResponseCode.EXCEPTION_RUNTIME, e.getMessage());
    }

    // 设置响应状态码
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    // 接收处理运行时异常
    @ExceptionHandler(value = IOException.class)
    @ResponseBody
    public String handler(IOException e) {
//        将异常输出到日志中
//        log.error("运行时异常：====>{}", e);
        return ResultFormatUtil.format(ResponseCode.EXCEPTION_IO, e.getMessage());
    }

}
