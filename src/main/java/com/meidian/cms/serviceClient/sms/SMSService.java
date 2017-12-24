package com.meidian.cms.serviceClient.sms;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;

public interface SMSService {

    /**
     * 发送短信
     * @param signName
     * @param templateCode
     * @return
     */
    public SendSmsResponse sendSms(String signName, String templateCode,String phones,String handlerMobile)throws ClientException;
}
