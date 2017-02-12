package com.szymon1013.myair.presenter;

import com.szymon1013.myair.data.DataDownloader;
import com.szymon1013.myair.data.IDBManager;
import com.szymon1013.myair.MyAirDBHelper;
import com.szymon1013.myair.data.StationDetailResponse;
import com.szymon1013.myair.view.DetailActivityView;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class DetailActivityPresenter implements MyAirDBHelper.ISubscribeAirStation {
    private final DataDownloader mDataDownloader;
    private final DetailActivityView mDetailActivityView;
    private Scheduler mMainScheduler;
    private IDBManager mIdbManager;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public DetailActivityPresenter(DataDownloader dataDownloader, DetailActivityView detailActivityView, Scheduler mainScheduler, IDBManager idbManager) {
        mDataDownloader = dataDownloader;
        mDetailActivityView = detailActivityView;
        mMainScheduler = mainScheduler;
        mIdbManager = idbManager;
    }

    public void loadStations(int stationId) {
        if (stationId > 0) {
            final Single<StationDetailResponse> stationDetail = mDataDownloader.getStationDetailSingle(stationId);
            mCompositeDisposable.add(stationDetail.
                    subscribeOn(Schedulers.io()).
                    observeOn(mMainScheduler).
                    subscribeWith(new DisposableSingleObserver<StationDetailResponse>() {
                        @Override
                        public void onSuccess(StationDetailResponse stationDetailResponse) {
                            if (stationDetailResponse != null && stationDetailResponse.getData() != null) {
                                mDetailActivityView.showDetails(stationDetailResponse.getData());
                            } else {
                                mDetailActivityView.showError();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            mDetailActivityView.showError();
                        }
                    }));
        } else {
            mDetailActivityView.showError();
        }

        mDetailActivityView.setSubscriptionState(mIdbManager.isSubscribed(stationId));
    }

    public void toggleSubscription(final int stationId) {
        if (mIdbManager.isSubscribed(stationId)) {
            mIdbManager.unsubscribeStation(stationId, this);
        } else {
            mIdbManager.subscribeStation(stationId, this);
        }
    }


    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void onStationSubscribed() {
        mDetailActivityView.setSubscriptionState(true);

    }

    @Override
    public void onUnsubscribeStation() {
        mDetailActivityView.setSubscriptionState(false);
    }
}
