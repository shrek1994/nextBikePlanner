package com.maciejwozny.nextbikeplanner.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.maciejwozny.nextbikeplanner.SettingsActivity;

import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;

public class RoadDownloader {
    private final static String TAG = "RoadDownloader";
    private final static String SERVICE = "http://router.project-osrm.org/route/v1/cycling/";
    private OSRMRoadManager roadManager;
    private SharedPreferences preferences;

    public RoadDownloader(Context context) {
        roadManager = new OSRMRoadManager(context);
        preferences = context.getSharedPreferences(SettingsActivity.SETTINGS_PREFERENCES, Context.MODE_PRIVATE);
    }

    public Road downloadRoad(ArrayList<GeoPoint> geoPoints) {
        String service = SERVICE;
        if (preferences.getBoolean("pref_custom_osrm", false)) {
            service = "http://" + preferences.getString("pref_osrm_ip", "router.project-osrm.org")
            + "/route/v1/cycling/";

            Log.d(TAG, "custom url = " + service);
        }
        roadManager.setService(service);
        Road road = roadManager.getRoad(geoPoints);
        while (road.mStatus != Road.STATUS_OK) {
            Log.e(TAG, "Status - not OK !");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            road = roadManager.getRoad(geoPoints);
        }
        return road;
    }
}
