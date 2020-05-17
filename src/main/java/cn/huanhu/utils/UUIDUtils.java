package cn.huanhu.utils;

import java.util.UUID;

/**
 * @author m
 * @className UUIDUtils
 * @description UUID
 * @date 2020/5/15
 */
public class UUIDUtils {

    public static String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
