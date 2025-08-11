package com.baicha.mywallpaper.model;

import com.baicha.mywallpaper.status.HttpStatus;
import lombok.Data;

@Data
public class Respons {
    private Integer Code;
    private String Message;
    private Object Data;

    public static Respons ok() {
        Respons respons = new Respons();
        respons.setCode(HttpStatus.SUCCESS);
        respons.setMessage("success");
        return respons;
    }

    public static Respons error() {
        Respons respons = new Respons();
        respons.setCode(HttpStatus.BAD_REQUEST);
        respons.setMessage("error");
        return respons;
    }

    public static Respons ok(Object data) {
        Respons respons = ok();
        respons.setData(data);
        return respons;
    }

    public static Respons ok(String message) {
        Respons respons = new Respons();
        respons.setCode(HttpStatus.SUCCESS);
        respons.setMessage(message);
        return respons;
    }

    public static Respons ok(String message, Object data) {
        Respons respons = ok(message);
        respons.setData(data);
        return respons;
    }

    public static Respons error(Integer code, String message) {
        Respons respons = new Respons();
        respons.setCode(code);
        respons.setMessage(message);
        return respons;
    }

    public static Respons error(String message) {
        Respons respons = new Respons();
        respons.setCode(HttpStatus.BAD_REQUEST);
        respons.setMessage(message);
        return respons;
    }
}
