package com.szymon1013.myair.view;

import com.szymon1013.myair.data.StationDetail;
import com.szymon1013.myair.data.StationDetailResponse;

import java.util.List;

/**
 * Created by szymon on 2017-03-20.
 */

public interface StationListView {
    void showList(List<StationDetailResponse> stations);
    void showError();
    void showNoItemToShow();
    void showLoading();
}
