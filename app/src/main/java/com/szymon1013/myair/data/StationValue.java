package com.szymon1013.myair.data;

import com.google.gson.annotations.SerializedName;

public class StationValue {

    @SerializedName("v")
    private String mValue;

    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        mValue = value;
    }
}
