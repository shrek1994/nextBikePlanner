package com.maciejwozny.nextbikeplanner.station;

import android.util.Log;

import com.maciejwozny.nextbikeplanner.net.DataDownloader;

import java.util.concurrent.ExecutionException;

public class StationDownloader extends IStationsBuilder {
    private final static String TAG = "StationDownloader";
    private static final String URL = "https://api.nextbike.net/maps/nextbike-live.json?city=148";
    private DataDownloader dataDownloader;

    public StationDownloader(IStationsBuilder nextStationBuilder, DataDownloader dataDownloader) {
        super(nextStationBuilder);
        this.dataDownloader = dataDownloader;
    }

    @Override
    String getStation() {
        String jsonText = null;
        try {
            jsonText = dataDownloader.execute(URL).get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
        if (jsonText == null) {
            Log.w(TAG, "No internet connection!");
            return null;
        }
        return jsonText;
    }
}
