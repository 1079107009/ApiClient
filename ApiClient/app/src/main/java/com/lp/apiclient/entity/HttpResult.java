package com.lp.apiclient.entity;

/**
 * @author LiPin
 * @date 2017/10/19 11:20
 * 描述：
 */

public class HttpResult<T> {

    public static final int FAILURE = 0;
    public static final int SUCCESS = 200;

    private int resultCode = 200;
    private String resultMessage;

    private T data;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
