package com.szymon1013.myair.data;

import com.szymon1013.myair.MyAirDBHelper;

import java.util.List;

public interface IDBManager {
    List<StationID> getSubscribedStationsIDs();

    void unsubscribeStation(final int stationId, final MyAirDBHelper.ISubscribeAirStation subscribeAirStation);

    void subscribeStation(final int stationId, final MyAirDBHelper.ISubscribeAirStation subscribeAirStation);

    boolean isSubscribed(int stationId);

}
