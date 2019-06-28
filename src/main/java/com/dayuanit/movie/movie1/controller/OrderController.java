package com.dayuanit.movie.movie1.controller;

import com.dayuanit.movie.movie1.dto.ResponseDto;
import com.dayuanit.movie.movie1.entity.Film;
import com.dayuanit.movie.movie1.entity.Order;
import com.dayuanit.movie.movie1.handler.AlipayHandle;
import com.dayuanit.movie.movie1.service.FilmService;
import com.dayuanit.movie.movie1.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.Map;

@Controller
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private FilmService filmService;

    @Autowired
    private AlipayHandle alipayHandle;

    @RequestMapping(value = "/pay/{filmId}/{cinemaId}/{scheduleId}")
    @ResponseBody
    public ResponseDto pay(@PathVariable int filmId , @PathVariable int cinemaId, @PathVariable int scheduleId, String seatInfo){

        //TODO 生成订单 (存在问题 ，订单重复了，一个座位可以多次购买) 检验订单是否已存在
        Order order = orderService.createOrder(cinemaId, filmId, scheduleId, seatInfo);

        Film film = filmService.getFilm(filmId);

        //生成支付URL地址
        // payhub alipay代理网关
        String form = alipayHandle.createPayForm(order, film);
        return ResponseDto.success(form);
    }


    // 支付完成后 ，异步通知的网址
    @RequestMapping(value = "/pay/alipayNotify")
    @ResponseBody
    public void alipayNotify(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("alipay notify...");

        String username = request.getParameter("username");// 得到参数
        String[] usernames = request.getParameterValues("username"); // 得到参数是username的数组

        // 支付宝异步通知
        Map<String, String[]> parameterMap = request.getParameterMap();

        try {
            alipayHandle.processAliNotify(parameterMap);

            try (OutputStream os = response.getOutputStream()){
                os.write("success".getBytes());
                os.flush();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
