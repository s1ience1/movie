package com.dayuanit.movie.movie1.service;

import com.dayuanit.movie.movie1.entity.Area;
import com.dayuanit.movie.movie1.mapper.AreaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaService {

    @Autowired
    private AreaMapper areaMapper;

    public List<Area> listArea() {
        return areaMapper.listArea();
    }
}
