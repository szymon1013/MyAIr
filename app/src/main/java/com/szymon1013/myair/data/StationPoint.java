package com.szymon1013.myair.data;

import com.google.gson.annotations.SerializedName;

public class StationPoint {

    @SerializedName("lat")
    Double lat;
    @SerializedName("lon")
    Double lon;
    @SerializedName("uid")
    Integer uid;
    @SerializedName("aqi")
    String aqi;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }
}
