package com.maciejwozny.nextbikeplanner.graph;

import android.content.Context;
import android.util.Log;

import com.maciejwozny.nextbikeplanner.net.RoadDownloader;

import org.osmdroid.bonuspack.routing.Road;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class EdgeFactory {
    private static final String TAG = "EdgeFactory";
    private static final double TWENTY_MINUTES = 20 * 60;
    private static final double AVG_SPEED_KM_H = 12;
    private static final double AVG_SPEED_M_S = AVG_SPEED_KM_H * 1000 / (60 * 60);
    private EdgeReader edgeReader;
    private Context context;
    private List<IStationEdge> stationEdges;

    public EdgeFactory(EdgeReader edgeReader, Context context) {
        this.edgeReader = edgeReader;
        this.context = context;
        stationEdges = edgeReader.readGraphEdges();
    }

    /*
     * Creating edge if road duration is lower than 20 minutes
     */
    public IStationEdge create(StationVertex source, StationVertex destination) {
        double distance = source.getGeoPoint().distanceToAsDouble(destination.getGeoPoint());
        if (distance > 4000) {
            return null;
        }
        Road road = null;
        for (IStationEdge edge: stationEdges) {
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

        try {
            if (road == null) {
                Log.d(TAG, "trying to download road for " + source.getName()
                        + " and " + destination.getName());
                RoadDownloader roadDownloader = new RoadDownloader(context);
                road = roadDownloader.execute(
                        new ArrayList<>(
                                Arrays.asList(source.getGeoPoint(), destination.getGeoPoint())))
                        .get();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            Log.e(TAG, "error: " + e.toString());
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
