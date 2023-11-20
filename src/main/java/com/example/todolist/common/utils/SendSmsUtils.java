package com.example.todolist.common.utils;


import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.teaopenapi.models.Config;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class SendSmsUtils {
    public static Config config = new Config();

    //发送短信
    public static String createSms(String endpoint,String accessKeyId,String accessKeySecret,String phone) throws Exception{
        Random r=new Random();
        String sms=""+r.nextInt(1000, 9999);
            config
                //这里修改为自己的AccessKey ID
                .setAccessKeyId(accessKeyId)
                //这里修改为自己的AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = endpoint;
        Client client = new Client(config);
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setSignName("阿里云短信测试")//短信签名
                .setTemplateCode("SMS_154950909")//短信模板
                .setPhoneNumbers(phone)//这里填写接受短信的手机号码
                .setTemplateParam("{\"code\":\""+sms+"\"}");//验证码
        // 复制代码运行请自行打印 API 的返回值
        client.sendSms(sendSmsRequest);
        return sms;
    }

    /**
     * 任务超过12h提醒完成该任务
     * @param endpoint
     * @param accessKeyId
     * @param accessKeySecret
     * @param phone
     * @param msg
     * @return
     * @throws Exception
     */
    public static String taskNoSuccess(String endpoint,String accessKeyId,String accessKeySecret,String phone,String msg) throws Exception{
                config
                //这里修改为自己的AccessKey ID
                .setAccessKeyId(accessKeyId)
                //这里修改为自己的AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = endpoint;
        Client client = new Client(config);
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                //短信签名
                .setSignName("阿里云短信测试")
                //短信模板
                .setTemplateCode("SMS_154950909")
                //这里填写接受短信的手机号码
                .setPhoneNumbers(phone)
                //验证码
                .setTemplateParam("{\"code\":\""+msg+"\"}");
        // 复制代码运行请自行打印 API 的返回值
        client.sendSms(sendSmsRequest);
        return msg;
    }


    @Cacheable(value = "smsCode",key = "#tele")
    public  String getSms(String tele){
        return null;
    }


    @Cacheable(value = "smsByPassword",key = "#tele")
    public  String getSmsByPassword(String tele){
        return null;
    }
}
