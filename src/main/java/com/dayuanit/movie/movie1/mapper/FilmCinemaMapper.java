package com.dayuanit.movie.movie1.mapper;

import com.dayuanit.movie.movie1.entity.FilmCinema;

import java.util.List;

public interface FilmCinemaMapper {
    int insert(FilmCinema record);

    int insertSelective(FilmCinema record);

}