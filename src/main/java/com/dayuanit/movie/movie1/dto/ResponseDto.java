package com.dayuanit.movie.movie1.dto;

public class ResponseDto {
    public static final int successCode = 100;
    public static final int errorCode = 200;
    public static final int reLoginCode = 888;

    private int codeNum = successCode;
    private String message;
    private Object data;

    private ResponseDto() {

    }

    private ResponseDto(int codeNum) {
        this.codeNum = codeNum;
    }

    private ResponseDto(int code, String message) {
        this.codeNum = code;
        this.message = message;
    }

    private ResponseDto(Object data) {
        this.data = data;
    }


    public static ResponseDto reLogin() {
        return new ResponseDto(reLoginCode,"重新登录");
    }

    public static ResponseDto success() {
        return new ResponseDto();
    }

    public static ResponseDto success(Object data) {
        return new ResponseDto(data);
    }

    public static ResponseDto fail(String message) {
        return new ResponseDto(errorCode,message);
    }

    public int getCodeNum() {
        return codeNum;
    }

    public void setCodeNum(int codeNum) {
        this.codeNum = codeNum;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
