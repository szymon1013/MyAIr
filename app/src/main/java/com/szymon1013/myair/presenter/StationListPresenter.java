package com.szymon1013.myair.presenter;


import com.szymon1013.myair.data.DataDownloader;
import com.szymon1013.myair.data.IDBManager;
import com.szymon1013.myair.data.StationDetailResponse;
import com.szymon1013.myair.data.StationID;
import com.szymon1013.myair.view.StationListView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class StationListPresenter {
    private StationListView mView;
    private DataDownloader mDownloader;
    private Scheduler mMainScheduler;
    private IDBManager mHelper;

    public StationListPresenter(StationListView view, DataDownloader downloader, Scheduler mainScheduler, IDBManager helper) {
        mView = view;
        mDownloader = downloader;
        mMainScheduler = mainScheduler;
        mHelper = helper;
    }

    public void loadSubscribedStations() {
        final ArrayList<StationDetailResponse> result = new ArrayList<>();
        List<StationID> subscribedStations = mHelper.getSubscribedStationsIDs();
        if (subscribedStations == null || subscribedStations.isEmpty()) {
            mView.showNoItemToShow();
            mView.showList(null);
        } else {
            mView.showLoading();
            List<Observable<StationDetailResponse>> stationsDetailObservableList = getStationsDetailObservableList(subscribedStations);
            Observable.merge(stationsDetailObservableList).
                    subscribeOn(Schedulers.io()).
                    observeOn(mMainScheduler).
                    subscribeWith(new Observer<StationDetailResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(StationDetailResponse value) {
                            result.add(value);
                        }

                        @Override
                        public void onError(Throwable e) {
                            mView.showError();
                        }

                        @Override
                        public void onComplete() {
                            mView.showList(result);
                        }
                    });

        }

    }


    private List<Observable<StationDetailResponse>> getStationsDetailObservableList(List<StationID> subscribedStations) {
        List<Observable<StationDetailResponse>> stationsDetailObservableList = new ArrayList();
        for (StationID stationID : subscribedStations) {
            int id = stationID.getId();
            stationsDetailObservableList.
                    add(mDownloader.getStationDetailObservable(id).
                            subscribeOn(Schedulers.io()));

        }
        return stationsDetailObservableList;
    }
}
