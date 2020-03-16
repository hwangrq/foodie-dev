package com.yellowrq.service.center;

import com.yellowrq.bo.center.OrderItemsCommentBO;
import com.yellowrq.pojo.OrderItems;
import com.yellowrq.utils.PagedGridResult;
import org.apache.catalina.User;

import java.util.List;

/**
 * ClassName:MyCommentsService
 * Package:com.yellowrq.service.center
 * Description:
 *
 * @author:yellowrq
 * @date: 2020/3/12 14:39
 */
public interface MyCommentsService {

    /**
     * 根据订单id查询管理的商品
     * @param orderId
     * @return
     */
    public List<OrderItems> queryPendingComment(String orderId);

    /**
     * 保存用户的评论
     * @param orderId
     * @param userId
     * @param commentList
     */
    public void saveComments(String orderId, String userId, List<OrderItemsCommentBO> commentList);


    /**
     * 我的评价查询 分页
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    public PagedGridResult queryMyComments(String userId, Integer page, Integer pageSize);
}

