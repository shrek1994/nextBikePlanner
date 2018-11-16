package com.maciejwozny.nextbikeplanner;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.maciejwozny.nextbikeplanner.graph.IStationVertex;
import com.maciejwozny.nextbikeplanner.station.IStation;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;
import java.util.List;

public class MapManager {
    private final static String TAG = "MapManager";
    private final static GeoPoint WROCLAW_GEO_POINT = new GeoPoint(51.1078852, 17.0385376);
    private Context context;
    private MapView mapView;
    private ChooseStationDialog stationDialog;
    private ArrayList<IStation> stationList;

    public MapManager(Context context, MapView mapView,
                      ArrayList<IStation> stationList) {
        this.context = context;
        this.mapView = mapView;
        this.stationList = stationList;

        this.mapView.setTileSource(TileSourceFactory.MAPNIK);
        this.mapView.setBuiltInZoomControls(true);
        this.mapView.setMultiTouchControls(true);

        IMapController mapController = this.mapView.getController();
        mapController.setZoom(11.5);
        mapController.setCenter(WROCLAW_GEO_POINT);
    }

    public void setStationDialog(ChooseStationDialog stationDialog) {
        this.stationDialog = stationDialog;
    }

    public void clearMap() {
        mapView.getOverlays().clear();
    }

    public void initBikeStations() {
        ArrayList<OverlayItem> items = new ArrayList<>();
        for (IStation station : stationList) {
            items.add(new OverlayItem(station.getName(), "", //"bikes: " + station.getBikeNumber(),
                    new GeoPoint(station.getLatitude(), station.getLongitude())));
        }

        ItemizedOverlayWithFocus<OverlayItem> mOverlay = new ItemizedOverlayWithFocus<OverlayItem>(items,
            new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                @Override
                public boolean onItemSingleTapUp(final int index, final OverlayItem item) {
                    return true;
                }

                @Override
                public boolean onItemLongPress(final int index, final OverlayItem item) {
                    stationDialog.show(context, item.getTitle());
                    return false;
                }
            }, context);
        mOverlay.setFocusItemsOnTap(true);

        mapView.getOverlays().add(mOverlay);
    }

    public void addBikeStations(List<IStationVertex> vertexList) {
        ArrayList<OverlayItem> overlayItemList = new ArrayList<>();
        for (IStationVertex vertex: vertexList) {
            overlayItemList.add(new OverlayItem(vertex.getName(), "" ,vertex.getGeoPoint()));
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
                }, context);

        mOverlay.setFocusItemsOnTap(true);

        mapView.getOverlays().add(mOverlay);
        mapView.invalidate();
    }

    public void showRoad(Road road) {
        Polyline roadOverlay = RoadManager.buildRoadOverlay(
                road, Color.RED, 8);
        mapView.getOverlays().add(roadOverlay);
        mapView.invalidate();

        Log.d(TAG, "road length = " + road.mLength);
        Log.d(TAG, "road duration = " + road.mDuration / 60 + "[min]");
    }
}
