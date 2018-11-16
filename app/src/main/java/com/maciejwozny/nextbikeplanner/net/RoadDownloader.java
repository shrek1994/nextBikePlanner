package com.maciejwozny.nextbikeplanner.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.maciejwozny.nextbikeplanner.SettingsActivity;

import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;

public class RoadDownloader extends AsyncTask<ArrayList<GeoPoint>, Void, Road> {
    private final static String TAG = "RoadDownloader";
    private final static String OSRM_SERVICE = "http://router.project-osrm.org/route/v1/cycling/";
    private final static String SERVICE = "http://127.0.0.1:5000/route/v1/cycling/";
    private OSRMRoadManager roadManager;
    private SharedPreferences preferences;

    public RoadDownloader(Context context) {
        roadManager = new OSRMRoadManager(context);
        preferences = context.getSharedPreferences(SettingsActivity.SETTINGS_PREFERENCES, Context.MODE_PRIVATE);
    }

    private Road downloadRoad(ArrayList<GeoPoint> geoPoints) {
        String service = SERVICE;
        boolean customOsrm = preferences.getBoolean("pref_custom_osrm", false);
        Log.d(TAG, "pref_custom_osrm = " + customOsrm);
        if (customOsrm) {
            service = "http://" + preferences.getString("pref_osrm_ip", "router.project-osrm.org")
            + "/route/v1/cycling/";

            Log.d(TAG, "custom url = " + service);
        }
        roadManager.setService(service);
        Road road = roadManager.getRoad(geoPoints);
        for (int i = 0; i < 10 && road.mStatus != Road.STATUS_OK; i++) {
            Log.e(TAG, "Status - not OK !");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            road = roadManager.getRoad(geoPoints);
        }
        return road;
    }

    @Override
    protected Road doInBackground(ArrayList<GeoPoint>... geoPoints) {
        if (geoPoints.length > 0)
            return downloadRoad(geoPoints[0]);
        return null;
    }
}
