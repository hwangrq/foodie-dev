package com.yellowrq.service.impl;

import com.github.pagehelper.PageHelper;
import com.google.common.collect.Ordering;
import com.yellowrq.bo.center.OrderItemsCommentBO;
import com.yellowrq.enums.YesOrNo;
import com.yellowrq.mapper.ItemsCommentsMapperCustom;
import com.yellowrq.mapper.OrderItemsMapper;
import com.yellowrq.mapper.OrderStatusMapper;
import com.yellowrq.mapper.OrdersMapper;
import com.yellowrq.pojo.OrderItems;
import com.yellowrq.pojo.OrderStatus;
import com.yellowrq.pojo.Orders;
import com.yellowrq.pojo.vo.MyCommentVO;
import com.yellowrq.service.center.MyCommentsService;
import com.yellowrq.service.impl.center.BaseService;
import com.yellowrq.utils.PagedGridResult;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * ClassName:MyCommentsServiceImpl
 * Package:com.yellowrq.service.impl
 * Description:
 *
 * @author:yellowrq
 * @date: 2020/3/12 14:39
 */
@Service
public class MyCommentsServiceImpl extends BaseService implements MyCommentsService {

    @Autowired
    public OrderItemsMapper orderItemsMapper;

    @Autowired
    public OrdersMapper ordersMapper;

    @Autowired
    public OrderStatusMapper orderStatusMapper;

    @Autowired
    public ItemsCommentsMapperCustom itemsCommentsMapperCustom;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<OrderItems> queryPendingComment(String orderId) {
        OrderItems oi = new OrderItems();
        oi.setOrderId(orderId);
        List<OrderItems> list = orderItemsMapper.select(oi);
        return list;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveComments(String orderId, String userId,
                             List<OrderItemsCommentBO> commentList) {

        // 1. 保存评价 items_comments
        for (OrderItemsCommentBO oic : commentList) {
            oic.setCommentId(sid.nextShort());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("commentList", commentList);
        itemsCommentsMapperCustom.saveComments(map);

        // 2. 修改订单表改已评价 orders
        Orders order = new Orders();
        order.setId(orderId);
        order.setIsComment(YesOrNo.Yes.type);
        ordersMapper.updateByPrimaryKeySelective(order);

        // 3. 修改订单状态表的留言时间 order_status
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setCommentTime(new Date());
        orderStatusMapper.updateByPrimaryKeySelective(orderStatus);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryMyComments(String userId,
                                           Integer page,
                                           Integer pageSize) {

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        PageHelper.startPage(page, pageSize);
        List<MyCommentVO> list = itemsCommentsMapperCustom.queryMyComments(map);

        return setterPagedGrid(list, page);
    }
}
