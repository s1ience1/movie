package com.dayuanit.movie.movie1.entity;

public class Cinemas {
    private Integer id;

    private String cinemaName;

    private String cinemaCode;

    private String picName;

    private String cinemaAddress;

    private String cinemaTelno;

    private String areaCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName == null ? null : cinemaName.trim();
    }

    public String getCinemaCode() {
        return cinemaCode;
    }

    public void setCinemaCode(String cinemaCode) {
        this.cinemaCode = cinemaCode == null ? null : cinemaCode.trim();
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName == null ? null : picName.trim();
    }

    public String getCinemaAddress() {
        return cinemaAddress;
    }

    public void setCinemaAddress(String cinemaAddress) {
        this.cinemaAddress = cinemaAddress == null ? null : cinemaAddress.trim();
    }

    public String getCinemaTelno() {
        return cinemaTelno;
    }

    public void setCinemaTelno(String cinemaTelno) {
        this.cinemaTelno = cinemaTelno == null ? null : cinemaTelno.trim();
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
    }
}