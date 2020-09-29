package com.yellowrq.service.impl;

import com.yellowrq.bo.ShopcartBO;
import com.yellowrq.bo.SubmitOrderBO;
import com.yellowrq.enums.OrderStatusEnum;
import com.yellowrq.enums.YesOrNo;
import com.yellowrq.mapper.OrderItemsMapper;
import com.yellowrq.mapper.OrderStatusMapper;
import com.yellowrq.mapper.OrdersMapper;
import com.yellowrq.pojo.*;
import com.yellowrq.pojo.vo.MerchantOrdersVO;
import com.yellowrq.pojo.vo.OrderVO;
import com.yellowrq.pojo.vo.ShopcartVO;
import com.yellowrq.service.AddressService;
import com.yellowrq.service.ItemService;
import com.yellowrq.service.OrderService;
import com.yellowrq.utils.DateUtil;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ClassName:OrderServiceImpl
 * Package:com.yellowrq.service.impl
 * Description:
 *
 * @author:YellowRQ
 * @data:2020/3/8 21:39
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderItemsMapper orderItemsMapper;
    @Autowired
    private OrderStatusMapper orderStatusMapper;
    @Autowired
    private Sid sid;
    @Autowired
    private AddressService addressService;
    @Autowired
    private ItemService itemService;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public OrderVO createOrder(List<ShopcartBO> shopcartList, SubmitOrderBO submitOrderBO) {
        String userId = submitOrderBO.getUserId();
        String itemSpecIds = submitOrderBO.getItemSpecIds();
        String addressId = submitOrderBO.getAddressId();
        Integer payMethod = submitOrderBO.getPayMethod();
        String leftMsg = submitOrderBO.getLeftMsg();
        //包邮费用设置为0
        Integer postAmount = 0;

        String orderId = sid.nextShort();

        UserAddress address = addressService.queryUserAddress(userId, addressId);

        //1.新订单数据保存
        Orders newOrder = new Orders();
        newOrder.setId(orderId);
        newOrder.setUserId(userId);
        newOrder.setReceiverName(address.getReceiver());
        newOrder.setReceiverMobile(address.getMobile());
        newOrder.setReceiverAddress(address.getProvince() + " "
                                    + address.getCity() + " "
                                    + address.getDistrict() + " "
                                    + address.getDetail());
//        newOrder.setTotalAmount();
//        newOrder.setRealPayAmount();
        newOrder.setPostAmount(postAmount);

        newOrder.setPayMethod(payMethod);
        newOrder.setLeftMsg(leftMsg);

        newOrder.setIsComment(YesOrNo.NO.type);
        newOrder.setIsDelete(YesOrNo.NO.type);
        newOrder.setCreatedTime(new Date());
        newOrder.setUpdatedTime(new Date());

        //2.循环根据itemSpecIds保存订单商品信息表
        String[] itemSpecIdArr = itemSpecIds.split(",");
        Integer totalAmount = 0;    //商品原价累计
        Integer realPayAmount = 0;  //优惠后的实际支付的价格累计

        List<ShopcartBO> toBeRemovedShopcartList = new ArrayList<>();
        for (String itemSpecId : itemSpecIdArr) {
            ShopcartBO cartItem = getBuyCountsFromShopcart(shopcartList, itemSpecId);
            // 整合redis后，商品购买的数量重新从redis的购物车中获取
            int buyCounts = cartItem.getBuyCounts();
            toBeRemovedShopcartList.add(cartItem);

            //2.1 根据规格id，查询规格的具体信息，主要获取价格
            ItemsSpec itemSpec = itemService.queryItemSpecById(itemSpecId);
            totalAmount += itemSpec.getPriceNormal() * buyCounts;
            realPayAmount += itemSpec.getPriceDiscount() * buyCounts;

            //2.2 根据规格id， 获得商品信息以及商品图片
            String itemId = itemSpec.getItemId();
            Items item = itemService.queryItemById(itemId);
            String imgUrl = itemService.queryItemsMainImgById(itemId);

            //2.3循环保存子订单数据到数据库
            String subOrderId = sid.nextShort();
            OrderItems subOrderItem = new OrderItems();
            subOrderItem.setId(subOrderId);
            subOrderItem.setOrderId(orderId);
            subOrderItem.setItemId(itemId);
            subOrderItem.setItemName(item.getItemName());
            subOrderItem.setItemImg(imgUrl);
            subOrderItem.setBuyCounts(buyCounts);
            subOrderItem.setItemSpecId(itemSpecId);
            subOrderItem.setItemSpecName(itemSpec.getName());
            subOrderItem.setPrice(itemSpec.getPriceDiscount());
            orderItemsMapper.insert(subOrderItem);

            //2.4 在用户提交订单以后，规格表中需要扣除库存
            itemService.decreaseItemSpecStock(itemSpecId, buyCounts);
        }
        newOrder.setTotalAmount(totalAmount);
        newOrder.setRealPayAmount(realPayAmount);
        ordersMapper.insert(newOrder);

        //3.保存订单状态表
        OrderStatus waitPayOrderStatus = new OrderStatus();
        waitPayOrderStatus.setOrderId(orderId);
        waitPayOrderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        waitPayOrderStatus.setCreatedTime(new Date());
        orderStatusMapper.insert(waitPayOrderStatus);

        //4.构建商户订单，用于传给支付中心
        MerchantOrdersVO merchantOrdersVO = new MerchantOrdersVO();
        merchantOrdersVO.setMerchantOrderId(orderId);
        merchantOrdersVO.setMerchantUserId(userId);
        merchantOrdersVO.setAmount(realPayAmount + postAmount);
        merchantOrdersVO.setPayMethod(payMethod);

        //构建自定义订单vo
        OrderVO orderVO = new OrderVO();
        orderVO.setOrderId(orderId);
        orderVO.setMerchantOrdersVO(merchantOrdersVO);
        orderVO.setToBeRemoveShopcartList(toBeRemovedShopcartList);
        return orderVO;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateOrderStatus(String orderId, Integer orderStatus) {
        OrderStatus paidStatus = new OrderStatus();
        paidStatus.setOrderId(orderId);
        paidStatus.setOrderStatus(orderStatus);
        paidStatus.setPayTime(new Date());
        orderStatusMapper.updateByPrimaryKeySelective(paidStatus);
    }

    /**
     * 从redis中的购物车里获取商品，目的：counts
     * @param shopcartBOList
     * @param specId
     * @return
     */
    private ShopcartBO getBuyCountsFromShopcart(List<ShopcartBO> shopcartBOList, String specId) {
        for (ShopcartBO shopcartBO : shopcartBOList) {
            if (shopcartBO.getSpecId().equals(specId)) {
                return shopcartBO;
            }
        }
        return null;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public OrderStatus queryOrderStatusInfo(String orderId) {
        return orderStatusMapper.selectByPrimaryKey(orderId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public void closeOrder() {
       //查询所有未付款订单，判断时间是否超时（1天），超时则关闭交易
        OrderStatus queryOrder = new OrderStatus();
        queryOrder.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        List<OrderStatus> list = orderStatusMapper.select(queryOrder);
        for (OrderStatus orderStatus : list) {
            //订单创建时间
            Date createTime = orderStatus.getCreatedTime();
            //与当前时间进行比较
            int days = DateUtil.daysBetween(createTime, new Date());
            if (days > 1) {
                //超过一天，关闭订单
                doCloseOrder(orderStatus.getOrderId());
            }
        }

    }

    /**
     * 关闭当前订单
     * @param orderId
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    void doCloseOrder(String orderId) {
        OrderStatus close = new OrderStatus();
        close.setOrderId(orderId);
        close.setOrderStatus(OrderStatusEnum.CLOSE.type);
        orderStatusMapper.updateByPrimaryKeySelective(close);
    }
}
