package com.szymon1013.myair.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.szymon1013.myair.presenter.DetailActivityPresenter;
import com.szymon1013.myair.MyAirDBHelper;
import com.szymon1013.myair.R;
import com.szymon1013.myair.aplication.MyAirApplication;
import com.szymon1013.myair.data.StationDetail;
import com.szymon1013.myair.databinding.DetailBinding;
import com.szymon1013.myair.network.AirDataDownloader;
import com.szymon1013.myair.view.DetailActivityView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class DetailActivity extends AppCompatActivity implements DetailActivityView, View.OnClickListener {

    private int mMapId;
    public static String MAP_DETAIL_ID = "map_detail_id";
    private DetailActivityPresenter mPresenter;
    private View loading;
    private View error;
    private DetailBinding detailBinding;
    private Button mSubscribeButton;

    @Inject
    AirDataDownloader airDataDownloader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MyAirApplication) getApplication()).getNetComponent().inject(this);

        detailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        mMapId = getIntent().getIntExtra(MAP_DETAIL_ID, -1);

        setUI();
        mPresenter = new DetailActivityPresenter(airDataDownloader, this, AndroidSchedulers.mainThread(), new MyAirDBHelper());
        mPresenter.loadStations(mMapId);
    }


    private void setUI() {
        mSubscribeButton = detailBinding.buttonTest;
        mSubscribeButton.setOnClickListener(this);

        loading = detailBinding.loading;
        error = detailBinding.error;

    }


    @Override
    public void showDetails(StationDetail stationDetail) {
        if (stationDetail != null) {
            detailBinding.setStation(stationDetail);
        }
        hideLoading();
    }

    @Override
    public void showError() {
        error.setVisibility(View.VISIBLE);
        hideLoading();
    }

    @Override
    public void setSubscriptionState(boolean subscriptionState) {
        if (subscriptionState) {
            mSubscribeButton.setText("unSubscribe");
        } else
            mSubscribeButton.setText("subscribe");
    }


    private void hideLoading() {
        loading.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.unsubscribe();
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.button_test:
                mPresenter.toggleSubscription(mMapId);
        }
    }

}
