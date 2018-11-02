package com.maciejwozny.nextbikeplanner.net;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class DataDownloader extends AsyncTask<Void, Void, String> {
    private static final String mURL = "https://api.nextbike.net/maps/nextbike-live.json?city=148";
    private static final String TAG = "DataDownloader";

    private String downloadFile(String urlTxt) {
        try {
            URL url = new URL(urlTxt);
            URLConnection connection = url.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line;

            while ((line = reader.readLine()) != null) {
                buffer.append(line+"\n");
            }

            Log.d(TAG, "Downloaded! " + buffer.substring(0, 100) + "...");
            return buffer.toString();
        } catch (IOException e) {
            Log.d(TAG, "IOError: '" + e.toString() + "'");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected String doInBackground(Void... voids) {
        return downloadFile(mURL);
    }
}
