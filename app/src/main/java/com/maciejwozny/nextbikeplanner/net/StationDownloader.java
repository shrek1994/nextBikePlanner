package com.maciejwozny.nextbikeplanner.net;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class StationDownloader implements IStationDownloader {
    private final static String TAG = "StationDownloader";
    private DataDownloader dataDownloader;

    public StationDownloader(DataDownloader dataDownloader) {
        this.dataDownloader = dataDownloader;
    }

    @Override
    public List<Station> downloadStations() {
        List<Station> stationList = new ArrayList<>();
        String jsonText = null;
        try {
            jsonText = dataDownloader.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if (jsonText == null) {
            Log.e(TAG, "No internet connection!");
            return null;
        }

        Log.d(TAG, jsonText);
        try {

            JSONObject json = new JSONObject(jsonText);
            JSONArray jsonCountries = json.getJSONArray("countries");
            JSONArray jsonCity = jsonCountries.getJSONObject(0).getJSONArray("cities");
            JSONArray jsonStations = jsonCity.getJSONObject(0).getJSONArray("places");
            for (int i = 0; i < jsonStations.length(); ++i) {
                JSONObject station = jsonStations.getJSONObject(i);
                String name = station.getString("name");
                double lat = station.getDouble("lat");
                double lng = station.getDouble("lng");
                int bikes = station.getInt("bikes");
                int number = station.getInt("number");
                int freeRacks = station.getInt("free_racks");

                stationList.add(new Station(name, number, bikes, freeRacks, lng, lat));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return stationList;
    }
}
