package com.maciejwozny.nextbikeplanner.memory;

import android.content.Context;
import android.util.Log;

import com.maciejwozny.nextbikeplanner.R;
import com.maciejwozny.nextbikeplanner.net.Station;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class StationReader {
    private final static String TAG = "StationReader";
    private Context context;

    public StationReader(Context context) {
        this.context = context;
    }

    public List<Station> readStation() {
        //TODO refactor - same code like in station downloader:

        StringBuffer buffer = new StringBuffer();
        List<Station> stationList = new ArrayList<>();
        try {
            InputStream stream = context.getResources().openRawResource(R.raw.nextbike_wroclaw);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            String line;

            while ((line = reader.readLine()) != null) {
                buffer.append(line+"\n");
            }

            Log.d(TAG, "Read! " + buffer.substring(0, 100) + "...");
        } catch (IOException e) {
            Log.e(TAG, "IOError: '" + e.toString() + "'");
            e.printStackTrace();
        }

        String jsonText = buffer.toString();

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
