package com.lfy.mallproduct.product.exception;/*
 *@Author:user
 *@Date:7/10/2023 10:26 PM
 */
/**
 * 集中处理所有异常
 */

import com.lfy.common.exception.BizCodeEnume;
import com.lfy.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@Slf4j
//@ResponseBody
//@ControllerAdvice(basePackages = "com.lfy.mallproduct.product.controller")
@RestControllerAdvice(basePackages = "com.lfy.mallproduct.product.controller")
public class MallExceptionControllerAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleValidException(MethodArgumentNotValidException e) {
        log.error("数据校验出现问题{}，异常类型:{}", e.getMessage(), e.getClass());
        HashMap<String, String> map = new HashMap<>();
        BindingResult bindingResult = e.getBindingResult();
        bindingResult.getFieldErrors().forEach((item) -> {
            map.put(item.getField(), item.getDefaultMessage());
        });
        return R.error(BizCodeEnume.VAILD_EXCEPTION.getCode(),BizCodeEnume.VAILD_EXCEPTION.getMsg()).put("data", map);
    }

    @ExceptionHandler(value = Throwable.class)
    public R handleException(Throwable throwable){
        log.error("未知错误",throwable);
        return R.error(BizCodeEnume.UNKNOW_EXCEPTION.getCode(), BizCodeEnume.UNKNOW_EXCEPTION.getMsg());
    }
}
