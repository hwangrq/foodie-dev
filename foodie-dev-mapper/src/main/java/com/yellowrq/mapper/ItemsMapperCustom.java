package com.yellowrq.mapper;

import com.yellowrq.bo.ShopcartBO;
import com.yellowrq.my.mapper.MyMapper;
import com.yellowrq.pojo.Items;
import com.yellowrq.pojo.vo.ItemCommentVO;
import com.yellowrq.pojo.vo.SearchItemsVO;
import com.yellowrq.pojo.vo.ShopcartVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsMapperCustom {

    public List<ItemCommentVO> queryItemComments(@Param("paramsMap") Map<String, Object> map);

    public List<SearchItemsVO> searchItems(@Param("paramsMap") Map<String, Object> map);

    public List<SearchItemsVO> searchItemsByThirdCat(@Param("paramsMap") Map<String, Object> map);

    public List<ShopcartVO> queryItemsBySpecIds(@Param("paramsList") List specIdList);

    public int decreaseItemSpecStock(@Param("specId") String specId,
                                     @Param("pendingCounts") Integer pendingCounts);
}