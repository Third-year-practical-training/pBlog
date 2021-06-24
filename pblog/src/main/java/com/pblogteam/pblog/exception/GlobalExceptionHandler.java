package com.pblogteam.pblog.exception;


import com.pblogteam.pblog.constant.ResponseState;
import com.pblogteam.pblog.vo.ResultVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import javax.servlet.http.HttpServletRequest;



/**
 * 全局异常类，处理自定义异常
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    // 可以添加服务器日志
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BlogException.class)
    public ResultVO blogExceptionHandler(HttpServletRequest request, BlogException e) {
            // return 自定义的信息和状态码，throw抛出来的
            return ResultVO.throwError(e.getCode(), e.getMsg());
    }

    // 未知错误
    @ExceptionHandler(Exception.class)
    public ResultVO globalExceptionHandler(HttpServletRequest request, Exception e) {
        return ResultVO.throwError(ResponseState.UNKNOWN_ERROR);
    }
}
