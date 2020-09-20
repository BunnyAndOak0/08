package edu.kmust.bully.user.controller;

import edu.kmust.bully.common.exception.*;
import edu.kmust.bully.common.security.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author BunnyAndOak0
 * @Description
 * @Date 2020/8/14 2:33
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public R handleException(Throwable e) {
        if (e instanceof ParameterValidationException) {
            return R.failure(R.State.ERR_PARAMETER_INVALIDATION, e);
        } else if (e instanceof PhoneDuplicateException) {
            return R.failure(R.State.ERR_PHONE_DUPLICATE, e);
        } else if (e instanceof InsertException) {
            return R.failure(R.State.ERR_INSERT, e);
        } else {
            return R.failure(R.State.ERR_UNKNOWN, e);
        }
    }
}
