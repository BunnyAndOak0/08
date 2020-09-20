package edu.kmust.bully.common.security;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Author BunnyAndOak0
 * @Description
 * @Date 2020/8/19 15:18
 **/
@Data
@Accessors(chain=true)
public class R<T> {
    private Integer state;
    private String message;
    private T data;

    public static R ok() {
        return new R().setState(State.OK);
    }

    public static <T> R ok(T data) {
        return new R<T>().setState(State.OK).setData(data);
    }

    public static R ok(String msg){
        return new R().setState(State.OK).setMessage(msg);
    }

    public static R ok(String msg, Object obj){
        return new R().setState(State.OK).setMessage(msg).setData(obj);
    }

    public static R failure(Integer state, String message) {
        return new R().setState(state).setMessage(message);
    }

    public static R failure(Integer state, Throwable e) {
        return failure(state, e.getMessage());
    }

    public static R failure(String msg) {
        return new R().setMessage(msg);
    }

    public static interface State {
        int OK = 2000;

        int ERR_PARAMETER_INVALIDATION = 2001;
        int ERR_RIGESTER = 2002;
        int ERR_PHONE_DUPLICATE = 2003;

        int ERR_MODEL_NOT_FOUND = 3001;
        int ERR_COMMENT_NOT_FOUND = 3002;
        int ERR_PERMISSION_DENIED = 3003;
        int ERR_ACCESS_DENIED = 3004;

        int ERR_INSERT = 4001;
        int ERR_UPDATE = 4002;
        int ERR_DELETE = 4003;

        int ERR_UPLOAD_FILE_SIZE = 5001;
        int ERR_UPLOAD_FILE_TYPE = 5002;
        int ERR_UPLOAD_FILE_IO = 5003;
        int ERR_UPLOAD_EMPTY = 5004;

        int ERR_UNKNOWN = 9999;
    }

}
