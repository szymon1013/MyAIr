package com.szymon1013.myair.data;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface DataDownloader {
    Single<StationDetailResponse> getStationDetailSingle(int id);

    Observable<StationDetailResponse> getStationDetailObservable(int id);
}
