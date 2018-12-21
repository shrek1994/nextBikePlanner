package com.maciejwozny.nextbikeplanner.graph;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.maciejwozny.nextbikeplanner.station.Station;

import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GraphBuilder {
    private static final String TAG = "GraphBuilder";
    private EdgeFactory factory;

    public GraphBuilder(Context context, RoadReader roadReader) {
        factory = new EdgeFactory(context, roadReader);
    }

    public Graph<StationVertex, StationEdge> buildGraph(List<Station> stationList) {
        Log.d(TAG, "Started building graph...");
        Graph<StationVertex, StationEdge> graph = new SimpleWeightedGraph<>(StationEdge.class);
        for (Station station: stationList) {
            Set<StationVertex> vertexSet = graph.vertexSet();
            StationVertex stationVertex = new StationVertex(station);
            graph.addVertex(stationVertex);
            for (StationVertex vertex: vertexSet) {
                if (stationVertex.equals(vertex)) {
                    continue;
                }
                StationEdge edge = factory.create(stationVertex, vertex);
                if (edge == null) {
                    continue;
                }
                graph.addEdge(stationVertex, vertex, edge);
                //adding 15 second for give back bike
                double duration = edge.getRoad().mDuration + 15;
                graph.setEdgeWeight(edge, duration);
            }
        }
        return graph;
    }

    public String saveGraphEdges(Graph<StationVertex, StationEdge> graph) {
        Log.d(TAG, "saveGraphEdges()");
        Gson gson = new Gson();
        List<StationEdge> edgeArrayList = new ArrayList<>(graph.edgeSet());
        Log.d(TAG, "edgeArrayList.size = " + edgeArrayList.size());

        Log.d(TAG, gson.toJson(edgeArrayList).substring(0, 100) + "...");
        File file = null;
        try {
            File docsFolder = new File(Environment.getExternalStorageDirectory() + "/nextBikePlaner");
            if (!docsFolder.exists()) {
                docsFolder.mkdir();
            }

            file = new File(docsFolder.getAbsolutePath(),"edges.json");
            FileOutputStream edgesStream = new FileOutputStream(file);
            edgesStream.write(gson.toJson(edgeArrayList).getBytes());
            edgesStream.close();
        } catch (IOException e) {
            Log.e(TAG, "error: " + e.toString());
            e.printStackTrace();
        }
        Log.d(TAG, "saved to: " + file.getAbsolutePath());
        return file.getAbsolutePath();
    }
}
