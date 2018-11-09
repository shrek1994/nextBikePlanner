package com.maciejwozny.nextbikeplanner.graph;

import android.util.Log;

import com.maciejwozny.nextbikeplanner.net.Station;

import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.List;
import java.util.Set;

public class GraphBuilder {
    private static final String TAG = "GraphBuilder";

    public Graph<IStationVertex, IStationEdge> build(List<Station> stationList) {
        Graph<IStationVertex, IStationEdge> graph = new SimpleWeightedGraph<>(IStationEdge.class);
        for (Station station: stationList) {
            Set<IStationVertex> vertexSet = graph.vertexSet();
            StationVertex stationVertex = new StationVertex(station);
            graph.addVertex(stationVertex);
            for (IStationVertex vertex: vertexSet) {
                if (stationVertex == vertex)
                    continue;
                IStationEdge edge = new StationEdge(stationVertex, vertex);
                if (edge.getTime() > 4000) continue; // TODO remove it !
                Log.d(TAG, stationVertex.getName() + " - " + vertex.getName()
                        + " = " + edge.getTime());
                graph.addEdge(stationVertex, vertex, edge);
                graph.setEdgeWeight(edge, edge.getTime());
            }
        }

        return graph;
    }
}
