package com.szymon1013.myair.network;

import com.szymon1013.myair.data.DataDownloader;
import com.szymon1013.myair.data.StationDetailResponse;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Retrofit;


public class AirDataDownloader implements DataDownloader {
    private Retrofit mRetrofit;

    public AirDataDownloader(Retrofit retrofit) {
        mRetrofit = retrofit;
    }


    @Override
    public Single<StationDetailResponse> getStationDetailSingle(int id) {
        StationService station = mRetrofit.create(StationService.class);
        return station.getDetailObservable("@" + id);
    }


    public Observable<StationDetailResponse> getStationDetailObservable(int id) {
        StationService station = mRetrofit.create(StationService.class);
        return station.getDetailObservable2("@" + id);
    }
}
