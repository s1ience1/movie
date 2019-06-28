package com.dayuanit.movie.movie1.task;

import com.dayuanit.movie.movie1.Enums.OrderStatusEnum;
import com.dayuanit.movie.movie1.entity.Order;
import com.dayuanit.movie.movie1.mapper.OrderMapper;
import com.dayuanit.movie.movie1.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
//@EnableScheduling
public class OrderCancelTask extends BaseTask {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderService orderService;

    @Override
    @Scheduled(cron = "0/120 * * * * *")
    public void doTask() {
        System.out.println("orderCancel begin");
        processOrderCancel();
    }

    public void processOrderCancel() {
        // 把所有待支付的订单查询出来  超时的订单都取消 1 min

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE,-1);
        Date deadTime = calendar.getTime();

        List<Order> orders = orderMapper.listCancelOrder(OrderStatusEnum.pre_pay.getStatus(), deadTime);

        if (orders.size() == 0) {
            return;
        }

        for (Order order : orders) {
            // 修改订单状态
            try {
                orderService.cancelOrder(order);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}
