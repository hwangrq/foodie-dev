package com.yellowrq.pojo.vo;

import com.yellowrq.bo.ShopcartBO;

import java.util.List;

/**
 * 自定义订单vo
 */
public class OrderVO {

    private String orderId;
    private MerchantOrdersVO merchantOrdersVO;
    private List<ShopcartBO> toBeRemoveShopcartList;

    public List<ShopcartBO> getToBeRemoveShopcartList() {
        return toBeRemoveShopcartList;
    }

    public void setToBeRemoveShopcartList(List<ShopcartBO> toBeRemoveShopcartList) {
        this.toBeRemoveShopcartList = toBeRemoveShopcartList;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public MerchantOrdersVO getMerchantOrdersVO() {
        return merchantOrdersVO;
    }

    public void setMerchantOrdersVO(MerchantOrdersVO merchantOrdersVO) {
        this.merchantOrdersVO = merchantOrdersVO;
    }
}
