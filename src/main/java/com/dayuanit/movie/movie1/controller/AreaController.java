package com.dayuanit.movie.movie1.controller;

import com.dayuanit.movie.movie1.dto.ResponseDto;
import com.dayuanit.movie.movie1.entity.Area;
import com.dayuanit.movie.movie1.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class AreaController extends BaseController {

    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "/area")
    @ResponseBody
    public ResponseDto listArea() {
        List<Area> areas = areaService.listArea();
        return ResponseDto.success(areas);
    }
}
