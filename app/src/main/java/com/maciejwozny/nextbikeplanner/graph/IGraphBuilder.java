package com.maciejwozny.nextbikeplanner.graph;

import com.maciejwozny.nextbikeplanner.station.IStation;

import org.jgrapht.Graph;

import java.util.List;

public interface IGraphBuilder {
    Graph<IStationVertex, IStationEdge> buildGraph(List<IStation> stationList);
}
