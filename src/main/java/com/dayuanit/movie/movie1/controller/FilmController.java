package com.dayuanit.movie.movie1.controller;

import com.dayuanit.movie.movie1.dto.ResponseDto;
import com.dayuanit.movie.movie1.entity.Cinemas;
import com.dayuanit.movie.movie1.entity.Film;
import com.dayuanit.movie.movie1.entity.FilmSchedule;
import com.dayuanit.movie.movie1.entity.OrderInfo;
import com.dayuanit.movie.movie1.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.*;

@Controller
public class FilmController extends BaseController {

    @Autowired
    private FilmService filmService;


    @RequestMapping(value = "/film/listFilms")
    @ResponseBody
    public ResponseDto listFilms() {
        List<Film> films = filmService.listFilms();
        return ResponseDto.success(films);

    }


    @RequestMapping(value = "/pic/{filmName}",method = RequestMethod.GET)
    public void showPic(@PathVariable String filmName, HttpServletResponse response) {

        response.setContentType("image/png");
        try (FileInputStream fis = new FileInputStream("/tmp1/film-pic/" + filmName+".png");
             OutputStream os = response.getOutputStream()) {
            byte[] buff = new byte[1024];
            int length = -1;
            while(-1 != (length = fis.read(buff))) {
                os.write(buff, 0, length);
                os.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequestMapping(value = "/pic2/{filmName}",method = RequestMethod.GET)
    public void showPic2(@PathVariable String filmName, HttpServletResponse response) {
        response.setContentType("image/png");
        try (FileInputStream fis = new FileInputStream("/tmp1/film-pic/" + filmName+".jpg");
             OutputStream os = response.getOutputStream()) {
            byte[] buff = new byte[1024];
            int length = -1;
            while(-1 != (length = fis.read(buff))) {
                os.write(buff, 0, length);
                os.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @RequestMapping(value = "/film/{filmId}")
    @ResponseBody
    public ResponseDto getFilm(@PathVariable int filmId) {

        Film film = filmService.getFilm(filmId);
        return ResponseDto.success(film);
    }

    @ResponseBody
    @RequestMapping(value = "/film/actor/{filmCode}")
    public ResponseDto listActors(@PathVariable String filmCode) {
        Map<String, Object> map = filmService.loadFilmActor(filmCode);
        return ResponseDto.success(map);
    }


    @ResponseBody
    @RequestMapping(value = "/filmCinema/{filmCode}/{areaId}")
    public ResponseDto listFilmCinema(@PathVariable String filmCode,@PathVariable String areaId) {
        List<Cinemas> cinemas = filmService.listFilmAllCineam(filmCode,areaId);
        return ResponseDto.success(cinemas);
    }

    @RequestMapping(value = "/cinema/{cinemaId}")
    @ResponseBody
    public ResponseDto cinemaInfo(@PathVariable int cinemaId){
        Cinemas cinema = filmService.getCinemaById(cinemaId);
        return ResponseDto.success(cinema);
    }


    @RequestMapping(value = "cinemaSchedule/{filmId}/{cinemaId}")
    @ResponseBody
    public ResponseDto cinemaSchedule(@PathVariable int filmId, @PathVariable int cinemaId){
        List<FilmSchedule> filmSchedules = filmService.listFilmSchedule(cinemaId, filmId);
        return ResponseDto.success(filmSchedules);
    }


    @RequestMapping(value = "cinema/listFilm/{cinemaId}")
    @ResponseBody
    public ResponseDto listFilm(@PathVariable int cinemaId){
        List<Film> films = filmService.listCinemaFilms(cinemaId);
        return ResponseDto.success(films);
    }


    @ResponseBody
    @RequestMapping(value = "/schedule/{scheduleId}")
    public ResponseDto getScheduel(@PathVariable int scheduleId) {

        FilmSchedule filmSchduel = filmService.getFilmSchduel(scheduleId);
        return ResponseDto.success(filmSchduel);
    }

    @ResponseBody
    @RequestMapping(value = "/film/alreadyBuyTicket/{scheduleId}")
    public ResponseDto alreadyBuyTicket(@PathVariable int scheduleId){
        List<OrderInfo> orderInfos = filmService.listAlreadyBuyTicket(scheduleId);
        return ResponseDto.success(orderInfos);
    }

}
