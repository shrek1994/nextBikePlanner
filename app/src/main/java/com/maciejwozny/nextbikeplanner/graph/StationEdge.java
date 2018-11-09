package com.maciejwozny.nextbikeplanner.graph;

class StationEdge implements IStationEdge {
    private static final String TAG = "StationEdge";
    private IStationVertex source;
    private IStationVertex destination;
    private double time;

    public StationEdge(IStationVertex source, IStationVertex destination) {
        this.source = source;
        this.destination = destination;

        time = source.getGeoPoint().distanceToAsDouble(destination.getGeoPoint());
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
    public double getTime() {
        return time;
    }
}
