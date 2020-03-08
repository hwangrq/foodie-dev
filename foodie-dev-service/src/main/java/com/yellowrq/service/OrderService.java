package com.yellowrq.service;

import com.yellowrq.bo.SubmitOrderBO;

/**
 * ClassName:OrderService
 * Package:com.yellowrq.service
 * Description:
 *  订单服务
 * @author:YellowRQ
 * @data:2020/3/8 21:36
 */
public interface OrderService {

    /**
     * 用于创建订单相关信息
     * @param submitOrderBO
     * @return
     */
    public String createOrder(SubmitOrderBO submitOrderBO);


}
