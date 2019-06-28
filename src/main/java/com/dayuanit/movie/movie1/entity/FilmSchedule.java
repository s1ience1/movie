package com.dayuanit.movie.movie1.entity;

public class FilmSchedule {
    private Integer id;

    private String filmCode;

    private String cineamCode;

    private String showTime;

    private String language;

    private String hall;

    private String price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilmCode() {
        return filmCode;
    }

    public void setFilmCode(String filmCode) {
        this.filmCode = filmCode == null ? null : filmCode.trim();
    }

    public String getCineamCode() {
        return cineamCode;
    }

    public void setCineamCode(String cineamCode) {
        this.cineamCode = cineamCode == null ? null : cineamCode.trim();
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime == null ? null : showTime.trim();
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language == null ? null : language.trim();
    }

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall == null ? null : hall.trim();
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price == null ? null : price.trim();
    }
}