package com.maciejwozny.nextbikeplanner;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.TextView;

import com.maciejwozny.nextbikeplanner.net.Station;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;

public class MapManager {
    private final static GeoPoint WROCLAW_GEO_POINT = new GeoPoint(51.1078852, 17.0385376);
    private Context context;
    private MapView mapView;
    private CalculatePathListener pathListener;
    private ArrayList<Station> stationList;

    public MapManager(Context context,
                      MapView mapView,
                      CalculatePathListener pathListener,
                      ArrayList<Station> stationList) {
        this.context = context;
        this.mapView = mapView;
        this.pathListener = pathListener;
        this.stationList = stationList;

        this.mapView.setTileSource(TileSourceFactory.MAPNIK);
        this.mapView.setBuiltInZoomControls(true);
        this.mapView.setMultiTouchControls(true);

        IMapController mapController = this.mapView.getController();
        mapController.setZoom(11.5);
        mapController.setCenter(WROCLAW_GEO_POINT);

    }

    public void clearMap() {
        mapView.getOverlays().clear();
    }

    public void initBikeStations(TextView start, TextView end) {
        ArrayList<OverlayItem> items = new ArrayList<>();
        for (Station station : stationList) {
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
                    DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                MapManager.this.pathListener.setEnd(item.getTitle());
                                end.setText(item.getTitle());
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                MapManager.this.pathListener.setStart(item.getTitle());
                                start.setText(item.getTitle());
                                break;
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage(item.getTitle()).setPositiveButton("End Point", dialogClickListener)
                            .setNegativeButton("Start point", dialogClickListener).show();
                    return false;
                }
            }, context);
        mOverlay.setFocusItemsOnTap(true);

        mapView.getOverlays().add(mOverlay);
    }
}
