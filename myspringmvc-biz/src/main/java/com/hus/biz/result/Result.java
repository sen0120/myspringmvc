package com.hus.biz.result;

/**
 * Created by fanyun on 16/10/24.
 */


import java.io.Serializable;


public class Result<T> implements Serializable {

    private static final long serialVersionUID = 8024930945916881379L;
    /* 结果数据 */
    private T data;
    /* 状态码 */
    private int code = 0;
    /* 状态描述辅助信息 */
    private String errorMsg;
    // 交互token
    private String token;

    private Result() {
    }

    private Result(int code, String errorMsg, T data) {
        this.data = data;
        this.code = code;
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    //是否处理成功, 意味着没有业务失败和系统失败
    public boolean isSuccess() {
        return code == 0;
    }

    //是否处理失败，包括业务异常和系统异常
    public boolean isFailed() {
        return !isSuccess();
    }

    public static Result buidSucc() {
        return new Result(0, null, null);
    }

    public static <T> Result<T> buidSucc(T data) {
        return new Result(0, null, data);
    }

    public static Result buidFail(int code, String errorMsg) {
        return new Result(code, errorMsg, null);
    }

    public static Result buidBusinessFail(String errorMsg) {
        return new Result(6, errorMsg, null);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
