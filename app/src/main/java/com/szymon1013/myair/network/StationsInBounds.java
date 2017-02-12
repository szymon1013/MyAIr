package com.szymon1013.myair.network;

import com.szymon1013.myair.data.StationResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StationsInBounds {
    String endpoint = "/map/bounds/";
    String LATLNG = "latlng";

    @GET(endpoint)

    Call<StationResponse> getStations(@Query(LATLNG) String orde);
}
