package com.szymon1013.myair.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StationDetail {
    @SerializedName("idx")
    Integer idx;

    @SerializedName("aqi")
    Integer aqi;

    @SerializedName("time")
    StationTime time;

    @SerializedName("city")
    CityDetail city;

    @SerializedName("attributions")
    ArrayList<Attribution> attributions;

    @SerializedName("iaqi")
    Iaqi iaqi;

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public Integer getAqi() {
        return aqi;
    }

    public void setAqi(Integer aqi) {
        this.aqi = aqi;
    }

    public StationTime getTime() {
        return time;
    }

    public void setTime(StationTime time) {
        this.time = time;
    }

    public CityDetail getCity() {
        return city;
    }

    public void setCity(CityDetail city) {
        this.city = city;
    }

    public ArrayList<Attribution> getAttributions() {
        return attributions;
    }

    public void setAttributions(ArrayList<Attribution> attributions) {
        this.attributions = attributions;
    }

    public Iaqi getIaqi() {
        return iaqi;
    }

    public void setIaqi(Iaqi iaqi) {
        this.iaqi = iaqi;
    }
}
