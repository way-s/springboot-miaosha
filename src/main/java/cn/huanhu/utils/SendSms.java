package cn.huanhu.utils;


import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import java.util.logging.Logger;

import static cn.huanhu.utils.GenerateCode.produceVerCode;

/**
 * @author m
 * @className SendSms
 * @description SMS
 * @date 2020/5/9
 */
public class SendSms {

    private static Logger logger = Logger.getLogger(String.valueOf(SendSms.class));

    public static String sendMsg(String phoneCode) {
        String codeId = produceVerCode();
        String accessKeyId = "xxxxxxxxxxxxxxxx";
        String accessSecret = "xxxxxxxxxxxxxxxx";

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneCode);
        request.putQueryParameter("SignName", "乐gou商城");
        request.putQueryParameter("TemplateCode", "SMS_191485280");
        request.putQueryParameter("TemplateParam", "{code:" + codeId + "}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            logger.info(response.getData());

        } catch (ClientException e) {
            e.printStackTrace();
            return "0";
        }
        return codeId;
    }
}
