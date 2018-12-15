package com.maciejwozny.nextbikeplanner.net;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class DataDownloader extends AsyncTask<String, Void, String> {
    private static final String TAG = "DataDownloader";

    private String downloadFile(String urlTxt) {
        try {
            URL url = new URL(urlTxt);
            URLConnection connection = url.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            StringBuilder builder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }

            Log.d(TAG, "Downloaded! " + builder.substring(0, 100) + "...");
            return builder.toString();
        } catch (IOException e) {
            Log.e(TAG, "IOError: '" + e.toString() + "'");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected String doInBackground(String... url) {
        if (url.length > 0)
            return downloadFile(url[0]);
        return null;
    }
}
