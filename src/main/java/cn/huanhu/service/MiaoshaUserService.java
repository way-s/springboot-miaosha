package cn.huanhu.service;

import cn.huanhu.config.exception.GlobalException;
import cn.huanhu.config.redis.prefix.MiaoshaUserKey;
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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import static cn.huanhu.utils.GenerateCode.produceVerCode;

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

    @Resource
    private RedisService redisService;

    public MiaoshaUser getById(long id) {
        // 取缓存
        MiaoshaUser user = redisService.get(MiaoshaUserKey.getById,""+id,MiaoshaUser.class);
        if (user != null ){
            return user;
        }
        // 取数据库，并存入缓存
        user = miaoshaUserDao.getById(id);
        if (user != null ){
            redisService.set(MiaoshaUserKey.getById,""+id, user);
        }
        return user;
    }

    /**
     * 修改密码 更新数据库
     * @param token
     * @param id
     * @param passwordNew
     * @return
     * @throws GlobalException
     */
    public boolean updatePassword(String token,long id,String passwordNew) throws GlobalException {
        //取user
        MiaoshaUser user = getById(id);
        if(user == null){
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //更新的东西越多，产生的lock越多 修改哪个字段，就更新哪个字段
        MiaoshaUser toBeUpdate = new MiaoshaUser();
        toBeUpdate.setId(id);
        toBeUpdate.setPassword(Md5Utils.formPassToDBPass(passwordNew,user.getSalt()));
        int val = miaoshaUserDao.update(toBeUpdate);
        if(val > 0){
            //处理缓存 更新缓存
            redisService.delete(MiaoshaUserKey.getById,""+id);
            user.setPassword(toBeUpdate.getPassword());
            redisService.set(MiaoshaUserKey.token,token,user);
            return true;
        }
        return false;
    }

    /**
     * 查看缓存中用户是否已经登录
     *
     * @param response
     * @param token
     * @return MiaoshaUser
     */
    public MiaoshaUser getByToken(HttpServletResponse response, String token) {
        MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
        //延长有效期
        if (user != null) {
//            logger.info("getByTokenAddCookie");
            addCookie(response, token, user);
        }
        return user;
    }

    /**
     * 登手机号密码录
     *
     * @param response
     * @param loginVo  手机号 密码
     * @return token
     * @throws GlobalException 异常
     */
    public String doLogin(HttpServletResponse response, LoginVo loginVo) throws GlobalException {
        if (loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
        //判断手机号是否存在
        MiaoshaUser miaoshaUser = getById(Long.parseLong(mobile));
        if (miaoshaUser == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //验证密码
        String dbPass = miaoshaUser.getPassword();
        String saltDB = miaoshaUser.getSalt();
        String calcPass = Md5Utils.formPassToDBPass(formPass, saltDB);
        if (!calcPass.equals(dbPass)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        //生成cookie
        String token = UUIDUtils.uuid();
        addCookie(response, token, miaoshaUser);

        return token;
    }

    private void addCookie(HttpServletResponse response, String token, MiaoshaUser user) {
        redisService.set(MiaoshaUserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
//        logger.info("addCookie:->" + "token:" + token + "\t" + "cookie：" + cookie.getMaxAge());

    }

    /**
     * 登手机号密码录
     *
     * @param id 手机号
     * @param password 密码
     * @param nickname 昵称
     * @return token
     * @throws GlobalException 异常
     */
    public Object doRegister(String id,String password,String nickname) throws GlobalException {
        //判断手机号是否存在
        MiaoshaUser miaoshaUser = getById(Long.parseLong(id));
        if (miaoshaUser != null) {
            throw new GlobalException(CodeMsg.MoBILE_ALREADY_EXIST);
        }
        //验证密码
        String calcPass = Md5Utils.formPassToDBPass(password , Md5Utils.salt);
        //注册
        MiaoshaUser user = new MiaoshaUser();
        user.setId(Long.parseLong(id));
        user.setPassword(calcPass);
        user.setNickname(nickname);
        user.setSalt(Md5Utils.salt);
//        logger.info(user.toString());
       int index = miaoshaUserDao.insert(user);
       if(index > 0){
           return true;
       }else {
           throw  new GlobalException(CodeMsg.REGISTER_ERROR);
       }

    }

    /**
     * 发送验证码
     *
     * @param mobile 手机号
     * @return token
     * @throws GlobalException 异常
     */
    public Boolean doSendVerCode(String mobile) throws GlobalException {

        //获取用户信息
        MiaoshaUser miaoshaUser = getById(Long.parseLong(mobile));
        if (miaoshaUser == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //发送验证码
//        String vCode = sendMsg(mobile);
        String vCode = produceVerCode();
//        if("0".equals(vCode)){
//            throw new GlobalException(CodeMsg.SEND_MESSAGE_ERROR);
//        }
        //存放验证码
        redisService.set(MiaoshaUserKey.verCode, mobile, vCode);
        logger.info("verCode:" + vCode);
        return true;
    }

    /**
     * 验证码校验
     *
     * @param response
     * @param loginVo  手机号
     * @return token
     * @throws GlobalException 异常
     */
    public String doPhoneL(HttpServletResponse response, LoginVo loginVo) throws GlobalException {
        String mobile = loginVo.getMobile();
        //验证码
        String inputCode = loginVo.getPassword();
        logger.info(loginVo.toString());
        //获取用户信息
        MiaoshaUser miaoshaUser = getById(Long.parseLong(mobile));
        //校验
        String checkCode = redisService.get(MiaoshaUserKey.verCode, mobile, String.class);
        if (!inputCode.equals(checkCode)) {
            throw new GlobalException(CodeMsg.NEW_CODE_ERROR);
        }

        //生成cookie
        String token = UUIDUtils.uuid();
        addCookie(response, token, miaoshaUser);
        logger.info("user"+miaoshaUser.toString());
        //删除redis中的验证码
        redisService.delete(MiaoshaUserKey.verCode,mobile);
        logger.info("生成cookie" + "\n" + response.getStatus() + "\t" + token + "\t" + miaoshaUser.getNickname());
        return token;
    }

}
