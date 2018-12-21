package com.maciejwozny.nextbikeplanner.graph;

import android.content.Context;
import android.util.Log;

import org.osmdroid.bonuspack.routing.Road;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class EdgeFactory {
    private static final String TAG = "EdgeFactory";
    private static final double TWENTY_MINUTES = 20 * 60;
    private static final double AVG_SPEED_KM_H = 12;
    private static final double AVG_SPEED_M_S = AVG_SPEED_KM_H * 1000 / (60 * 60);
    private Context context;
    private RoadReader roadReader;

    public EdgeFactory(Context context, RoadReader roadReader) {
        this.context = context;
        this.roadReader = roadReader;
    }

    /*
     * Creating edge if road duration is lower than 20 minutes
     */
    public StationEdge create(StationVertex source, StationVertex destination) {
        double distance = source.getGeoPoint().distanceToAsDouble(destination.getGeoPoint());
        if (distance > 4000) {
            return null;
        }

        Road road = roadReader.getRoad(source, destination);

        if (road == null) {
            try {
                Log.d(TAG, "trying to download road for " + source.getName()
                        + " and " + destination.getName());
                RoadDownloader roadDownloader = new RoadDownloader(context);
                road = roadDownloader.execute(
                        new ArrayList<>(
                                Arrays.asList(source.getGeoPoint(), destination.getGeoPoint())))
                        .get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                Log.e(TAG, "error: " + e.toString());
            }
        }

        if (road == null) {
            return null;
        }

        if (road.mDuration > TWENTY_MINUTES) {
            return null;
        }

        return new StationEdge(source, destination, road);
    }
}
