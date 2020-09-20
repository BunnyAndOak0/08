package edu.kmust.bully.diary.config;

import edu.kmust.bully.common.security.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionsHandler {
    @ExceptionHandler
    public R exceptionHandler(Throwable e){
        switch(e.getClass()+""){
            case "class cn.tedu.bully.portal.exception.InsertException":
                return R.failure(R.State.ERR_INSERT,e);
            case "class cn.tedu.bully.portal.exception.DeleteException":
                System.err.println("发生了异常:"+e.getMessage());
                return R.failure(R.State.ERR_DELETE,e);
            case "class cn.tedu.bully.portal.exception.UpdateException":
                return R.failure(R.State.ERR_UPDATE,e);
            case "class cn.tedu.bully.portal.exception.FileSizeException":
                return R.failure(R.State.ERR_UPLOAD_FILE_SIZE,e);
            case "class cn.tedu.bully.portal.exception.FileTypeException":
                return R.failure(R.State.ERR_UPLOAD_FILE_TYPE,e);
            case "class cn.tedu.bully.portal.exception.FileUploadException":
                return R.failure(R.State.ERR_UPLOAD_FILE_IO,e);
            default:
                System.err.println("发生了异常:"+e.getMessage());
                return R.failure(R.State.ERR_UNKNOWN,"未知错误");
        }

    }
}
