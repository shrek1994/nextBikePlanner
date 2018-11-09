package com.maciejwozny.nextbikeplanner;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.maciejwozny.nextbikeplanner.graph.GraphBuilder;
import com.maciejwozny.nextbikeplanner.graph.IStationEdge;
import com.maciejwozny.nextbikeplanner.graph.IStationVertex;
import com.maciejwozny.nextbikeplanner.net.IStation;
import com.maciejwozny.nextbikeplanner.net.RoadDownloader;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.List;

public class CalculatePathListener implements View.OnClickListener {
    private static final String TAG = "CalculatePathListener";
    private Activity activity;
    private List<IStation> stationList;
    private MapManager mapManager;
    private String start = null;
    private String end = null;

    public CalculatePathListener(Activity activity, List<IStation> stationList, MapManager mapManager) {
        this.activity = activity;
        this.stationList = stationList;
        this.mapManager = mapManager;
    }

    @Override
    public void onClick(View view) {
        Graph<IStationVertex, IStationEdge> graph = new GraphBuilder().build(stationList);

        Log.d(TAG, graph.toString());
        Log.d(TAG, "number of vertex: " + graph.vertexSet().size());
        Log.d(TAG, "number of edges: " + graph.edgeSet().size());
        IStationVertex destination = null, source = null;

        if (start == null) {
            Toast.makeText(activity, "Select start !", Toast.LENGTH_LONG).show();
            return;
        }

        if (end == null) {
            Toast.makeText(activity, "Select end !", Toast.LENGTH_LONG).show();
            return;
        }

        for (IStationVertex vertex : graph.vertexSet()) {
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

        GraphPath<IStationVertex, IStationEdge> path
                = DijkstraShortestPath.findPathBetween(graph, source, destination);
        List<IStationEdge> stationEdges = path.getEdgeList();

        String pathString = "";
        for (IStationEdge edge: stationEdges) {
            pathString += edge.getDestination().getName() + " -> " + edge.getSource().getName()
                    + " = " + edge.getTime() + " meters\n";
        }

        List<IStationVertex> vertexList = path.getVertexList();
        Toast.makeText(activity, pathString, Toast.LENGTH_LONG).show();

        mapManager.clearMap();
        mapManager.addBikeStations(vertexList);

        new Thread(() -> {
            ArrayList<GeoPoint> geoPoints = new ArrayList<>();
            for (IStationVertex vertex: vertexList) {
                geoPoints.add(vertex.getGeoPoint());
            }

            Road road = new RoadDownloader(activity).downloadRoad(geoPoints);
            CalculatePathListener.this.activity.runOnUiThread(() -> {
                mapManager.showRoad(road);
            });

        }).start();
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
