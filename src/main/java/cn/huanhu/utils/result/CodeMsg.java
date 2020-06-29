package cn.huanhu.utils.result;

/**
 * @author m
 * @className CodeMsg
 * @description CodeMsg
 * @date 2020/5/11
 */
public class CodeMsg {
    private int code;
    private String msg;

    //通用异常
    public static CodeMsg SUCCESS = new CodeMsg(0, "success");

    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
    public static CodeMsg VALIDATION_ERROR = new CodeMsg(500101, "参数校验异常");
    public static CodeMsg REQUEST_ILLEGLE = new CodeMsg(500102, "请求异常");
    public static CodeMsg ACCESS_LIMIT_REACH = new CodeMsg(500103, "访问太频繁");
    //登录模块 5002XX
    public static CodeMsg SESSION_ERROR = new CodeMsg(500201, "session不存在或失效，请重新登录");
    public static CodeMsg PASSWPRD_EMPTY = new CodeMsg(500202, "密码不能为空");
    public static CodeMsg MOBILE_EMPTY = new CodeMsg(500203, "手机号不能为空");
    public static CodeMsg MOBILE_ERROR = new CodeMsg(500204, "请输入正确的手机号");
    public static CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500205, "用户不存在，请先注册");


    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500206, "密码错误");
    public static CodeMsg MoBILE_ALREADY_EXIST = new CodeMsg(500207,"用户已经存在");
    public static CodeMsg NEW_CODE_ERROR = new CodeMsg(500208,"验证码错误");
    public static CodeMsg REGISTER_ERROR = new CodeMsg(500209,"注册异常，请重新注册");
    public static CodeMsg SEND_MESSAGE_ERROR = new CodeMsg(5002010,"发送失败");
    //商品模块 5003XX

    //订单模块 5004XX
    public static CodeMsg ORDER_ERROR = new CodeMsg(500401, "订单号不能为空");
    public static CodeMsg ORDER_NOT_EXIT = new CodeMsg(500402, "订单不存在");

    //秒杀模块 5005XX
    public static CodeMsg MIAOSHA_OVER = new CodeMsg(500501, "库存不足");
    public static CodeMsg MIAOSHA_REPEAT = new CodeMsg(500502, "不能重复秒杀");
    public static CodeMsg MIAOSHA_FAIL = new CodeMsg(500503, "秒杀失败");
    public static CodeMsg IMAGE_ERROR = new CodeMsg(500504, "图片验证码不正确");

    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CodeMsg fillMsg(Object... args) {
        int code = this.code;
        String message = String.format(this.msg, args);
        return new CodeMsg(code,message);
    }

    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }

}
