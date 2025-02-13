package com.example.exceptiontt.exception;

import lombok.Data;

@Data
public class Resp<T> {

    //服务端返回的错误码
    private int code = 200;
    //服务端返回的错误信息
    private String msg = "success";
    //我们服务端返回的数据
    private T data;

    private Resp(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Resp success(T data) {
        Resp resp = new Resp(200, "success", data);
        return resp;
    }

    public static <T> Resp success(String msg, T data) {
        Resp resp = new Resp(200, msg, data);
        return resp;
    }

    public static <T> Resp error(AppExceptionCodeMsg appExceptionCodeMsg) {
        Resp resp = new Resp(appExceptionCodeMsg.getCode(), appExceptionCodeMsg.getMsg(), null);
        return resp;
    }

    public static <T> Resp error(int code, String msg) {
        Resp resp = new Resp(code, msg, null);
        resp.setCode(code);
        resp.setMsg(msg);
        return resp;
    }

    public static <T> Resp error(String msg){
        return error(400,msg);
    }

    public Resp<T> addData(T value) {
        this.setData(value);
        return this;
    }

    public int getCode() {
        return code;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

}