package com.szymon1013.myair.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class StationID extends RealmObject {
    @PrimaryKey
    Integer id;

    public StationID(Integer id) {
        this.id = id;
    }

    public StationID() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
