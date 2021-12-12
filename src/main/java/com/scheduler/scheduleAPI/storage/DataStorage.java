package com.scheduler.scheduleAPI.storage;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.gson.Gson;

public class DataStorage {
    Gson gson = new Gson();

    static Datastore datastore = DatastoreOptions.newBuilder().setProjectId("bookmyschedule-329807").build().getService();
    private static DataStorage instance = null;

    private DataStorage() {
    }


    public static Datastore getInstance() {
        if (instance == null)
            instance = new DataStorage();
        return datastore;
    }

}