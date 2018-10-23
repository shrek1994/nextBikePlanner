package com.maciejwozny.nextbikeplanner.graph;

public interface IStationEdge {
    IStationVertex getSource();
    IStationVertex getDestination();
    float getTime();
}
