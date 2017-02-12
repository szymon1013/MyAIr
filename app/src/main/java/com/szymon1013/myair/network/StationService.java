package com.szymon1013.myair.network;

import com.szymon1013.myair.data.StationDetailResponse;
import com.szymon1013.myair.data.StationResponse;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface StationService {
    String endpoint = "/feed/{station}/";

    @GET(endpoint)
    Single<StationDetailResponse> getDetailObservable(@Path("station") String station);

    @GET(endpoint)
    Observable<StationDetailResponse> getDetailObservable2(@Path("station") String station);
}
