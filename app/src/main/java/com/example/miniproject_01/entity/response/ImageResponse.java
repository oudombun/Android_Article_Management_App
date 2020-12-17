package com.example.miniproject_01.entity.response;

import com.google.gson.annotations.SerializedName;

public class ImageResponse {
    @SerializedName("message")
    private String msg;
    @SerializedName("data")
    private String data;

    @Override
    public String toString() {
        return "ImageResponse{" +
                "msg='" + msg + '\'' +
                ", data='" + data + '\'' +
                '}';
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
