package edu.kmust.bully.publish.controller;

import edu.kmust.bully.common.exception.*;
import edu.kmust.bully.common.security.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public R handleException(Throwable e) {
        if (e instanceof InsertException) {
            return R.failure(R.State.ERR_INSERT, e);
        } else if (e instanceof FileEmptyException) {
            return R.failure(R.State.ERR_UPLOAD_EMPTY, e);
        } else if (e instanceof FileSizeException) {
            return R.failure(R.State.ERR_UPLOAD_FILE_SIZE, e);
        } else if (e instanceof FileTypeException) {
            return R.failure(R.State.ERR_UPLOAD_FILE_TYPE, e);
        } else if (e instanceof FileIOException) {
            return R.failure(R.State.ERR_UPLOAD_FILE_IO, e);
        } else if (e instanceof ModelNotFoundException) {
            return R.failure(R.State.ERR_MODEL_NOT_FOUND, e);
        } else if (e instanceof CommentNotFoundException) {
            return R.failure(R.State.ERR_COMMENT_NOT_FOUND, e);
        } else if (e instanceof PermissionDeniedException) {
            return R.failure(R.State.ERR_PERMISSION_DENIED, e);
        } else if (e instanceof DeleteException) {
            return R.failure(R.State.ERR_DELETE, e);
        } else if (e instanceof AccessDeniedException) {
            return R.failure(R.State.ERR_ACCESS_DENIED, e);
        } else {
            log.debug("Unknown Exception", e);
            return R.failure(R.State.ERR_UNKNOWN, e);
        }
    }
}
