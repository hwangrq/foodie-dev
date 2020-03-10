package com.yellowrq.pojo.vo;

/**
 * ClassName:MerchantOrdersVO
 * Package:com.yellowrq.vo
 * Description:
 *  商户订单vo
 * @author:yellowrq
 * @date: 2020/3/10 11:13
 */
public class MerchantOrdersVO {

    private String merchantOrderId; //商户订单号
    private String merchantUserId;  //商户方的发起用户的用户id
    private Integer amount;         //实际支付总金额（包括商户所支付的订单邮费）
    private Integer payMethod;      //支付方式: 1.微信，2.支付宝
    private String returnUrl;       //支付成功的回调地址

    public String getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(String merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public String getMerchantUserId() {
        return merchantUserId;
    }

    public void setMerchantUserId(String merchantUserId) {
        this.merchantUserId = merchantUserId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }
}
