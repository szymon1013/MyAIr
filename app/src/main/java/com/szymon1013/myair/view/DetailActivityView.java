package com.szymon1013.myair.view;

import com.szymon1013.myair.data.StationDetail;

public interface DetailActivityView {
    void showDetails(StationDetail stationDetail);
    void showError();
    void setSubscriptionState(boolean isSubscribed);
}
