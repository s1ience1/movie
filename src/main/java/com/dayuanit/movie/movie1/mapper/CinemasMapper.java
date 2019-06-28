package com.dayuanit.movie.movie1.mapper;

import com.dayuanit.movie.movie1.entity.Cinemas;
import com.dayuanit.movie.movie1.entity.FilmCinema;

import java.util.List;

public interface CinemasMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cinemas record);

    int insertSelective(Cinemas record);

    Cinemas selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cinemas record);

    int updateByPrimaryKey(Cinemas record);

    List<Cinemas> listCinema(String cinemaCode);

    List<Cinemas> listFilmCinema(String filmCode, String areaCode);

}