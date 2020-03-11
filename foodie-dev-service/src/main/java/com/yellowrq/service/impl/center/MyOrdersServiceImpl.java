package com.yellowrq.service.impl.center;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yellowrq.mapper.OrderStatusMapper;
import com.yellowrq.mapper.OrdersMapper;
import com.yellowrq.mapper.OrdersMapperCustom;
import com.yellowrq.pojo.Orders;
import com.yellowrq.pojo.vo.MyOrdersVO;
import com.yellowrq.service.center.MyOrdersService;
import com.yellowrq.utils.PagedGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:MyOrdersServiceImpl
 * Package:com.yellowrq.service.impl.center
 * Description:
 *
 * @author:yellowrq
 * @date: 2020/3/11 17:34
 */
@Service
public class MyOrdersServiceImpl extends BaseService implements MyOrdersService {

    @Autowired
    private OrdersMapperCustom ordersMapperCustom;

    @Autowired
    public OrdersMapper ordersMapper;

    @Autowired
    public OrderStatusMapper orderStatusMapper;


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryMyOrders(String userId,
                                         Integer orderStatus,
                                         Integer page,
                                         Integer pageSize) {

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        if (orderStatus != null) {
            map.put("orderStatus", orderStatus);
        }

        PageHelper.startPage(page, pageSize);

        List<MyOrdersVO> list = ordersMapperCustom.queryMyOrders(map);

        return setterPagedGrid(list, page);
    }

    @Override
    public void updateDeliverOrderStatus(String orderId) {

    }

    @Override
    public Orders queryMyOrder(String userId, String orderId) {
        return null;
    }

    @Override
    public boolean updateReceiveOrderStatus(String orderId) {
        return false;
    }

    @Override
    public boolean deleteOrder(String userId, String orderId) {
        return false;
    }

    @Override
    public PagedGridResult getOrdersTrend(String userId, Integer page, Integer pageSize) {
        return null;
    }


}
