package com.yellowrq.service.impl;

import com.yellowrq.mapper.CategoryMapper;
import com.yellowrq.mapper.CategoryMapperCustom;
import com.yellowrq.pojo.Category;
import com.yellowrq.pojo.vo.CategoryVO;
import com.yellowrq.service.CategroyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * ClassName:CategroyServiceImpl
 * Package:com.yellowrq.service.impl
 * Description:
 *
 * @author:yellowrq
 * @date: 2020/2/25 15:40
 */
@Service
public class CategroyServiceImpl implements CategroyService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryMapperCustom categoryMapperCustom;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> queryAllRootLevelCat() {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type", 1);
        List<Category> categories = categoryMapper.selectByExample(example);
        return categories;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<CategoryVO> getSubCatList(Integer rootCatId) {
        return categoryMapperCustom.getSubCatList(rootCatId);
    }
}
