package cn.huanhu.utils;


import java.security.SecureRandom;

/**
 * @author m
 * @className GenerateCode
 * @description GenerateCode
 * @date 2020/5/27
 */
public class GenerateCode {
    private static final String VERIFY_CODE = "0123456789";

    public static String produceVerCode(){
        char[] verCode =new char[6];
        SecureRandom secureRandom= new SecureRandom();
        for(int ini= 0; ini<verCode .length; ++ini){
            verCode [ini]=VERIFY_CODE.charAt(secureRandom.nextInt(VERIFY_CODE.length()));
        }
        return new String(verCode );
    }

    
}
