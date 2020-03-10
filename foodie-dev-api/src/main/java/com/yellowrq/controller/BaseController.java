package com.yellowrq.controller;

import org.springframework.stereotype.Controller;

/**
 * ClassName:BaseController
 * Package:com.yellowrq.controller
 * Description:
 *
 * @author:yellowrq
 * @date: 2020/2/27 17:11
 */
@Controller
public class BaseController {

    public static final String FOODIE_SHOPCART = "shopcart";

    public static final Integer COMMENT_PAGE_SIZE = 10;
    public static final Integer PAGE_SIZE = 20;

    //支付中心的调用地址

    String paymentServerUrl = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";

    //微信支付成功 -->支付中心 --> 本电商平台
    //                       |-->回调通知的url
//    String payReturnUrl = "http://localhost:8088/orders/notifyMerchantOrderPaid";
    String payReturnUrl = "http://qm7is2.natappfree.cc/orders/notifyMerchantOrderPaid";

}
