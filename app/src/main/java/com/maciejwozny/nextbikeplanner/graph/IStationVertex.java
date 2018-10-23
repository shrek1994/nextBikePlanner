package com.maciejwozny.nextbikeplanner.graph;

import android.location.Location;

public interface IStationVertex {
    String getName();
    double getLongitude();
    double getLatitude();
    public Location getLocation();
}
