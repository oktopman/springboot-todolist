package me.oktop.springboottodolist.web.dto;

import lombok.Getter;

@Getter
public class ResponseDto<T> {

    T data;
    private String code;

    public ResponseDto(T data) {
        this.data = data;
        this.code = "200";
    }

    public ResponseDto() {
        this.code = "200";
    }

    public static<T> ResponseDto success(T data) {
        return new ResponseDto<>(data);
    }

    public static ResponseDto success() {
        return new ResponseDto<>();
    }
}
