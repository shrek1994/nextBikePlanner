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

public class StationReader {
    private final static String TAG = "StationReader";
    private Context context;
    private StationParser stationParser;

    public StationReader(Context context, StationParser stationParser) {
        this.context = context;
        this.stationParser = stationParser;
    }

    public ArrayList<IStation> readStation() {
        StringBuffer buffer = new StringBuffer();
        ArrayList<IStation> stationList = new ArrayList<>();
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

        return stationParser.parse(jsonText);
    }
}
