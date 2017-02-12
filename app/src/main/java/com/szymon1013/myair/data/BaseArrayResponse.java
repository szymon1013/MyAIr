package com.szymon1013.myair.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public abstract class BaseArrayResponse<T> {
    @SerializedName("status")
    String status;

    @SerializedName("data")
    ArrayList<T> data;

    public ArrayList<T> getData() {
        return data;
    }

    public void setData(ArrayList<T> data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
