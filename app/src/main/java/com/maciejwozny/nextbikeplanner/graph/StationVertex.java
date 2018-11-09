package com.maciejwozny.nextbikeplanner.graph;

import com.maciejwozny.nextbikeplanner.net.IStation;
import com.maciejwozny.nextbikeplanner.net.Station;

import org.osmdroid.util.GeoPoint;

class StationVertex implements IStationVertex {
    private String name;
    private GeoPoint geoPoint;

    public StationVertex(IStation station) {
        this.name = station.getName();
        this.geoPoint = new GeoPoint(station.getLatitude(), station.getLongitude());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public GeoPoint getGeoPoint() {
        return geoPoint;
    }
}
