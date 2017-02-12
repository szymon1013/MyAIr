package com.szymon1013.myair.data;

import com.google.gson.annotations.SerializedName;

public class Iaqi {

    @SerializedName("pm25")
    StationValue mPm25;

    @SerializedName("pm10")
    StationValue mPm10;

    @SerializedName("no2")
    StationValue mNo2;

    @SerializedName("co")
    StationValue mCo;

    @SerializedName("t")
    StationValue mT;

    @SerializedName("p")
    StationValue mP;

    @SerializedName("h")
    StationValue mH;

    public StationValue getH() {
        return mH;
    }

    public void setH(StationValue h) {
        mH = h;
    }

    public StationValue getP() {
        return mP;
    }

    public void setP(StationValue p) {
        mP = p;
    }

    public StationValue getT() {
        return mT;
    }

    public void setT(StationValue t) {
        mT = t;
    }

    public StationValue getCo() {
        return mCo;
    }

    public void setCo(StationValue CO) {
        mCo = CO;
    }

    public StationValue getNo2() {
        return mNo2;
    }

    public void setNo2(StationValue no2) {
        mNo2 = no2;
    }

    public StationValue getPm10() {
        return mPm10;
    }

    public void setPm10(StationValue pm10) {
        mPm10 = pm10;
    }

    public StationValue getPm25() {
        return mPm25;
    }

    public void setPm25(StationValue pm25) {
        mPm25 = pm25;
    }
}
