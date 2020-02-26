package com.yellowrq.service;

import com.yellowrq.pojo.Category;
import com.yellowrq.pojo.vo.CategoryVO;
import com.yellowrq.pojo.vo.NewItemsVO;

import java.util.List;

/**
 * ClassName:CategroyService
 * Package:com.yellowrq.service
 * Description:
 *  分类服务
 * @author:yellowrq
 * @date: 2020/2/25 15:21
 */
public interface CategroyService {

    /**
     * 查询所有一级分类
     * @return
     */
    public List<Category> queryAllRootLevelCat();

    /**
     * 根据一级分类id
     * 查询子分类信息
     * @param rootCatId
     * @return
     */
    public List<CategoryVO> getSubCatList(Integer rootCatId);

    /**
     * 查询首页每个一级分类下的六条最新商品数据
     * @param rootCatId
     * @return
     */
    public List<NewItemsVO> getSixNewItemLazy(Integer rootCatId);
}
