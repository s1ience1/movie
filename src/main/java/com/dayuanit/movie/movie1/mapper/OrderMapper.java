package com.dayuanit.movie.movie1.mapper;

import com.dayuanit.movie.movie1.entity.Order;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    int modifyOrderStatus(@Param("orderId") Integer orderId,
                          @Param("newStatus") Integer newStatus,
                          @Param("oldStatus") Integer oldStatus);

    List<Order> listPrePayOrder(Integer status);

    List<Order> listCancelOrder(@Param("status") Integer status, @Param("deadTime") Date deadTime);
}