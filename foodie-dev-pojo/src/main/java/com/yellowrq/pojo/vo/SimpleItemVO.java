package com.yellowrq.pojo.vo;

/**
 * ClassName:SimpleItemVO
 * Package:com.yellowrq.pojo.vo
 * Description:
 *  6个最新商品的简单数据类型
 * @author:yellowrq
 * @date: 2020/2/26 18:06
 */
public class SimpleItemVO {

    private String itemId;
    private String itemName;
    private String itemUrl;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }
}
