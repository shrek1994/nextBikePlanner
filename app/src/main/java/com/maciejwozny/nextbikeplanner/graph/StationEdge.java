package com.maciejwozny.nextbikeplanner.graph;

import org.osmdroid.bonuspack.routing.Road;

import java.io.Serializable;
import java.util.Objects;

class StationEdge implements IStationEdge, Serializable {
    private static final String TAG = "StationEdge";
    private StationVertex source;
    private StationVertex destination;
    private Road road;

    public StationEdge(StationVertex source, StationVertex destination, Road road) {
        this.source = source;
        this.destination = destination;
        this.road = road;
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
