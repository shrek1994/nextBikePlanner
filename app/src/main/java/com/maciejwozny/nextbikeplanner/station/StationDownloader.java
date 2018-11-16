package com.maciejwozny.nextbikeplanner.station;

import android.util.Log;

import com.maciejwozny.nextbikeplanner.net.DataDownloader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class StationDownloader implements IStationDownloader {
    private final static String TAG = "StationDownloader";
    private static final String URL = "https://api.nextbike.net/maps/nextbike-live.json?city=148";
    private DataDownloader dataDownloader;
    private StationParser stationParser;

    public StationDownloader(DataDownloader dataDownloader, StationParser stationParser) {
        this.dataDownloader = dataDownloader;
        this.stationParser = stationParser;
    }

    @Override
    public ArrayList<IStation> downloadStations() {
        String jsonText = null;
        try {
            jsonText = dataDownloader.execute(URL).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        if (jsonText == null) {
            Log.e(TAG, "No internet connection!");
            return null;
        }

        return stationParser.parse(jsonText);
    }
}
