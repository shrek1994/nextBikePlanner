package com.maciejwozny.nextbikeplanner.graph;

import org.osmdroid.util.GeoPoint;

public interface IStationVertex {
    String getName();
    GeoPoint getGeoPoint();
}
