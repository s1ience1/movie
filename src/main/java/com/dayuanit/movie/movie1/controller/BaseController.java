package com.dayuanit.movie.movie1.controller;

import com.dayuanit.movie.movie1.dto.ResponseDto;
import com.dayuanit.movie.movie1.exception.BizException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public abstract class BaseController {

    protected Logger logger = LoggerFactory.getLogger(BaseController.class);

    @ExceptionHandler(BizException.class)
    @ResponseBody
    public ResponseDto processBizException(BizException bz) {
        logger.error(bz.getMessage(),bz);
        return ResponseDto.fail(bz.getMessage());
    }


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseDto handleException(Exception e) {
        logger.error(e.getMessage(),e);
        return ResponseDto.fail("请联系客服");
    }
}
