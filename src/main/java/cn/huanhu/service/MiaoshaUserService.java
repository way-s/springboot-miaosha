package cn.huanhu.service;

import cn.huanhu.config.exception.GlobalException;
import cn.huanhu.dao.MiaoshaUserDao;
import cn.huanhu.entity.MiaoshaUser;
import cn.huanhu.entity.vo.LoginVo;
import cn.huanhu.utils.Md5Utils;
import cn.huanhu.utils.UUIDUtils;
import cn.huanhu.utils.result.CodeMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(MiaoshaUserService.class);

    public static final String COOKIE_NAME_TOKEN = "token";

    @Resource
    private MiaoshaUserDao miaoshaUserDao;

//    @Resource
//    private RedisService redisService;

    public MiaoshaUser getById(long id){
        logger.info("id = "+id);
        return miaoshaUserDao.getById(id);
    }

    /**
     *
     *修改 boolean -> Object
     */
    public Object doLogin(LoginVo loginVo){
        if(loginVo == null){
            return new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        //判断手机号是否存在
        MiaoshaUser miaoshaUser = getById(Long.parseLong(mobile));
        if(miaoshaUser == null){
            return new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //验证密码
        String dbPass = miaoshaUser.getPassword();
        String saltDB=miaoshaUser.getSalt();
        String calcPass = Md5Utils.formPassToDBPass(formPass,saltDB);
        if(!calcPass.equals(dbPass)){
            return new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        //生成cookie
        String token = UUIDUtils.uuid();
//        redisService.set();
        return true;
    }
}
