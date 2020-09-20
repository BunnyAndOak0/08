package edu.kmust.bully.diary.vo;


import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class R {
    private Integer state;
    private String message;
    private Object obj;

    public static R ok(String msg){
        return new R().setState(State.SUCCESS).setMessage(msg);
    }

    public static R ok(String msg, Object obj){
        return new R().setState(State.SUCCESS).setMessage(msg).setObj(obj);
    }

    public static R ok(Object obj){
        return new R().setObj(obj);
    }

    public static R error(Integer state,Throwable e){
        return new R().setState(state).setMessage(e.getMessage());
    }

    public static R error(Integer state,String msg){
        return new R().setState(state).setMessage(msg);
    }

    public static interface State{
        int SUCCESS = 2000;
        int ERR_INSERT = 4001;
        int ERR_UPDATE = 4002;
        int ERR_DELETE = 4003;
        int ERR_FILESIZE = 5001;
        int ERR_FILETYPE = 5002;
        int ERR_FILEUPLOAD = 5003;




        int ERR_UNKOWN = 9999;
    }


}
