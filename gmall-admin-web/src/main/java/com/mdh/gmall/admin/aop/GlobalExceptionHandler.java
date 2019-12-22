package com.mdh.gmall.admin.aop;

import com.mdh.gmall.to.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一处理所有异常
 *
 * @author madonghao
 * @create 2019-12-22 17:39
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 数学运算异常
    @ExceptionHandler(value = {ArithmeticException.class})
    public Object handleArithmeticException(Exception exception){
        log.error("数学运算异常:{}", exception.getStackTrace());
        return new CommonResult().validateFailed("数学运算异常！");
    }

    // 数学运算异常
    @ExceptionHandler(value = {NullPointerException.class})
    public Object handleNullPointeException(Exception exception){
        log.error("空指针操作异常:{}", exception.getStackTrace());
        return new CommonResult().validateFailed("空指针操作！");
    }
}
