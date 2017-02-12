package com.szymon1013.myair.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.szymon1013.myair.MyAirDBHelper;
import com.szymon1013.myair.R;
import com.szymon1013.myair.adapter.StationsListAdapter;
import com.szymon1013.myair.aplication.MyAirApplication;
import com.szymon1013.myair.data.StationDetailResponse;
import com.szymon1013.myair.databinding.FragmentStationListBinding;
import com.szymon1013.myair.network.AirDataDownloader;
import com.szymon1013.myair.presenter.StationListPresenter;
import com.szymon1013.myair.view.StationListView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;


public class StationList extends Fragment implements StationListView, View.OnClickListener {

    public static String TAG = StationList.class.getClass().getSimpleName().toString();
    private FragmentStationListBinding binding;
    private RecyclerView mSubscribedList;
    private StationsListAdapter mListAdapter;
    @Inject
    AirDataDownloader mAirDataDownloader;
    private StationListPresenter mStationListPresenter;
    private FrameLayout mLoadingView;
    private FrameLayout mErrorView;
    private LinearLayout mNoItemToShowView;
    private Button mGoToMapButton;
    private IStationListInteraction mIStationListInteraction;

    public StationList() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_station_list, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
    }

    private void initUI() {
        mListAdapter = new StationsListAdapter(null);
        mSubscribedList = binding.subscribedStations;
        mLoadingView = binding.loading;
        mErrorView = binding.error;
        mNoItemToShowView = binding.noItemToShow;
        mGoToMapButton = binding.goToMap;
        mGoToMapButton.setOnClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mSubscribedList.setLayoutManager(linearLayoutManager);
        mSubscribedList.setAdapter(mListAdapter);
        mListAdapter.notifyDataSetChanged();
        mStationListPresenter = new StationListPresenter(this, mAirDataDownloader, AndroidSchedulers.mainThread(), new MyAirDBHelper());
    }

    @Override
    public void onResume() {
        super.onResume();
        mStationListPresenter.loadSubscribedStations();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = getActivity();
        ((MyAirApplication) activity.getApplication()).getNetComponent().inject(this);

        try {
            mIStationListInteraction = (IStationListInteraction) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement IStationListInteraction");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mIStationListInteraction = null;
    }


    @Override
    public void showList(List<StationDetailResponse> stations) {
        mListAdapter.setData(stations);
        mLoadingView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError() {
        mErrorView.setVisibility(View.INVISIBLE);

    }

    @Override
    public void showNoItemToShow() {
        mLoadingView.setVisibility(View.INVISIBLE);
        mNoItemToShowView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading() {
        mLoadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        mIStationListInteraction.goToMap();
    }

    public interface IStationListInteraction {
        void goToMap();
    }
}
