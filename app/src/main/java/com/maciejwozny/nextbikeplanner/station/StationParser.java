package com.maciejwozny.nextbikeplanner.station;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StationParser {
    private final static String TAG = "StationParser";

    public ArrayList<IStation> parse(String jsonText) {
        Log.d(TAG, jsonText);
        ArrayList<IStation> stationList = new ArrayList<>();
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
