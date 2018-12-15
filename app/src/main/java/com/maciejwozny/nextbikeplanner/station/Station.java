package com.maciejwozny.nextbikeplanner.station;

import java.io.Serializable;

public class Station implements Serializable {
    private String name;
    private double longitude;
    private double latitude;

    public Station(String name, double longitude, double latitude) {
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
