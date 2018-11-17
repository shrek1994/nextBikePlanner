package com.maciejwozny.nextbikeplanner.graph;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.maciejwozny.nextbikeplanner.station.IStation;

import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GraphBuilder implements IGraphBuilder {
    private static final String TAG = "GraphBuilder";
    private EdgeFactory factory;

    public GraphBuilder(Context context, EdgeReader edgeReader) {
        factory = new EdgeFactory(context, edgeReader);
    }

    @Override
    public Graph<IStationVertex, IStationEdge> buildGraph(List<IStation> stationList) {
        Log.d(TAG, "Started building graph...");
        Graph<IStationVertex, IStationEdge> graph = new SimpleWeightedGraph<>(IStationEdge.class);
        for (IStation station: stationList) {
            Set<IStationVertex> vertexSet = graph.vertexSet();
            StationVertex stationVertex = new StationVertex(station);
            graph.addVertex(stationVertex);
            for (IStationVertex vertex: vertexSet) {
                if (stationVertex.equals(vertex)) {
                    continue;
                }
                IStationEdge edge = factory.create(stationVertex, (StationVertex)vertex);
                if (edge == null) {
                    continue;
                }
//                Log.v(TAG, stationVertex.getName() + " - " + vertex.getName()
//                        + " = " + edge.getRoad().mDuration);
                graph.addEdge(stationVertex, vertex, edge);
                //adding 15 second for give back bike
                double duration = edge.getRoad().mDuration + 15;
                graph.setEdgeWeight(edge, duration);
            }
        }
        return graph;
    }

    public String saveGraphEdges(Graph<IStationVertex, IStationEdge> graph) {
        Log.d(TAG, "saveGraphEdges()");
        Gson gson = new Gson();
        List<IStationEdge> edgeArrayList = new ArrayList<>(graph.edgeSet());
//        List<IStationVertex> vertexArrayList = new ArrayList<>(graph.vertexSet());
//        Log.d(TAG, "vertexArrayList.size = " + vertexArrayList.size());
        Log.d(TAG, "edgeArrayList.size = " + edgeArrayList.size());

        Log.d(TAG, gson.toJson(edgeArrayList).substring(0, 100) + "...");
        File file = null;
        try {
            File docsFolder = new File(Environment.getExternalStorageDirectory() + "/nextBikePlaner");
            if (!docsFolder.exists()) {
                docsFolder.mkdir();
            }
//            file = new File(docsFolder.getAbsolutePath(),"vertexes.json");
//            FileOutputStream vertexStream = new FileOutputStream(file);
//            vertexStream.write(gson.toJson(vertexArrayList).getBytes());
//            vertexStream.close();

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
