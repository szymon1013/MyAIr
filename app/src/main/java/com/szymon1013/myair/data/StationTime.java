package com.szymon1013.myair.data;

import com.google.gson.annotations.SerializedName;

public class StationTime {

    @SerializedName("v")
    Long v;

    @SerializedName("s")
    String s;

    @SerializedName("tz")
    String tz;

    public String getTz() {
        return tz;
    }

    public void setTz(String tz) {
        this.tz = tz;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public Long getV() {
        return v;
    }

    public void setV(Long v) {
        this.v = v;
    }
}
