package com.ada.common.util;


/**
 * Created by jack
 * @author jack
 */
public class AdaResponse<T> {
    private static final int CODE_SUCCESS = 200;

    private static final int CODE_FAIL = 500;

    private static final int CODE_ERROR = 500;

    private static final int CODE_NO_LOGIN = 300;

    private int code;

    private String msg;

    private T data;

    public AdaResponse(){

    }

    public AdaResponse(int code, String msg, T data) {
        this.setCode(code);
        this.setMsg(msg);
        this.setData(data);
    }

    public static <T> AdaResponse<T> success() {
        return new AdaResponse<T>(CODE_SUCCESS, "success", null);
    }

    public static <T> AdaResponse<T> success(String message) {
        return new AdaResponse<T>(CODE_SUCCESS, message, null);
    }

    public static <T> AdaResponse<T> success(T data) {
        return new AdaResponse<T>(CODE_SUCCESS, "success", data);
    }

    public static <T> AdaResponse<T> success(String message, T data) {
        return new AdaResponse<T>(CODE_SUCCESS, message, data);
    }

    public static <T> AdaResponse<T> error() {
        return new AdaResponse<T>(CODE_ERROR, "fail", null);
    }

    public static <T> AdaResponse<T> error(String message) {
        return new AdaResponse<T>(CODE_ERROR, message, null);
    }

    public static <T> AdaResponse<T> error(T data) {
        return new AdaResponse<T>(CODE_ERROR, "fail", data);
    }

    public static <T> AdaResponse<T> error(String message, T data) {
        return new AdaResponse<T>(CODE_ERROR, message, data);
    }

    public static <T> AdaResponse<T> badrequest() {
        return new AdaResponse<T>(CODE_FAIL, "no identifier arguments", null);
    }

    public static <T> AdaResponse<T> badrequest(String message) {
        return new AdaResponse<T>(CODE_FAIL, message, null);
    }

    public static <T> AdaResponse<T> badrequest(T data) {
        return new AdaResponse<T>(CODE_FAIL, "no identifier arguments", data);
    }

    public static <T> AdaResponse<T> badrequest(String message, T data) {
        return new AdaResponse<T>(CODE_FAIL, message, data);
    }

    public static <T> AdaResponse<T> noLogin(String message) {
        return new AdaResponse<T>(CODE_NO_LOGIN, message, null);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
