package com.yellowrq.mapper;

import com.yellowrq.my.mapper.MyMapper;
import com.yellowrq.pojo.Items;
import com.yellowrq.pojo.vo.ItemCommentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsMapperCustom {

    public List<ItemCommentVO> queryItemComments(@Param("paramsMap") Map<String, Object> map);
}