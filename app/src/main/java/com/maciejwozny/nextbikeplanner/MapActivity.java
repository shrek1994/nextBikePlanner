package com.maciejwozny.nextbikeplanner;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.maciejwozny.nextbikeplanner.net.DataDownloader;
import com.maciejwozny.nextbikeplanner.net.Station;
import com.maciejwozny.nextbikeplanner.net.StationDownloader;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;

import java.util.ArrayList;


// kromera -> nadodrze
// in line length = 2.293 [km]
// GOOGLE:
// length 2,5km
// car time: 6 min, avg: 25km/h
// bike: 2,7 km, 12 min, avg: 13,5km/h
// OSRM (car):
// road length = 2.5028 [km]
// road duration = 367.0 [s] = 6.1 [min]
// avg speed: 24,5 km /h
// Open Route Service:
// car:
// "distance":2502.1,"duration":357.5 = 5,95 [min]
// avg speed: 25 km/h
// cycling-regular:
// "distance":2799.1,"duration":671.2 = 11,2 [min]
// avg speed: 15 km/h
// cycling-road:
// "distance":2710.5,"duration":430.7 = 7,16 [min]
// avg speed: 22,5 km/h
// cycling-safe:
// NOT WORKING !
// cycling-mountain:
// "distance":2755.2,"duration":656.4 = 10,94 [min]
// avg speed: 15 km/h
// cycling-tour:
// NOT WORKING !
// cycling-electric:
// "distance":2799.1,"duration":560.8 = 9,33 [min]
// avg speed: 18 km/h


public class MapActivity extends AppCompatActivity {
    private final static String TAG = "MapActivity";
    private final static GeoPoint WROCLAW_GEO_POINT = new GeoPoint(51.1078852, 17.0385376);
    private MapView map = null;
    private CalculatePathListener pathListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        ArrayList<Station> stationList = (ArrayList<Station>) intent.getSerializableExtra(MainActivity.EXTRA_STATION_LIST);

        if (stationList == null) {
            StationDownloader stationDownloader = new StationDownloader(new DataDownloader());
            stationList = (ArrayList<Station>) stationDownloader.downloadStations();
        }

        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setContentView(R.layout.activity_map);

        map = findViewById(R.id.map);
        pathListener = new CalculatePathListener(this, stationList, map);
        Button calculateButton = findViewById(R.id.calculateMapButton);
        calculateButton.setOnClickListener(pathListener);

        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);

        IMapController mapController = map.getController();
        mapController.setZoom(11.5);
        mapController.setCenter(WROCLAW_GEO_POINT);

        ArrayList<OverlayItem> items = new ArrayList<>();
        for (Station station : stationList) {
            items.add(new OverlayItem(station.getName(), "bikes: " + station.getBikeNumber(),
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
                            MapActivity.this.pathListener.setStart(item.getTitle());
                            TextView start = MapActivity.this.findViewById(R.id.startTextView);
                            start.setText(item.getTitle());
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            MapActivity.this.pathListener.setEnd(item.getTitle());
                            TextView end = MapActivity.this.findViewById(R.id.endTextView);
                            end.setText(item.getTitle());
                            break;
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);
                builder.setMessage(item.getTitle()).setPositiveButton("Start point", dialogClickListener)
                        .setNegativeButton("End Point", dialogClickListener).show();
                return false;
            }
        }, ctx);
        mOverlay.setFocusItemsOnTap(true);

        map.getOverlays().add(mOverlay);
    }



    @Override
    public void onResume(){
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    @Override
    public void onPause(){
        super.onPause();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        Configuration.getInstance().save(this, prefs);
        map.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
