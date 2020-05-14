package cn.huanhu.service.impl;

import cn.huanhu.dao.MiaoshaUserDao;
import cn.huanhu.entity.MiaoshaUser;
import cn.huanhu.entity.vo.LoginVo;
import cn.huanhu.utils.Md5Utils;
import cn.huanhu.utils.result.CodeMsg;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author m
 * @className MiaoShaUserService
 * @description MiaoShaUserService
 * @date 2020/5/13
 */
@Service
public class MiaoshaUserService {

    @Resource
    private MiaoshaUserDao miaoshaUserDao;

    public MiaoshaUser getById(long id){
        return miaoshaUserDao.getById(id);
    }

    public CodeMsg login(LoginVo loginVo){
        if(loginVo == null){
            return CodeMsg.SERVER_ERROR;
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        //判断手机号是否存在
        MiaoshaUser miaoshaUser = getById(Long.parseLong(mobile));
        if(miaoshaUser == null){
            return CodeMsg.MOBILE_NOT_EXIST;
        }
        //验证密码
        String dbPass = miaoshaUser.getPassword();
        String saltDB=miaoshaUser.getSalt();
        String calcPass = Md5Utils.formpassToDBpass(formPass,saltDB);
        if(!calcPass.equals(dbPass)){
            return CodeMsg.PASSWORD_ERROR;
        }
        return CodeMsg.SUCCESS;
    }
}
