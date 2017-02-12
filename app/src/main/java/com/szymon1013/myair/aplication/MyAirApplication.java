package com.szymon1013.myair.aplication;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.szymon1013.myair.AppModule;
import com.szymon1013.myair.DaggerNetComponent;
import com.szymon1013.myair.NetComponent;
import com.szymon1013.myair.NetModule;

import io.realm.Realm;



public class MyAirApplication extends Application {
    public static final String WAQI_ADDRESS = "https://api.waqi.info";
    // todo obtain key  http://aqicn.org/data-platform/token/#/
    public static final String AIR_API_KEY = "KEY";

    private NetComponent mNetComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        Realm.init(this);
        mNetComponent = DaggerNetComponent.builder().
                appModule(new AppModule(this)).
                netModule(new NetModule(WAQI_ADDRESS, AIR_API_KEY)).
                build();
    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }
}
