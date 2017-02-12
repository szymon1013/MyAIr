package com.szymon1013.myair;

import com.szymon1013.myair.activity.DetailActivity;
import com.szymon1013.myair.fragment.AirStationMap;
import com.szymon1013.myair.fragment.StationList;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    void inject(DetailActivity activity);

    void inject(StationList fragment);

    void inject(AirStationMap mapFragment);
}
