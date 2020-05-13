package cn.huanhu.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author m
 * @className ExceptionHandler
 * @description ExceptionHandler
 */
@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public String runTimeException(RuntimeException run) {
        run.printStackTrace();
        return run.toString();
    }

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public String exceptionHandler(Exception e) {
        e.printStackTrace();
        return e.toString();
    }


}
