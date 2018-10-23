package com.maciejwozny.nextbikeplanner.net;

import android.content.Context;
import android.util.Log;

import com.maciejwozny.nextbikeplanner.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DataDownloader {
    private static final String URL = "https://api.nextbike.net/maps/nextbike-live.json?city=148";
    private static final String TAG = "DataDownloader";
    private Context context;

    public DataDownloader(Context context) {
        this.context = context;
    }

    public String downloadFile() {
        //TODO implement
        try {
            Log.d(TAG, context.getAssets().getLocales()[0]);
            InputStream stream = context.getResources().openRawResource(R.raw.nextbike_wroclaw);
            byte[] buffer = new byte[stream.available()];
            //read the text file as a stream, into the buffer
            stream.read(buffer);
            //create a output stream to write the buffer into
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            //write this buffer to the output stream
            outputStream.write(buffer);
            //Close the Input and Output streams
            outputStream.close();
            stream.close();

            //return the output stream as a String
            Log.d(TAG, "'" + outputStream.toString() + "'");
            return outputStream.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
