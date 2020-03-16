package com.yellowrq.mapper;

import com.yellowrq.my.mapper.MyMapper;
import com.yellowrq.pojo.ItemsComments;
import com.yellowrq.pojo.vo.MyCommentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * ClassName:ItemsCommentsMapperCustom
 * Package:com.yellowrq.mapper
 * Description:
 *
 * @author:yellowrq
 * @date: 2020/3/12 15:04
 */
public interface ItemsCommentsMapperCustom extends MyMapper<ItemsComments> {

    public void saveComments(Map<String, Object> map);

    public List<MyCommentVO> queryMyComments(@Param("paramsMap") Map<String, Object> map);

}