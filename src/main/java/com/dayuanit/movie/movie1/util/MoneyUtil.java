package com.dayuanit.movie.movie1.util;

import java.math.BigDecimal;

public class MoneyUtil {
    public static String add(String num1,String num2) {
        BigDecimal bigDecimal = new BigDecimal(num1);
        BigDecimal bigDecima2 = new BigDecimal(num2);
        // setScale是设置保留几位小数   ROUND_HALF_EVEN 满5就进以为
        BigDecimal add = bigDecimal.add(bigDecima2).setScale(2,BigDecimal.ROUND_HALF_EVEN);
        return add.toString();
    }

    public static String minus(String num1,String num2) {
        BigDecimal bigDecimal = new BigDecimal(num1);
        BigDecimal bigDecima2 = new BigDecimal(num2);
        BigDecimal minus = bigDecimal.subtract(bigDecima2).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        return minus.toString();
    }

    public static String mul(String num1, String num2) {
        BigDecimal bigDecimal = new BigDecimal(num1);
        BigDecimal bigDecima2 = new BigDecimal(num2);
        return bigDecimal.multiply(bigDecima2).setScale(2,BigDecimal.ROUND_HALF_EVEN).toString();
    }
}
