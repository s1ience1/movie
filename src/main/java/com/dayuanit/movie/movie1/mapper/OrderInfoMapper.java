package com.dayuanit.movie.movie1.mapper;

import com.dayuanit.movie.movie1.entity.OrderInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderInfo record);

    int insertSelective(OrderInfo record);

    OrderInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderInfo record);

    int updateByPrimaryKey(OrderInfo record);

    List<OrderInfo> listOrderInfo(Integer orderId);

    List<OrderInfo> listAlreadyBuyTicket(Integer scheduleId);

    OrderInfo checkTicketStatus(@Param("scheduleId") Integer scheduleId,
                                @Param("row")Integer row,
                                @Param("col") Integer col);
}