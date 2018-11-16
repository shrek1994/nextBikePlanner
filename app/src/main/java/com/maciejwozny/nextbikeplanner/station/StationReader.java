package com.maciejwozny.nextbikeplanner.station;

import android.content.Context;
import android.util.Log;

import com.maciejwozny.nextbikeplanner.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class StationReader extends IStationsBuilder {
    private final static String TAG = "StationReader";
    private Context context;

    public StationReader(IStationsBuilder nextStationBuilder, Context context) {
        super(nextStationBuilder);
        this.context = context;
    }

    @Override
    String getStation() {
        StringBuffer buffer = new StringBuffer();
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

        return buffer.toString();
    }
}
