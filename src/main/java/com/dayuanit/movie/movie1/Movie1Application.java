package com.dayuanit.movie.movie1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
//扫描mapper + 告诉xml的位置
@MapperScan("com.dayuanit.movie.movie1.mapper")
public class Movie1Application {

    public static void main(String[] args) {
        SpringApplication.run(Movie1Application.class, args);
    }

}
