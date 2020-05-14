package cn.huanhu.entity.vo;

import lombok.Data;

/**
 * @author m
 * @className LoginVo
 * @description LoginVo
 * @date 2020/5/13
 */
@Data
public class LoginVo {

    private String mobile;

    private String password;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
