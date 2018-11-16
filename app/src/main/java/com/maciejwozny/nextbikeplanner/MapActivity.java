package com.maciejwozny.nextbikeplanner;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;

import com.maciejwozny.nextbikeplanner.station.IStationFactory;
import com.maciejwozny.nextbikeplanner.station.StationFactory;
import com.maciejwozny.nextbikeplanner.station.IStation;

import org.osmdroid.views.MapView;

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
    private MapView map = null;
    private CalculatePathListener pathListener = null;
    private MapManager mapManager = null;
    private ChooseStationDialog stationDialog = null;
    private IStationFactory stationFactory = null;
    private ProgressBar progressBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stationFactory = new StationFactory(this);

        ArrayList<IStation> stationList = stationFactory.createStationList();

//        Context ctx = getApplicationContext();
//        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setContentView(R.layout.activity_map);

        map = findViewById(R.id.map);
        progressBar = findViewById(R.id.progressBar);
        mapManager = new MapManager(this, map, stationList);
        pathListener = new CalculatePathListener(this, stationList, mapManager, progressBar);
        stationDialog = new ChooseStationDialog(pathListener, findViewById(R.id.startTextView),
                findViewById(R.id.endTextView));

        Button calculateButton = findViewById(R.id.calculateMapButton);
        calculateButton.setOnClickListener(pathListener);

        mapManager.setStationDialog(stationDialog);
        mapManager.initBikeStations();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // Do the file write
        } else {
            // Request permission from the user
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
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
            case R.id.action_clear_map:
                mapManager.clearMap();
                mapManager.initBikeStations();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
