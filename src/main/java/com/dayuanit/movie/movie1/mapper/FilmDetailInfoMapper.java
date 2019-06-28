package com.dayuanit.movie.movie1.mapper;

import com.dayuanit.movie.movie1.entity.FilmDetailInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FilmDetailInfoMapper {
    int insert(FilmDetailInfo record);

    int insertSelective(FilmDetailInfo record);

    List<FilmDetailInfo> loadActor(@Param("filmCode")String filmCode, @Param("actorType") Integer actorType);
}