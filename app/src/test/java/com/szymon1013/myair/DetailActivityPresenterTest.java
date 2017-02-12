package com.szymon1013.myair;

import com.szymon1013.myair.data.DataDownloader;
import com.szymon1013.myair.data.IDBManager;
import com.szymon1013.myair.data.StationDetail;
import com.szymon1013.myair.data.StationDetailResponse;
import com.szymon1013.myair.presenter.DetailActivityPresenter;
import com.szymon1013.myair.view.DetailActivityView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DetailActivityPresenterTest {

    private DetailActivityPresenter presenter;
    private StationDetailResponse STATION_DETAIL = new StationDetailResponse();
    private StationDetailResponse INVALID_STATION_DETAIL = new StationDetailResponse();
    private StationDetail content = new StationDetail();

    @Mock
    DetailActivityView detailActivityView;
    @Mock
    DataDownloader dataDownloader;
    @Mock
    IDBManager mIDBManager;


    @Before
    public void setUp() {
        presenter = new DetailActivityPresenter(dataDownloader, detailActivityView, Schedulers.trampoline(),mIDBManager);
        RxJavaPlugins.setIoSchedulerHandler(new Function<Scheduler, Scheduler>() {
            @Override
            public Scheduler apply(Scheduler scheduler) throws Exception {
                return Schedulers.trampoline();
            }
        });
        STATION_DETAIL.setData(content);
    }

    @After
    public void cleanUp() {
        RxJavaPlugins.reset();
    }

    @Test
    public void shouldShowContent() {
        when(dataDownloader.getStationDetailSingle(1)).thenReturn(Single.just(STATION_DETAIL));
        presenter.loadStations(1);
        verify(detailActivityView).showDetails(content);
    }

    @Test
    public void shouldShowError() {
        when(dataDownloader.getStationDetailSingle(1)).thenReturn(Single.just(INVALID_STATION_DETAIL));
        presenter.loadStations(1);
        verify(detailActivityView).showError();
    }

    @Test
    public void shouldShowErrorSecondScenario() {
        when(dataDownloader.getStationDetailSingle(1)).thenReturn(Single.<StationDetailResponse>error(new Throwable("boom")));
        presenter.loadStations(1);
        verify(detailActivityView).showError();
    }

    @Test
    public void shouldShowErrorInvalidParam() {
        presenter.loadStations(-1);
        verify(detailActivityView).showError();
    }

}