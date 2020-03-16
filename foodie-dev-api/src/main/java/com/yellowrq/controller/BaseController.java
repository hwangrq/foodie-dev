package com.yellowrq.controller;

import com.yellowrq.pojo.Orders;
import com.yellowrq.service.center.MyOrdersService;
import com.yellowrq.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;

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

    public static final Integer COMMON_PAGE_SIZE = 10;
    public static final Integer PAGE_SIZE = 20;

    @Autowired
    public MyOrdersService myOrdersService;

    //支付中心的调用地址

    String paymentServerUrl = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";

    //微信支付成功 -->支付中心 --> 本电商平台
    //                       |-->回调通知的url
//    String payReturnUrl = "http://localhost:8088/orders/notifyMerchantOrderPaid";
    String payReturnUrl = "http://e6xp2h.natappfree.cc/orders/notifyMerchantOrderPaid";

    //用户上传头像的位置
    public static final String IMAGE_USER_FACE_LOCATION = "D:"
                                                        + File.separator +"workspaces"
                                                        + File.separator +"image"
                                                        + File.separator +"foodie"
                                                        + File.separator +"faces";


    /**
     * 用于验证用户和订单是否有关联关系，避免非法用户调用
     * @return
     */
    public JSONResult checkUserOrder(String userId, String orderId) {
        Orders order = myOrdersService.queryMyOrder(userId, orderId);
        if (order == null) {
            return JSONResult.errorMsg("订单不存在！");
        }
        return JSONResult.ok(order);
    }
}
