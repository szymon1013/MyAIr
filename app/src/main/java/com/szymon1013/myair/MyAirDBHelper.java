package com.szymon1013.myair;

import com.szymon1013.myair.data.IDBManager;
import com.szymon1013.myair.data.StationID;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by szymon on 2017-03-20.
 */

public class MyAirDBHelper implements IDBManager {

    public static final String STATION_FIELD_NAME_ID = "id";

    @Override
    public List<StationID> getSubscribedStationsIDs() {
        Realm defaultInstance = Realm.getDefaultInstance();
        RealmResults<StationID> all = defaultInstance.where(StationID.class).findAll().sort(STATION_FIELD_NAME_ID);
        return all;
    }

    public void unsubscribeStation(final int stationId, final ISubscribeAirStation subscribeAirStation) {
        final Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(StationID.class).equalTo(STATION_FIELD_NAME_ID, stationId).findAll().deleteAllFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                subscribeAirStation.onUnsubscribeStation();
                realm.close();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                realm.close();
            }
        });

    }


    public void subscribeStation(final int stationId, final ISubscribeAirStation subscribeAirStation) {
        final Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(new StationID(stationId));
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                subscribeAirStation.onStationSubscribed();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                realm.close();
            }
        });

    }

    public boolean isSubscribed(int stationId) {
        final Realm realm = Realm.getDefaultInstance();
        StationID first = realm.where(StationID.class).equalTo(STATION_FIELD_NAME_ID, stationId).findFirst();
        if (first != null)
            return true;
        else
            return false;

    }

    public interface ISubscribeAirStation {
        void onStationSubscribed();

        void onUnsubscribeStation();
    }


}
