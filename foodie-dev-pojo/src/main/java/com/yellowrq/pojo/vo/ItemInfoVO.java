package com.yellowrq.pojo.vo;

import com.yellowrq.pojo.Items;
import com.yellowrq.pojo.ItemsImg;
import com.yellowrq.pojo.ItemsParam;
import com.yellowrq.pojo.ItemsSpec;

import java.util.List;

/**
 * ClassName:ItemInfoVO
 * Package:com.yellowrq.pojo.vo
 * Description:
 *  商品详情VO
 * @author:yellowrq
 * @date: 2020/2/27 14:58
 */
public class ItemInfoVO {

    private Items item ;
    private List<ItemsImg> itemImgList ;
    private List<ItemsSpec> itemSpecList ;
    private ItemsParam itemParams ;

    public Items getItem() {
        return item;
    }

    public void setItem(Items item) {
        this.item = item;
    }

    public List<ItemsImg> getItemImgList() {
        return itemImgList;
    }

    public void setItemImgList(List<ItemsImg> itemImgList) {
        this.itemImgList = itemImgList;
    }

    public List<ItemsSpec> getItemSpecList() {
        return itemSpecList;
    }

    public void setItemSpecList(List<ItemsSpec> itemSpecList) {
        this.itemSpecList = itemSpecList;
    }

    public ItemsParam getItemParams() {
        return itemParams;
    }

    public void setItemParams(ItemsParam itemParams) {
        this.itemParams = itemParams;
    }
}
