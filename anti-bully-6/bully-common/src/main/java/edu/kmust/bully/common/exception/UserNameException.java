package edu.kmust.bully.common.exception;

/**
 * @Author BunnyAndOak0
 * @Description
 * @Date 2020/8/14 11:50
 **/
public class UserNameException extends ServiceException {
    public UserNameException() {
        super();
    }

    public UserNameException(String message) {
        super(message);
    }

    public UserNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNameException(Throwable cause) {
        super(cause);
    }

    public UserNameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
