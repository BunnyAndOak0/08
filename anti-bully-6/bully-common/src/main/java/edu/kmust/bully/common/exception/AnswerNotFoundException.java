package edu.kmust.bully.common.exception;

/**
 * @Author BunnyAndOak0
 * @Description
 * @Date 2020/8/25 19:58
 **/
public class AnswerNotFoundException extends ServiceException {
    public AnswerNotFoundException() {
        super();
    }

    public AnswerNotFoundException(String message) {
        super(message);
    }

    public AnswerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public AnswerNotFoundException(Throwable cause) {
        super(cause);
    }

    public AnswerNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
