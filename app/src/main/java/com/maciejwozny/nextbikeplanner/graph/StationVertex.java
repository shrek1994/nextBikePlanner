package com.maciejwozny.nextbikeplanner.graph;

import android.location.Location;

import com.maciejwozny.nextbikeplanner.net.IStation;

class StationVertex implements IStationVertex {
    private String name;
    private double longitude;
    private double latitude;
    private Location location;

    public StationVertex(IStation station) {
        this.name = station.getName();
        this.longitude = station.getLongitude();
        this.latitude = station.getLatitude();
        this.location = new Location("");
        location.setLatitude(latitude);
        location.setLongitude(longitude);
    }

    public StationVertex(String name, double longitude, double latitude) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }

    @Override
    public double getLatitude() {
        return latitude;
    }

    @Override
    public Location getLocation() {
        return location;
    }
}
