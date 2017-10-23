package com.lp.apiclient.entity;

import com.google.gson.annotations.SerializedName;

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

    private int count;
    private int start;
    private int total;
    private String title;

    @SerializedName("subjects")
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
