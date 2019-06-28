package com.dayuanit.movie.movie1.service;

import com.dayuanit.movie.movie1.entity.*;
import com.dayuanit.movie.movie1.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FilmService {

    @Autowired
    private FilmMapper filmMapper;

    @Autowired
    private FilmDetailInfoMapper filmDetailInfoMapper;

    @Autowired
    private CinemasMapper cinemasMapper;

    @Autowired
    private FilmCinemaMapper filmCinemaMapper;

    @Autowired
    private FilmScheduleMapper filmScheduleMapper;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Value("${pic.ip.port}")
    private String picUrl;



    public List<Film> listFilms() {
        List<Film> films = filmMapper.listFilms();
        return films;
    }

    public Film getFilm(int filmId) {
        Film film = filmMapper.selectByPrimaryKey(filmId);
        return film;
    }

    public Map<String,Object> loadFilmActor(String filmCode) {
        List<FilmDetailInfo> dircet = filmDetailInfoMapper.loadActor(filmCode, 0);

        List<FilmDetailInfo> actor = filmDetailInfoMapper.loadActor(filmCode, 1);

        Map<String,Object> map = new HashMap<>();
        map.put("direct",dircet);
        map.put("actor",actor);

        return map;
    }



    public List<Cinemas> listFilmAllCineam(String filmCode,String areaId) {

        List<Cinemas> cinemas = cinemasMapper.listFilmCinema(filmCode, areaId);

        return cinemas;
    }

    public Cinemas getCinemaById(int cinemaId) {
        Cinemas cinemas = cinemasMapper.selectByPrimaryKey(cinemaId);
        cinemas.setPicName(picUrl+cinemas.getCinemaName());
        return cinemas;
    }

    public List<FilmSchedule> listFilmSchedule(int cinemaId,int filmId) {
        Cinemas cinemas = cinemasMapper.selectByPrimaryKey(cinemaId);
        Film film = filmMapper.selectByPrimaryKey(filmId);
        List<FilmSchedule> filmSchedules = filmScheduleMapper.listScedule(cinemas.getCinemaCode(), film.getFilmCode());
        return filmSchedules;
    }

    public List<Film> listCinemaFilms(int cinemaId) {
        String cinemaCode = cinemasMapper.selectByPrimaryKey(cinemaId).getCinemaCode();
        List<Film> films = filmMapper.listCinemaFilms(cinemaCode);
        return films;
    }

    public FilmSchedule getFilmSchduel(int scheduelId) {
        FilmSchedule filmSchedule = filmScheduleMapper.selectByPrimaryKey(scheduelId);

        return filmSchedule;
    }

    public List<OrderInfo> listAlreadyBuyTicket(int scheduelId) {
        return orderInfoMapper.listAlreadyBuyTicket(scheduelId);
    }


}
