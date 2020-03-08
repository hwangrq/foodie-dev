package com.yellowrq.enums;

/**
 * ClassName:PayMethod
 * Package:com.yellowrq.enums
 * Description:
 *  支付方式-枚举
 * @author:YellowRQ
 * @data:2020/3/8 21:32
 */
public enum PayMethod {

    WEIXIN(1,"微信"),
    ALIPAY(2,"支付宝");

    public final Integer type;
    public final String value;

    PayMethod(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
