package com.maciejwozny.nextbikeplanner.graph;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.maciejwozny.nextbikeplanner.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class EdgeReader {
    private static final String TAG = "EdgeReader";
    private Context context;

    public EdgeReader(Context context) {
        this.context = context;
    }

    public List<IStationEdge> readGraphEdges() {
        Log.d(TAG, "readGraphEdges from resources");

        InputStream stream = context.getResources().openRawResource(R.raw.edges_wroclaw);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        Gson gson = new Gson();
        StationEdge[] edges = gson.fromJson(reader, StationEdge[].class);
        Log.d(TAG, "read " + edges.length + " edges");
        return Arrays.asList(edges);
    }
}
