package com.maciejwozny.nextbikeplanner.graph;

class StationEdge implements IStationEdge {
    private static final String TAG = "StationEdge";
    private IStationVertex source;
    private IStationVertex destination;
    private float time;

    public StationEdge(IStationVertex source, IStationVertex destination) {
        this.source = source;
        this.destination = destination;
        time = source.getLocation().distanceTo(destination.getLocation());
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
    public float getTime() {
        return time;
    }
}
