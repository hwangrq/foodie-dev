package com.yellowrq.service;

import com.yellowrq.pojo.Items;
import com.yellowrq.pojo.ItemsImg;
import com.yellowrq.pojo.ItemsParam;
import com.yellowrq.pojo.ItemsSpec;
import com.yellowrq.pojo.vo.CommentLevelCountsVO;
import com.yellowrq.pojo.vo.ShopcartVO;
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

    /**
     * 根据关键字和排序 搜索商品列表
     * 分页
     * @param keywords
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    public PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize);

    /**
     * 根据分类id 搜索商品列表
     * 分页
     * @param catId
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    public PagedGridResult searchItemsByThirdCat(Integer catId, String sort, Integer page, Integer pageSize);

    /**
     * 根据拼接的规格ids查询最新的购物车中的商品数据(用于刷新渲染购物车)
     * @param specIds
     * @return
     */
    public List<ShopcartVO> queryItemsBySpecIds(String specIds);

    /**
     * 根据商品的规格id 获取规格对象的具体信息
     * @param itemSpecId
     * @return
     */
    public ItemsSpec queryItemSpecById(String itemSpecId);

    /**
     * 根据商品id获得商品图片主图url
     * @param itemId
     * @return
     */
    public String queryItemsMainImgById(String itemId);

    /**
     * 减少库存
     * @param itemSpecId
     * @param buyCounts
     */
    public void decreaseItemSpecStock(String itemSpecId, int buyCounts);
}
