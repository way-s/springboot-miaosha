package cn.huanhu.config.exception;

import cn.huanhu.utils.result.CodeMsg;
import cn.huanhu.utils.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author m
 * @className ExceptionHandler
 * @description ExceptionHandler
 * @date 2020/5/12
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest request, Exception e) {
//        e.printStackTrace();
        logger.info(e.getMessage());
        if (e instanceof GlobalException) {
            GlobalException exception = (GlobalException) e;
            return Result.error(exception.getCm());
        } else if (e instanceof BindException) {
            BindException bindException = (BindException) e;
            List<ObjectError> errors = bindException.getAllErrors();
            ObjectError error = errors.get(0);
            String message = error.getDefaultMessage();
            return Result.error(CodeMsg.VALIDATION_ERROR.fillMsg(message));
        } else {
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }

}
