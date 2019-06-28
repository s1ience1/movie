package com.dayuanit.movie.movie1.Enums;

public enum OrderStatusEnum {
    pre_pay(0),
    pay_success(1),
    cancel_order(2);

    private int status;

    private OrderStatusEnum(int status){
        this.status = status;
    }

    public int getStatus() {
        return status;
    }


}
