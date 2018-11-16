package com.maciejwozny.nextbikeplanner.graph;

import org.osmdroid.bonuspack.routing.Road;

import java.io.Serializable;

public interface IStationEdge extends Serializable {
    IStationVertex getSource();
    IStationVertex getDestination();
    Road getRoad();
}
