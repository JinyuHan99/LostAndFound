package com.example.controller;

import com.example.exception.SystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProjectExceptionAdvice {
    @ExceptionHandler(Exception.class)
    public Result doException(Exception ex){
        System.out.println("error occurred");
        System.out.println(ex.toString());
        return new Result(5000,null,"遇到异常，请稍后再试");
    }
    @ExceptionHandler(SystemException.class)
    public Result doSystemException(SystemException ex){
        System.out.println("系统繁忙");
        return new Result(ex.getCode(),null,ex.getMessage());
    }
}
