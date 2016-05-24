package util;


import util.tools.MagCore;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mazhenhua on 2016/5/24.
 */
public class MagClient{

    // 加签方式
    public static final String signType = "RSA";

    // 商户私钥
    public static final String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAO/6rPCvyCC+IMalLzTy3cVBz/+wamCFNiq9qKEilEBDTttP7Rd/GAS51lsfCrsISbg5td/w25+wulDfuMbjjlW9Afh0p7Jscmbo1skqIOIUPYfVQEL687B0EmJufMlljfu52b2efVAyWZF9QBG1vx/AJz1EVyfskMaYVqPiTesZAgMBAAECgYEAtVnkk0bjoArOTg/KquLWQRlJDFrPKP3CP25wHsU4749t6kJuU5FSH1Ao81d0Dn9m5neGQCOOdRFi23cV9gdFKYMhwPE6+nTAloxI3vb8K9NNMe0zcFksva9c9bUaMGH2p40szMoOpO6TrSHO9Hx4GJ6UfsUUqkFFlN76XprwE+ECQQD9rXwfbr9GKh9QMNvnwo9xxyVl4kI88iq0X6G4qVXo1Tv6/DBDJNkX1mbXKFYL5NOW1waZzR+Z/XcKWAmUT8J9AkEA8i0WT/ieNsF3IuFvrIYG4WUadbUqObcYP4Y7Vt836zggRbu0qvYiqAv92Leruaq3ZN1khxp6gZKl/OJHXc5xzQJACqr1AU1i9cxnrLOhS8m+xoYdaH9vUajNavBqmJ1mY3g0IYXhcbFm/72gbYPgundQ/pLkUCt0HMGv89tn67i+8QJBALV6UgkVnsIbkkKCOyRGv2syT3S7kOv1J+eamGcOGSJcSdrXwZiHoArcCZrYcIhOxOWB/m47ymfE1Dw/+QjzxlUCQCmnGFUO9zN862mKYjEkjDN65n1IUB9Fmc1msHkIZAQaQknmxmCIOHC75u4W0PGRyVzq8KkxpNBq62ICl7xmsPM=";

    // 字符编码方式
    public static final String inputCharset = "utf-8";

    // 网关地址
    public static final String gatewayUrl = "http://func2.vfinance.cn/mag/gateway/receiveOrder.do";

    public static void main(String[] args) {

        // 请求参数
        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("signType","RSA");
        sParaTemp.put("paramNames","service,version,partner_id,_input_charset,return_url,memo,request_no,trade_list,operator_id,buyer_id,buyer_id_type,buyer_mobile,buyer_ip,pay_method,go_cashier,is_web_access,is_anonymous,pay_channel_extension,identityType2");
        sParaTemp.put("service","create_instant_trade");
        sParaTemp.put("version","1.0");
        sParaTemp.put("partner_id","188888888888");
        sParaTemp.put("_input_charset","UTF-8");
        sParaTemp.put("return_url","http://func2admin.vfinance.cn/tpu/mag/syncNotify.do");
        sParaTemp.put("request_no","2016052421411589419518478273");
        sParaTemp.put("trade_list","28:2016052421411361333048070294~5:东风雪铁龙~4:0.10~1:2~4:0.20~0:~10:9187437612~3:UID~11:13855462942~13:爱丽舍-三厢 1.6 MT~35:http://www.test.com/?product-9.html~14:20140526090530~51:http://func2admin.vfinance.cn/tpu/mag/asynNotify.do~2:7d~7:上海大众4S店");
        sParaTemp.put("operator_id","10005454");
        sParaTemp.put("buyer_id","9187437611");
        sParaTemp.put("buyer_id_type","UID");
        sParaTemp.put("buyer_mobile","13812345678");
        sParaTemp.put("buyer_ip","102.122.12.45");
        sParaTemp.put("pay_method","11:online_bank^4:0.20^13:TESTBANK,C,DC");
        sParaTemp.put("go_cashier","Y");
        sParaTemp.put("is_web_access","Y");
        sParaTemp.put("is_anonymous","N");
        sParaTemp.put("pay_channel_extension","{'name':'zhangsan'}");
        sParaTemp.put("identityType2","UID");

        String result = null;
        try {
            // 发起http请求
            result = MagCore.buildRequest(sParaTemp, signType, privateKey, inputCharset, gatewayUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(result);
    }

}
