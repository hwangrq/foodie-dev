package com.yellowrq.service;

import com.yellowrq.pojo.Carousel;

import java.util.List;

/**
 * ClassName:CarouselService
 * Package:com.yellowrq.service
 * Description:
 *  轮播图
 * @author:yellowrq
 * @date: 2020/2/25 10:11
 */
public interface CarouselService {

    /**
     * 查询轮播图
     * @param isShow
     * @return
     */
    public List<Carousel> queryAll(Integer isShow);

}
