package com.szymon1013.myair.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by szymon on 2017-02-14.
 */
public class CityDetail {

    @SerializedName("name")
    String mName;

    @SerializedName("url")
    String mUrl;

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
