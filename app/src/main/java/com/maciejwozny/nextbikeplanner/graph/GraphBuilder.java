package com.maciejwozny.nextbikeplanner.graph;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.maciejwozny.nextbikeplanner.R;
import com.maciejwozny.nextbikeplanner.station.IStation;

import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GraphBuilder {
    private static final String TAG = "GraphBuilder";

    public Graph<IStationVertex, IStationEdge> build(List<IStation> stationList, Context context) {
        Graph<IStationVertex, IStationEdge> graph = new SimpleWeightedGraph<>(IStationEdge.class);
        for (IStation station: stationList) {
            Set<IStationVertex> vertexSet = graph.vertexSet();
            StationVertex stationVertex = new StationVertex(station);
            graph.addVertex(stationVertex);
            for (IStationVertex vertex: vertexSet) {
                if (stationVertex.equals(vertex))
                    continue;
                IStationEdge edge = new StationEdge(stationVertex, (StationVertex)vertex, context);
                if (edge.getRoad() == null) continue;
                Log.v(TAG, stationVertex.getName() + " - " + vertex.getName()
                        + " = " + edge.getRoad().mDuration);
                graph.addEdge(stationVertex, vertex, edge);
                graph.setEdgeWeight(edge, edge.getRoad().mDuration);
            }
        }
        return graph;
    }

    public String saveGraph(Graph<IStationVertex, IStationEdge> graph) {
        Log.d(TAG, "saveGraph()");
        Gson gson = new Gson();
        List<IStationEdge> edgeArrayList = new ArrayList<>(graph.edgeSet());
        List<IStationVertex> vertexArrayList = new ArrayList<>(graph.vertexSet());
        Log.d(TAG, "vertexArrayList.size = " + vertexArrayList.size());
        Log.d(TAG, "edgeArrayList.size = " + edgeArrayList.size());

        Log.d(TAG, gson.toJson(edgeArrayList));
        File file = null;
        try {
            File docsFolder = new File(Environment.getExternalStorageDirectory() + "/MyFolder");
            if (!docsFolder.exists()) {
                docsFolder.mkdir();
            }
            file = new File(docsFolder.getAbsolutePath(),"vertexes.json");
            FileOutputStream vertexStream = new FileOutputStream(file);
            vertexStream.write(gson.toJson(vertexArrayList).getBytes());
            vertexStream.close();

            file = new File(docsFolder.getAbsolutePath(),"edges.json");
            FileOutputStream edgesStream = new FileOutputStream(file);
            edgesStream.write(gson.toJson(edgeArrayList).getBytes());
            edgesStream.close();
        } catch (IOException e) {
            Log.e(TAG, "error: " + e.toString());
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    public Graph<IStationVertex, IStationEdge> readGraph(Context context) {
        Log.d(TAG, "readGraph from resources");
        Graph<IStationVertex, IStationEdge> graph = new SimpleWeightedGraph<>(IStationEdge.class);

        InputStream stream = context.getResources().openRawResource(R.raw.vertexes_wroclaw);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        Gson gson = new Gson();
//        IStationVertex[] vertexes = gson.fromJson(reader, StationVertex[].class);
//        for (IStationVertex vertex: vertexes) {
//            Log.v(TAG, vertex.toString());
//            graph.addVertex(vertex);
//        }

        stream = context.getResources().openRawResource(R.raw.edges_wroclaw);
        reader = new BufferedReader(new InputStreamReader(stream));
        gson = new Gson();
        StationEdge[] edges = gson.fromJson(reader, StationEdge[].class);
        for (StationEdge edge: edges) {
            Log.v(TAG, edge.toString());
            graph.addVertex(edge.getSource());
            graph.addVertex(edge.getDestination());
            graph.addEdge(edge.getSource(), edge.getDestination(), edge);
            graph.setEdgeWeight(edge, edge.getRoad().mDuration);
        }

        return graph;
    }
}
