package com.maciejwozny.nextbikeplanner;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.maciejwozny.nextbikeplanner.graph.RoadReader;
import com.maciejwozny.nextbikeplanner.graph.GraphBuilder;
import com.maciejwozny.nextbikeplanner.graph.StationEdge;
import com.maciejwozny.nextbikeplanner.graph.StationVertex;
import com.maciejwozny.nextbikeplanner.graph.RoadDownloader;
import com.maciejwozny.nextbikeplanner.station.Station;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CalculatePathListener implements View.OnClickListener {
    private static final String TAG = "CalculatePathListener";
    private Activity activity;
    private List<Station> stationList;
    private MapManager mapManager;
    private RoadDownloader roadDownloader;
    private RoadReader roadReader;
    private String start = null;
    private String end = null;
    private ProgressBar progressBar;
    private Graph<StationVertex, StationEdge> graph;
    private Thread createGraph = new Thread(() -> {
                graph = new GraphBuilder(activity, roadReader).buildGraph(stationList);
                activity.runOnUiThread(() -> progressBar.setVisibility(View.GONE));
            });

    public CalculatePathListener(Activity activity, List<Station> stationList,
                                 MapManager mapManager, ProgressBar progressBar) {
        this.activity = activity;
        this.stationList = stationList;
        this.mapManager = mapManager;
        this.roadDownloader = new RoadDownloader(activity);
        this.roadReader = new RoadReader(activity);
        this.progressBar = progressBar;
        createGraph.start();
    }

    @Override
    public void onClick(View view) {
        if (graph == null) {
            Log.d(TAG, "graph is null - still building...");
            try {
                createGraph.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Log.d(TAG, "number of vertex: " + graph.vertexSet().size());
        Log.d(TAG, "number of edges: " + graph.edgeSet().size());

        StationVertex destination = null, source = null;

        if (start == null) {
            Toast.makeText(activity, "Select start !", Toast.LENGTH_LONG).show();
            return;
        }

        if (end == null) {
            Toast.makeText(activity, "Select end !", Toast.LENGTH_LONG).show();
            return;
        }

        for (StationVertex vertex : graph.vertexSet()) {
            if (start.equals(vertex.getName())) {
                source = vertex;
            }
            if (end.equals(vertex.getName())) {
                destination = vertex;
            }
        }

        if (source == null) {
            Toast.makeText(activity, "Select correct start !", Toast.LENGTH_LONG).show();
            Log.e(TAG, "Start not found: \'" + start + "\'");
            return;
        }

        if (destination == null) {
            Toast.makeText(activity, "Select correct end !", Toast.LENGTH_LONG).show();
            Log.e(TAG, "End not found: \'" + end + "\'");
            return;
        }

        GraphPath<StationVertex, StationEdge> path
                = DijkstraShortestPath.findPathBetween(graph, source, destination);
        List<StationEdge> StationEdges = path.getEdgeList();

        String pathString = getPathText(StationEdges, source);

        List<StationVertex> vertexList = path.getVertexList();
        Toast.makeText(activity, pathString, Toast.LENGTH_LONG).show();

        mapManager.clearMap();
        mapManager.addBikeStations(vertexList);

        ArrayList<GeoPoint> geoPoints = new ArrayList<>();
        for (StationVertex vertex: vertexList) {
            geoPoints.add(vertex.getGeoPoint());
        }

        for (StationEdge edge: StationEdges) {
            Road road = roadReader.getRoad(edge.getSource(), edge.getDestination());
            try {
                if (road == null) {
                    RoadDownloader roadDownloader = new RoadDownloader(activity);
                    road = roadDownloader.execute(geoPoints).get();
                }
                mapManager.showRoad(road);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    @NonNull
    private String getPathText(List<StationEdge> StationEdges, StationVertex startVertex) {
        StationVertex previousVertex = startVertex;
        String pathString = "";
        for (StationEdge edge: StationEdges) {
            int minutes = (int) edge.getRoad().mDuration / 60;
            int seconds = (int) edge.getRoad().mDuration % 60;
            pathString += previousVertex.getName() + " -> ";
            if (previousVertex.equals(edge.getDestination())) {
                pathString += edge.getSource().getName();
                previousVertex = edge.getSource();
            } else {
                pathString += edge.getDestination().getName();
                previousVertex = edge.getDestination();
            }
            pathString += " = " + minutes + "min " + seconds + "s\n";
        }
        return pathString;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
