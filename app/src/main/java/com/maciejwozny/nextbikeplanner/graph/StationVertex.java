package com.maciejwozny.nextbikeplanner.graph;

import com.maciejwozny.nextbikeplanner.station.IStation;

import org.osmdroid.util.GeoPoint;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StationVertex that = (StationVertex) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public String toString() {
        return "StationVertex{" +
                "name='" + name + '\'' +
                ", geoPoint=" + geoPoint +
                '}';
    }
}
