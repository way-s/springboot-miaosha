package cn.huanhu.config.access;

import cn.huanhu.entity.MiaoshaUser;

/**
 * @author m
 * @className UserContext
 * @description UserContext
 * @date 2020/6/28
 */
public class UserContext {

    /**
     * 单线程绑定
     */
    private static ThreadLocal<MiaoshaUser> userThreadLocal = new ThreadLocal<>();

    public static MiaoshaUser getUser() {
        return userThreadLocal.get();
    }

    public static void setUser(MiaoshaUser user) {
        userThreadLocal.set(user);
    }
}
