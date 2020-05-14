package cn.huanhu.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author m
 * @className MiaoshaUser
 * @description MiaoshaUser
 * @date 2020/5/13
 */

@Data
public class MiaoshaUser {

    private long mobile;
    private String nickname;
    private String password;
    private String salt;
    private String head;
    private Date registerDate;
    private Date lastLoginDate;
    private Integer loginCount;
}
