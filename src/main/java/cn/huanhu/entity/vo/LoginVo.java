package cn.huanhu.entity.vo;

import cn.huanhu.config.validate.IsMobile;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author m
 * @className LoginVo
 * @description LoginVo
 * @date 2020/5/13
 */
@Data
public class LoginVo implements Serializable {

    @NotNull
    @IsMobile
    private String mobile;

    @NotNull
    @Length
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
