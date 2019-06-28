package com.dayuanit.movie.movie1.mapper;

import com.dayuanit.movie.movie1.entity.Film;

import java.util.List;

public interface FilmMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Film record);

    int insertSelective(Film record);

    Film selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Film record);

    int updateByPrimaryKeyWithBLOBs(Film record);

    int updateByPrimaryKey(Film record);

    List<Film> listFilms();

    List<Film> listCinemaFilms(String cinemaCode);
}