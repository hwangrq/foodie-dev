package com.yellowrq.service;

import com.yellowrq.pojo.Category;
import com.yellowrq.pojo.vo.CategoryVO;

import java.util.List;

/**
 * ClassName:CategroyService
 * Package:com.yellowrq.service
 * Description:
 *
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
}
