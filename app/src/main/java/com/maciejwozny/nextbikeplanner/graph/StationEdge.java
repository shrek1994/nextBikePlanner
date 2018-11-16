package com.maciejwozny.nextbikeplanner.graph;

import android.content.Context;
import android.util.Log;

import com.maciejwozny.nextbikeplanner.net.RoadDownloader;

import org.osmdroid.bonuspack.routing.Road;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

class StationEdge implements IStationEdge, Serializable {
    private static final String TAG = "StationEdge";
    private static final double TWENTY_MINUTES = 20 * 60;
    private StationVertex source;
    private StationVertex destination;
    private Road road = null;

    public StationEdge(StationVertex source, StationVertex destination, Context context) {
        this.source = source;
        this.destination = destination;
        double distance = source.getGeoPoint().distanceToAsDouble(destination.getGeoPoint());
        if (distance < 4000) {
            //TODO

//            road = new Road(new ArrayList<>(
//                                Arrays.asList(source.getGeoPoint(), destination.getGeoPoint())));

            try {
                RoadDownloader roadDownloader = new RoadDownloader(context);
                road = roadDownloader.execute(
                        new ArrayList<>(
                                Arrays.asList(source.getGeoPoint(), destination.getGeoPoint())))
                        .get();
                if (road.mDuration > TWENTY_MINUTES) {
                    road = null;
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                Log.e(TAG, "error: " + e.toString());
            }
        }
    }

    @Override
    public IStationVertex getSource() {
        return source;
    }

    @Override
    public IStationVertex getDestination() {
        return destination;
    }

    @Override
    public Road getRoad() {
        return road;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StationEdge that = (StationEdge) o;
        return Objects.equals(source, that.source) &&
                Objects.equals(destination, that.destination);
    }

    @Override
    public String toString() {
        return "StationEdge{" +
                "source=\'" + source.getName() +
                "\', destination=\'" + destination.getName() +
                "\', road=" + road +
                '}';
    }
}
