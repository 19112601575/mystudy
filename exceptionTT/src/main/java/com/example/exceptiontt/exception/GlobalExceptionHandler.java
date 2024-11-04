package com.example.exceptiontt.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    //    @ExceptionHandler(value = {Exception.class})
//    @ResponseBody
//    public <T> Resp<T> exceptionHandler(Exception e){
//        //这里先判断拦截到的Exception是不是我们自定义的异常类型
//        if(e instanceof AppException){
//            AppException appException = (AppException)e;
//            return Resp.error(appException.getCode(),appException.getMsg());
//        }
//
//        //如果拦截的异常不是我们自定义的异常(例如：数据库主键冲突)
//        return Resp.error(500,e.getMessage());
//    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public <T> Resp<T> exceptionHandler(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
//            String fieldName = ((FieldError) error).getField();
            String fieldName = "detailMsg";
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        log.error("Validation failed: {}", errors);
        return  Resp.<Map<String, String>>error(22222, "Validation failed").addData(errors);
    }
}