package com.dayuanit.movie.movie1.service;

import com.dayuanit.movie.movie1.Enums.OrderStatusEnum;
import com.dayuanit.movie.movie1.entity.*;
import com.dayuanit.movie.movie1.exception.BizException;
import com.dayuanit.movie.movie1.mapper.*;
import com.dayuanit.movie.movie1.util.MoneyUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private CinemasMapper cinemasMapper;

    @Autowired
    private FilmMapper filmMapper;

    @Autowired
    private FilmScheduleMapper filmScheduleMapper;

    @Autowired
    private RedisService redisServie;


    // 创建订单
    @Transactional(rollbackFor = Exception.class)
    public Order createOrder(int cinemaId, int filmId, int scheduleId,String seatInfo) {
        Cinemas cinemas = cinemasMapper.selectByPrimaryKey(cinemaId);
        Film film = filmMapper.selectByPrimaryKey(filmId);
        FilmSchedule filmSchedule = filmScheduleMapper.selectByPrimaryKey(scheduleId);

        //验证参数
        if (null == cinemas || null == film || null == filmSchedule || StringUtils.isBlank(seatInfo)) {
            throw new BizException("参数错误，请重试");
        }

        //写入主表
        Order order = new Order();
        order.setCinemaId(cinemaId);
        order.setFilmId(filmId);
        order.setFilmScheduleId(scheduleId);
        order.setCreateTime(new Date());
        order.setModifyTime(new Date());
        order.setStatus(OrderStatusEnum.pre_pay.getStatus());

        String[] seats = seatInfo.split(",");
        String money = MoneyUtil.mul(String.valueOf(seats.length),filmSchedule.getPrice());
        order.setAmount(money);

        orderMapper.insert(order);

        // 写入商品表 用订单的id关联
        for (String seat : seats) {
            String row = seat.split("-")[0];
            String col = seat.split("-")[1];

            //设置redis缓存  防止并发
            boolean flag = redisServie.cacheSeatInfo(scheduleId,Integer.parseInt(row),Integer.parseInt(col));
            if (!flag) {
                throw new BizException("订单已存在");
            }

            // 查询数据库
            OrderInfo seatBuy = orderInfoMapper.checkTicketStatus(scheduleId, Integer.parseInt(row), Integer.parseInt(col));
            if (null !=seatBuy) {
                throw new BizException("订单已存在");
            }

            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setOrderId(order.getId());
            orderInfo.setPrice(filmSchedule.getPrice());
            orderInfo.setSeatRow(Integer.parseInt(row));
            orderInfo.setSeatCol(Integer.parseInt(col));
            orderInfoMapper.insert(orderInfo);
        }

        return order;
    }

    public void alipayNotify(int orderId,String totalAmount) {
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if (null == order) {
            throw new BizException("订单不存在");
        }

        if (Double.parseDouble(totalAmount) != Double.parseDouble(order.getAmount())) {
            throw new BizException("支付金额不对");
        }

        if (order.getStatus() != OrderStatusEnum.pre_pay.getStatus()) {
            throw new BizException("支付状态不对");
        }

        if (order.getStatus() == OrderStatusEnum.cancel_order.getStatus()) {
            // 退款
            throw new BizException("支付状态不对");
        }

        // 所有的验证完成 修改订单状态为支付成功 使用乐观锁
        int rows = orderMapper.modifyOrderStatus(orderId, OrderStatusEnum.pay_success.getStatus(), order.getStatus());
        if (1 != rows) {
            throw new BizException("异步处理失败");
        }

        //TODO 比如发短信通知用户 或者发邮件 或者通知第三方等等 和当前异步处理流程非相关的业务 可以单独另起炉灶
    }


    public Order getOrder(Integer orderId) {
        return orderMapper.selectByPrimaryKey(orderId);
    }

    public List<OrderInfo> listOrderInfo(Integer orderId) {
        return orderInfoMapper.listOrderInfo(orderId);
    }

    public void aliQuery(Order order,String totalAmount) {
        // 验证金额
        if (Double.parseDouble(totalAmount) != Double.parseDouble(order.getAmount())){
            throw new BizException("支付金额不对");
        }

        // 修改订单状态为成功
        int rows = orderMapper.modifyOrderStatus(order.getId(), OrderStatusEnum.pay_success.getStatus(), order.getStatus());
        if (1 != rows) {
            throw new BizException("修改订单状态失败");
        }

        //TODO 比如发短信通知用户 或者发邮件 或者通知第三方等等 和当前异步处理流程非相关的业务 可以单独另起炉灶
    }

    public void cancelOrder(Order order) {
        int rows = orderMapper.modifyOrderStatus(order.getId(), OrderStatusEnum.cancel_order.getStatus(), order.getStatus());
        if (1 != rows) {
            throw new BizException("订单关闭失败");
        }

        //TODO 比如发短信通知用户 或者发邮件 或者通知第三方等等 和当前异步处理流程非相关的业务 可以单独另起炉灶
    }

}