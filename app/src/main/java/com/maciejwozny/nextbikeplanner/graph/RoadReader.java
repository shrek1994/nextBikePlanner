package com.maciejwozny.nextbikeplanner.graph;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.maciejwozny.nextbikeplanner.R;

import org.osmdroid.bonuspack.routing.Road;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class RoadReader {
    private static final String TAG = "RoadReader";
    private Context context;
    private List<StationEdge> cachedStationEdges = null;

    public RoadReader(Context context) {
        this.context = context;
    }

    public Road getRoad(StationVertex source, StationVertex destination) {
        readGraphEdges();

        Road road = null;
        for (StationEdge edge: cachedStationEdges) {
            if (edge.getSource().equals(source) && edge.getDestination().equals(destination)) {
                Log.d(TAG, "found road in file: " + edge.toString());
                road = edge.getRoad();
                break;
            }
            if (edge.getDestination().equals(source) && edge.getSource().equals(destination)) {
                Log.d(TAG, "found road in file: " + edge.toString());
                road = edge.getRoad();
                break;
            }
        }
        return road;
    }

    private List<StationEdge> readGraphEdges() {
        if (cachedStationEdges != null) {
            Log.d(TAG, "readGraphEdges(): return from cached");
            return cachedStationEdges;
        }

        Log.d(TAG, "readGraphEdges from resources");
        InputStream stream = context.getResources().openRawResource(R.raw.edges_wroclaw);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        Gson gson = new Gson();
        StationEdge[] edges = gson.fromJson(reader, StationEdge[].class);
        Log.d(TAG, "read " + edges.length + " edges");
        cachedStationEdges = Arrays.asList(edges);
        return cachedStationEdges;
    }
}
