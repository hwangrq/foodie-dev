package com.yellowrq.mapper;

import com.yellowrq.pojo.vo.CategoryVO;
import com.yellowrq.pojo.vo.NewItemsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CategoryMapperCustom {

    public List<CategoryVO> getSubCatList(Integer rootCatId);

    public List<NewItemsVO> getSixNewItemLazy(@Param("paramsMap") Map<String, Object> map);
}