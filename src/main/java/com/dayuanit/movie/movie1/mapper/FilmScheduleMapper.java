package com.dayuanit.movie.movie1.mapper;

import com.dayuanit.movie.movie1.entity.FilmSchedule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FilmScheduleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FilmSchedule record);

    int insertSelective(FilmSchedule record);

    FilmSchedule selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FilmSchedule record);

    int updateByPrimaryKey(FilmSchedule record);

    List<FilmSchedule> listScedule(@Param("cinemaCode") String cinemaCode, @Param("filmCode")String filmCode);

    FilmSchedule getFilmSchedule(@Param("filmCode") String filmCode,
                                 @Param("cinemaCode")String cinemaCode,
                                 @Param("showTime")String showTime);
}