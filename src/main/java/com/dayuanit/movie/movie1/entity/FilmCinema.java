package com.dayuanit.movie.movie1.entity;

public class FilmCinema {
    private String filmCode;

    private String cinemaCode;

    public String getFilmCode() {
        return filmCode;
    }

    public void setFilmCode(String filmCode) {
        this.filmCode = filmCode == null ? null : filmCode.trim();
    }

    public String getCinemaCode() {
        return cinemaCode;
    }

    public void setCinemaCode(String cinemaCode) {
        this.cinemaCode = cinemaCode == null ? null : cinemaCode.trim();
    }
}