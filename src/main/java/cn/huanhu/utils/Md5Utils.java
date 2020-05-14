package cn.huanhu.utils;


import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author m
 * @className Md5Utils
 * @description Md5Utils
 * @date 2020/5/14
 */
public class Md5Utils {

    public static final String salt = "1a2b3c4d" ;

    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    /**
     * 第一层MD5，将输入密码转化为表单提交的密码
     * @param inputPass 用户输入的密码
     * @return
     */
    public static String inputPassToFormpass(String inputPass){
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        String result = md5(str);
        return result;
    }

    /**
     * 第二层加密，将表单密码转化为数据库密码
     * @param formPass
     * @param salt
     * @return
     */
    public static String formpassToDBpass(String formPass,String salt){
        String str = "" + salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        String result = md5(str);
        return result;
    }

    /**
     * 两次加密
     * @param inputPass
     * @param salt
     * @return
     */
    public static String inputpassToDBpass(String inputPass ,String salt){
        String formPass = inputPassToFormpass(inputPass);
        String dbPass = formpassToDBpass(formPass, salt);
        return dbPass;
    }

    public static void main(String[] args) {
        System.out.println(inputpassToDBpass("123456", salt));
    }
}
