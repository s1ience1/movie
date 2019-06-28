package com.dayuanit.movie.movie1.controller;

import com.dayuanit.movie.movie1.entity.Order;
import com.dayuanit.movie.movie1.entity.OrderInfo;
import com.dayuanit.movie.movie1.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class PageController extends BaseController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/")
    public String toHome() {
        return "home";
    }

    @RequestMapping(value = "/toFilmInfo/{filmId}/{filmCode}")
    public String toFilmInfo(@PathVariable int filmId, @PathVariable String filmCode) {
        return "filmInfo";
    }


    @RequestMapping(value = "/film/toBuyTicket/{filmId}/{filmCode}")
    public String toBuyTicket(@PathVariable int filmId, @PathVariable String filmCode){
        return "FilmCinema";
    }


    @RequestMapping(value = "/film/toCinema/{cinemaId}/{filmId}")
    public String toCinema(@PathVariable int cinemaId,@PathVariable int filmId){
        return "toCinema";
    }



    @RequestMapping(value = "/toChooseSeat/{filmId}/{cinemaId}/{scheduleId}")
    public String toChooseSeat(@PathVariable int cinemaId,
                               @PathVariable int filmId,
                               @PathVariable int scheduleId){
        return "chooseSeat1";
    }

    //  '
    @RequestMapping(value = "/pay/{cinemaId}/{filmId}/{scheduleId}/{seatInfo}")
    public String pay(@PathVariable int cinemaId,
                               @PathVariable int filmId,
                               @PathVariable int scheduleId,@PathVariable String seatInfo){
        System.out.println(seatInfo);
        return "pay";
    }

    @RequestMapping(value = "/pay/{orderId}")
    public String returnUrl(@PathVariable int orderId, HttpServletRequest request){

        Order order = orderService.getOrder(orderId);
        List<OrderInfo> orderInfos = orderService.listOrderInfo(orderId);

        request.setAttribute("filmId",order.getFilmId());
        request.setAttribute("cinemaId",order.getCinemaId());
        request.setAttribute("scheduleId",order.getFilmScheduleId());

        StringBuilder sx = new StringBuilder();
        for (OrderInfo orderInfo : orderInfos) {
            sx.append(orderInfo.getSeatRow()).append("-").append(orderInfo.getSeatCol()).append(",");
        }
        sx.toString();
        request.setAttribute("seatInfo",sx.substring(0,sx.length()-1));

        return "orderInfo";
    }

}
