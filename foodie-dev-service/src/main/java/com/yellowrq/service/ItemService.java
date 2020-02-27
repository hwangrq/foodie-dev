package com.yellowrq.service;

import com.yellowrq.pojo.Items;
import com.yellowrq.pojo.ItemsImg;
import com.yellowrq.pojo.ItemsParam;
import com.yellowrq.pojo.ItemsSpec;
import com.yellowrq.pojo.vo.CommentLevelCountsVO;
import com.yellowrq.utils.PagedGridResult;

import java.util.List;

/**
 * ClassName:ItemService
 * Package:com.yellowrq.service
 * Description:
 * 商品服务
 * @author:yellowrq
 * @date: 2020/2/27 13:14
 */
public interface ItemService {

    /**
     * 根据商品id查询详情
     * @param itemId
     * @return
     */
    public Items queryItemById(String itemId);

    /**
     * 根据商品id查询商品图片列表
     * @param itemId
     * @return
     */
    public List<ItemsImg> queryItemImgList(String itemId);

    /**
     * 根据商品id查询商品规格列表
     * @param itemId
     * @return
     */
    public List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * 根据商品id查询商品参数属性
     * @param itemId
     * @return
     */
    public ItemsParam queryItemParam(String itemId);

    /**
     * 根据商品id查询商品的评价等级数量
     * @param itemId
     */
    public CommentLevelCountsVO queryCommentCounts(String itemId);

    /**
     * 根据商品id 查询商品评价
     * 分页
     * @param itemId
     * @param level
     * @return
     */
    public PagedGridResult queryPagedComments(String itemId, Integer level,
                                              Integer page, Integer pageSize);
}
