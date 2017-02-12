package com.szymon1013.myair.data;

import com.google.gson.annotations.SerializedName;


public abstract class BaseResponse<T> {
    @SerializedName("status")
    String status;

    @SerializedName("data")
    T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
