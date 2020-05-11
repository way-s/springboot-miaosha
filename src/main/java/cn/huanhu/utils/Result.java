package cn.huanhu.utils;

/**
 * @author m
 * @className Result
 * @description result
 * @date 2020/5/11
 */
public class Result<T> {
    private int code;
    private String msg;
    private Object data;


    /**
     *  成功时候的调用
     * */
//    public static  <T> Result<T> success(SuccessMsg successMsg){
//        return new Result<T>(successMsg);
//    }

    /**
     *  失败时候的调用
     * */
//    public static  <T> Result<T> error(CodeMsg codeMsg){
//        return new Result<T>(codeMsg);
//    }

    private Result(T data) {
        this.data = data;
    }

    private Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

//    private Result(CodeMsg codeMsg) {
//        if(codeMsg != null) {
//            this.code = codeMsg.getCode();
//            this.msg = codeMsg.getMsg();
//            this.data = "发生异常，请查看异常信息";
//        }
//    }

//    private Result(SuccessMsg successMsg) {
//        if(successMsg != null) {
//            this.code = successMsg.getCode();
//            this.msg = successMsg.getMsg();
//            this.data=successMsg.getObject();
//        }
//    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
