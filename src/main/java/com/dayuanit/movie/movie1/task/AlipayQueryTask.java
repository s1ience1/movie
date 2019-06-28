package com.dayuanit.movie.movie1.task;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.dayuanit.movie.movie1.Enums.OrderStatusEnum;
import com.dayuanit.movie.movie1.entity.Order;
import com.dayuanit.movie.movie1.handler.AlipayHandle;
import com.dayuanit.movie.movie1.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableScheduling
public class AlipayQueryTask extends BaseTask{

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private AlipayHandle alipayHandle;


    @Override
    @Scheduled(cron = "0/120 * * * * *")
    public void doTask() {
        System.out.println("task begin");
        processQueryPay();
    }


    private void processQueryPay(){
        // task---handle---service
        // 状态为 0 的代付款订单  去alipay查询是否支付
        List<Order> orders = orderMapper.listPrePayOrder(OrderStatusEnum.pre_pay.getStatus());
        if (orders.size() == 0) {
            return;
        }
        for (Order order : orders) {
            try {
                alipayHandle.processAliQuery(order);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
