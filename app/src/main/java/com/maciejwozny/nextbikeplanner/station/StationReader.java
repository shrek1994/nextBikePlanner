package com.maciejwozny.nextbikeplanner.station;

import android.content.Context;
import android.util.Log;

import com.maciejwozny.nextbikeplanner.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StationReader extends IStationsBuilder {
    private final static String TAG = "StationReader";
    private Context context;

    public StationReader(IStationsBuilder nextStationBuilder, Context context) {
        super(nextStationBuilder);
        this.context = context;
    }

    @Override
    String getStation() {
        StringBuilder builder = new StringBuilder();
        try {
            InputStream stream = context.getResources().openRawResource(R.raw.nextbike_wroclaw);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }

            Log.d(TAG, "Read! " + builder.substring(0, 100) + "...");
        } catch (IOException e) {
            Log.e(TAG, "IOError: '" + e.toString() + "'");
            e.printStackTrace();
        }

        return builder.toString();
    }
}
