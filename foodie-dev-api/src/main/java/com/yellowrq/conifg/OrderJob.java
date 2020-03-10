package com.yellowrq.conifg;

import com.yellowrq.service.OrderService;
import com.yellowrq.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * ClassName:OrderJob
 * Package:com.yellowrq.conifg
 * Description:
 *
 * @author:yellowrq
 * @date: 2020/3/10 16:58
 */
@Component
public class OrderJob {

    @Autowired
    private OrderService orderService;

    /**
     * 使用定时任务关闭超期未支付订单的弊端：
     * 1.会有时间差，程序不严谨：
     *      10:39下单，11点检查不足1小时，12点检查则超出1小时21分钟
     * 2.不支持集群：
     *      单机没毛病，使用集群，就会有多个定时任务
     *      解决方案：只使用一台计算机节点，单独来运行所有的定时任务
     * 3.会对计算机全表搜索，极其影响数据库性能
     *      select * from order where orderStatus = 10
     * 定时任务只适用于小型轻量级的项目，传统项目
     *
     * 后续：涉及到消息队列MQ --> RabbitMQ, RocketMQ, Kafka, ZeroMQ..
     *      延时任务（队列）
     *      10:12下单，未付款10状态，11:12分检查
     */
//    @Scheduled(cron = "0/3 * * * * ?")
    @Scheduled(cron = "0 0 0/1 * * ?") //每隔一小时
    public void autoCloseOrder(){
        orderService.closeOrder();
        System.out.println("执行定时任务，当前时间为：" +
                DateUtil.getCurrentDateString(DateUtil.DATETIME_PATTERN));
    }


}
