package com.yellowrq.controller;

import com.yellowrq.bo.SubmitOrderBO;
import com.yellowrq.enums.OrderStatusEnum;
import com.yellowrq.enums.PayMethod;
import com.yellowrq.pojo.OrderStatus;
import com.yellowrq.pojo.vo.MerchantOrdersVO;
import com.yellowrq.pojo.vo.OrderVO;
import com.yellowrq.service.OrderService;
import com.yellowrq.utils.CookieUtils;
import com.yellowrq.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ClassName:OrdersController
 * Package:com.yellowrq.controller
 * Description:
 *
 * @author:YellowRQ
 * @data:2020/3/8 21:18
 */
@Api(value = "订单相关", tags = {"订单相关的api接口"} )
@RestController
@RequestMapping("orders")
public class OrdersController extends BaseController{

    @Autowired
    private OrderService orderService;

    @Autowired
    private RestTemplate restTemplate;

    @ApiOperation(value = "创建订单", notes = "用户创建订单", httpMethod = "POST")
    @PostMapping("/create")
    public JSONResult create(
            @ApiParam(name = "submitOrderBO", value = "提交订单BO", required = true)
            @RequestBody SubmitOrderBO submitOrderBO,
            HttpServletRequest request,
            HttpServletResponse response){
        if (submitOrderBO.getPayMethod() != PayMethod.WEIXIN.type
                && submitOrderBO.getPayMethod() != PayMethod.ALIPAY.type){
            return JSONResult.errorMsg("支付方式不支持");
        }
//        System.out.println(submitOrderBO.toString());
        //1.创建订单
        OrderVO orderVO = orderService.createOrder(submitOrderBO);
        String orderId = orderVO.getOrderId();

        //2.创建订单以后，移除购物车中已结算（已提交）的商品
        //to 整合redis之后，完善购物车中的已结算商品清除,并且同步到前端的cookie
//        CookieUtils.setCookie(request, response, FOODIE_SHOPCART, "", true);
        //3.向支付中心发送当前订单，用于保存支付中心的订单数据
        MerchantOrdersVO merchantOrdersVO = orderVO.getMerchantOrdersVO();
        merchantOrdersVO.setReturnUrl(payReturnUrl);

        //为了方便测试购买所有的支付金额都统一改成1分钱
        merchantOrdersVO.setAmount(1);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("imoocUserId", "imooc");
        httpHeaders.add("password","imooc");
        HttpEntity<MerchantOrdersVO> entity = new HttpEntity<>(merchantOrdersVO, httpHeaders);
        ResponseEntity<JSONResult> responseEntity =
                restTemplate.postForEntity(paymentServerUrl, entity, JSONResult.class);
        JSONResult paymentResult = responseEntity.getBody();
        if (paymentResult.getStatus() != HttpStatus.OK.value()) {
            return JSONResult.errorMsg("支付中心订单创建失败，请联系管理员");
        }

        return JSONResult.ok(orderId);
    }

    @ApiOperation(value = "通知商户订单支付id", tags = {"通知商户订单支付id"}, httpMethod = "POST")
    @PostMapping("/notifyMerchantOrderPaid")
    public Integer notifyMerchantOrderPaid (String merchantOrderId) {
        orderService.updateOrderStatus(merchantOrderId, OrderStatusEnum.WAIT_DELIVER.type);
        return HttpStatus.OK.value();
    }

    @ApiOperation(value = "查询支付订单信息", tags = {"查询订单信息"}, httpMethod = "POST")
    @PostMapping("/getPaidOrderInfo")
    public JSONResult getPaidOrderInfo(String orderId) {
        OrderStatus orderStatus = orderService.queryOrderStatusInfo(orderId);
        return JSONResult.ok(orderStatus);
    }
}
