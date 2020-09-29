package com.yellowrq.controller;

import com.yellowrq.enums.YesOrNo;
import com.yellowrq.pojo.Carousel;
import com.yellowrq.pojo.Category;
import com.yellowrq.pojo.vo.CategoryVO;
import com.yellowrq.pojo.vo.NewItemsVO;
import com.yellowrq.service.CarouselService;
import com.yellowrq.service.CategroyService;
import com.yellowrq.utils.JSONResult;
import com.yellowrq.utils.JsonUtils;
import com.yellowrq.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName:IndexController
 * Package:com.yellowrq.controller
 * Description:
 *
 * @author:yellowrq
 * @date: 2020/2/25 10:33
 */
@Api(value = "首页", tags = {"首页展示的相关接口"})
@RestController
@RequestMapping("index")
public class IndexController {

    static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private CarouselService carouselService;

    @Autowired
    private CategroyService categroyService;

    @Autowired
    private RedisOperator redisOperator;

    @ApiOperation(value = "获取首页轮播图列表", notes = "获取首页轮播图列表", httpMethod = "GET")
    @GetMapping("/carousel")
    public JSONResult carousel(){
        List<Carousel> carousels;
        String carouselStr = redisOperator.get("carousel");
        if (StringUtils.isBlank(carouselStr)) {
            carousels = carouselService.queryAll(YesOrNo.Yes.type);
            redisOperator.set("carousel", JsonUtils.objectToJson(carousels));
        } else {
            carousels = JsonUtils.jsonToList(carouselStr, Carousel.class);
        }
        return JSONResult.ok(carousels);
        /**
         * 1.后台运营系统，一旦广告（轮播图）发生更改，就可以删除缓存，然后重置
         * 2.定时重置，如凌晨3点，注意时间分散
         * 3.每个轮播图都有可能是个广告，每个广告都有过期时间，过期则重置
         */
    }

    /**
     * 首页分类展示需求
     * 1. 第一次刷新主页查询大分类，渲染展示到首页
     * 2. 如果鼠标移动到大分类（根分类），则加载其子分类的内容，如果已经存在子分类，则不需要加载（懒加载）
     */
    @ApiOperation(value = "获取商品分类（一级分类）", notes = "获取商品分类（一级分类）", httpMethod = "GET")
    @GetMapping("/cats")
    public JSONResult cats(){
        List<Category> categories;
        String catStr = redisOperator.get("cat");
        if (StringUtils.isBlank(catStr)) {
            categories = categroyService.queryAllRootLevelCat();
            redisOperator.set("cat", JsonUtils.objectToJson(categories));
        } else {
            categories = JsonUtils.jsonToList(catStr, Category.class);
        }
        return JSONResult.ok(categories);
    }

    @ApiOperation(value = "获取商品子分类", notes = "获取商品子分类", httpMethod = "GET")
    @GetMapping("/subCat/{rootCatId}")
    public JSONResult subCat(
            @ApiParam(name = "rootCatId", value = "一级分类id", required = true)
            @PathVariable Integer rootCatId){
        if (rootCatId == null) {
            return JSONResult.errorMsg("分类不存在");
        }
        List<CategoryVO> list;
        String subCatStr = redisOperator.get("subCat" + rootCatId);
        if (StringUtils.isBlank(subCatStr)) {
            list = categroyService.getSubCatList(rootCatId);
            /**
             * 查询的key在redis中不存在，
             * 对应的id在数据库也不存在，
             * 此时被非法用户进行攻击，大量的请求会直接打在db上，
             * 造成宕机，从而影响整个系统，
             * 这种现象称之为缓存穿透。
             * 解决方案：把空的数据也缓存起来，比如空字符串，空对象，空数组或list
             */
            if (list != null && list.size() > 0) {
                redisOperator.set("subCat:" + rootCatId, JsonUtils.objectToJson(list));
            } else {
                redisOperator.set("subCat:" + rootCatId, JsonUtils.objectToJson(list), 5*60);
            }
        } else {
            list = JsonUtils.jsonToList(subCatStr, CategoryVO.class);
        }
        return JSONResult.ok(list);
    }

    @ApiOperation(value = "查询每个一级分类下的最新6条商品数据", notes = "查询每个一级分类下的最新6条商品数据", httpMethod = "GET")
    @GetMapping("/sixNewItems/{rootCatId}")
    public JSONResult sixNewItems(
            @ApiParam(name = "rootCatId", value = "一级分类id", required = true)
            @PathVariable Integer rootCatId){
        if (rootCatId == null) {
            return JSONResult.errorMsg("分类不存在");
        }
        List<NewItemsVO> list = categroyService.getSixNewItemLazy(rootCatId);
        return JSONResult.ok(list);
    }
}
