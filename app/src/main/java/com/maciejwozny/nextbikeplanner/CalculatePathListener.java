package com.maciejwozny.nextbikeplanner;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.maciejwozny.nextbikeplanner.graph.GraphBuilder;
import com.maciejwozny.nextbikeplanner.graph.IStationEdge;
import com.maciejwozny.nextbikeplanner.graph.IStationVertex;
import com.maciejwozny.nextbikeplanner.net.Station;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;
import java.util.List;

public class CalculatePathListener implements View.OnClickListener {
    private static final String TAG = "CalculatePathListener";
    private Activity activity;
    private List<Station> stationList;
    private MapView map;
    private String start = null;
    private String end = null;

    public CalculatePathListener(Activity activity, List<Station> stationList, MapView map) {
        this.activity = activity;
        this.stationList = stationList;
        this.map = map;
    }

    @Override
    public void onClick(View view) {
        Graph<IStationVertex, IStationEdge> graph = new GraphBuilder().build(stationList);

        Log.d(TAG, graph.toString());
        Log.d(TAG, "number of vertex: " + graph.vertexSet().size());
        Log.d(TAG, "number of edges: " + graph.edgeSet().size());
        IStationVertex destination = null, source = null;
        for (IStationVertex vertex : graph.vertexSet()) {
            if (start.equals(vertex.getName())) {
                source = vertex;
            }
            if (end.equals(vertex.getName())) {
                destination = vertex;
            }
        }

        if (source == null) {
            Toast.makeText(activity, "select correct start !", Toast.LENGTH_LONG).show();
            return;
        }

        if (destination == null) {
            Toast.makeText(activity, "select correct end !", Toast.LENGTH_LONG).show();
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
        ArrayList<OverlayItem> overlayItemList = new ArrayList<>();
        Toast.makeText(activity, pathString, Toast.LENGTH_LONG).show();
        map.getOverlays().clear();
        for (IStationVertex vertex: vertexList) {
            overlayItemList.add(new OverlayItem(vertex.getName(), "",vertex.getGeoPoint()));
        }
        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(overlayItemList,
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                        return true;
                    }

                    @Override
                    public boolean onItemLongPress(final int index, final OverlayItem item) {
                        return false;
                    }
                }, CalculatePathListener.this.activity);
        mOverlay.setFocusItemsOnTap(true);

        map.getOverlays().add(mOverlay);

        new Thread(() -> {
            RoadManager roadManager = new OSRMRoadManager(activity);
            ArrayList<GeoPoint> waypoints = new ArrayList<>();
            for (IStationVertex vertex: vertexList) {
                waypoints.add(vertex.getGeoPoint());
            }

            Road road = roadManager.getRoad(waypoints);
            while (road.mStatus != Road.STATUS_OK) {
                Log.e(TAG, "Status - not OK !");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                road = roadManager.getRoad(waypoints);
            }

            Road finalRoad = road;
            CalculatePathListener.this.activity.runOnUiThread(() -> {
                Polyline roadOverlay = RoadManager.buildRoadOverlay(
                        finalRoad, Color.RED, 8);
                map.getOverlays().add(roadOverlay);
                map.invalidate();
                Log.d(TAG, "road length = " + finalRoad.mLength);
                Log.d(TAG, "road duration = " + finalRoad.mDuration);
            });

        }).start();

        new Thread(() -> {

        }).start();
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
