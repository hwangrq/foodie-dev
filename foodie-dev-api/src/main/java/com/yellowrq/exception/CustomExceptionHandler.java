package com.yellowrq.exception;

import com.yellowrq.utils.JSONResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * ClassName:CustomExceptionHandler
 * Package:com.yellowrq.exception
 * Description:
 *
 * @author:yellowrq
 * @date: 2020/3/11 16:49
 */
@RestControllerAdvice
public class CustomExceptionHandler {

    /**
     * 上传文件超过500k，捕获异常
     * @param ex
     * @return
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public JSONResult handlerMaxUploadFile(MaxUploadSizeExceededException ex) {
        return JSONResult.errorMsg("文件大小不能超过500k,请压缩图片,或降低图片质量再上传！");
    }
}
