package com.szymon1013.myair;

import com.szymon1013.myair.data.DataDownloader;
import com.szymon1013.myair.data.IDBManager;
import com.szymon1013.myair.data.StationDetailResponse;
import com.szymon1013.myair.data.StationID;
import com.szymon1013.myair.presenter.StationListPresenter;
import com.szymon1013.myair.view.StationListView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StationListPresenterTest {
    private StationDetailResponse STATION_DETAIL = new StationDetailResponse();
    private ArrayList<StationID> subscribedStationIDList;
    private ArrayList<StationID> subscribedStationIDEmptyList;

    private StationListPresenter presenter;

    @Mock
    StationListView stationListView;

    @Mock
    DataDownloader dataDownloader;

    @Mock
    IDBManager DBManager;

    @Captor
    ArgumentCaptor<ArrayList<StationDetailResponse>> captor;


    @Before
    public void setUp() {
        presenter = new StationListPresenter(stationListView, dataDownloader, Schedulers.trampoline(), DBManager);

        RxJavaPlugins.setIoSchedulerHandler(new Function<Scheduler, Scheduler>() {
            @Override
            public Scheduler apply(Scheduler scheduler) throws Exception {
                return Schedulers.trampoline();
            }
        });
        initData();
    }

    private void initData() {
        subscribedStationIDList = new ArrayList<>();
        subscribedStationIDList.add(new StationID(0));
        subscribedStationIDList.add(new StationID(1));
        subscribedStationIDList.add(new StationID(2));

        subscribedStationIDEmptyList = new ArrayList<>();
    }

    @After
    public void cleanUp() {
        RxJavaPlugins.reset();
    }


    @Test
    public void shouldShowContent() {
        ArrayList<StationDetailResponse> responses = new ArrayList<>();
        responses.add(STATION_DETAIL);
        when(dataDownloader.getStationDetailObservable(0)).thenReturn(Observable.just(STATION_DETAIL));
        when(dataDownloader.getStationDetailObservable(1)).thenReturn(Observable.just(STATION_DETAIL));
        when(dataDownloader.getStationDetailObservable(2)).thenReturn(Observable.just(STATION_DETAIL));

        when(DBManager.getSubscribedStationsIDs()).thenReturn(subscribedStationIDList);

        presenter.loadSubscribedStations();

        verify(stationListView).showList(captor.capture());

        ArrayList<StationDetailResponse> value = captor.getValue();

        assertEquals(3, value.size());

        assertEquals(true, value.get(0).equals(STATION_DETAIL));
        assertEquals(true, value.get(1).equals(STATION_DETAIL));
        assertEquals(true, value.get(2).equals(STATION_DETAIL));

    }

    @Test
    public void shouldShowNoItemToShow() {
        when(DBManager.getSubscribedStationsIDs()).thenReturn(subscribedStationIDEmptyList);
        presenter.loadSubscribedStations();
        verify(stationListView).showNoItemToShow();
    }

    @Test
    public void shouldShowError() {
        when(dataDownloader.getStationDetailObservable(0)).thenReturn(Observable.<StationDetailResponse>error(new Throwable("boom")));
        when(dataDownloader.getStationDetailObservable(1)).thenReturn(Observable.just(STATION_DETAIL));
        when(dataDownloader.getStationDetailObservable(2)).thenReturn(Observable.just(STATION_DETAIL));

        when(DBManager.getSubscribedStationsIDs()).thenReturn(subscribedStationIDList);
        presenter.loadSubscribedStations();
        verify(stationListView).showError();
    }
}

