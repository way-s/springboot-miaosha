package cn.huanhu.config.exception;

import cn.huanhu.utils.result.CodeMsg;

/**
 * @author m
 * @className GlobalException
 * @description GlobalException 全局异常
 * @date 2020/5/15
 */
public class GlobalException extends Exception{

    public static final long serialVersionUID = 1L;

    private CodeMsg cm;

    public GlobalException(CodeMsg cm){
        super(cm.toString());
        this.cm = cm ;
    }

    public CodeMsg getCm() {
        return cm;
    }
}
