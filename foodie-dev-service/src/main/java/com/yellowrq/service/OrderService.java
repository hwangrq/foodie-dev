package com.yellowrq.service;

import com.yellowrq.bo.SubmitOrderBO;
import com.yellowrq.pojo.OrderStatus;
import com.yellowrq.pojo.vo.OrderVO;

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
    public OrderVO createOrder(SubmitOrderBO submitOrderBO);


    /**
     * 修改订单状态
     * @param orderId
     * @param orderStatus
     */
    public void updateOrderStatus(String orderId, Integer orderStatus);

    /**
     * 查询订单状态
     * @param orderId
     * @return
     */
    public OrderStatus queryOrderStatusInfo(String orderId);

    /**
     * 关闭超时未支付订单
     */
    public void closeOrder();
}
