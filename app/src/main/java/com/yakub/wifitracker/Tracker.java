package com.yakub.wifitracker;


import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Tracker implements LocationListener {

    final private static int MIN_DISTANCE = 5;
    final private WifiManager wifiManager;
    final private LocationManager locationManager;
    final private Extractor<GoogleMap> mapExtractor;
    final private Context context;
    final private BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            List<ScanResult> scanResults = wifiManager.getScanResults();
            addMarker(scanResults);
            writeLog(scanResults);

        }
    };
    private LatLng location = null;


    @SuppressLint("MissingPermission")
    public Tracker(Context context, Extractor<GoogleMap> mapExtractor) {
        this.context = context.getApplicationContext();
        this.wifiManager = (WifiManager) this.context.getSystemService(Context.WIFI_SERVICE);
        this.locationManager = (LocationManager) this.context.getSystemService(Context.LOCATION_SERVICE);
        this.mapExtractor = mapExtractor;

        String bestProvider = locationManager.getBestProvider(new Criteria(), true);

        //todo dodać obsłuę uprawnień
        locationManager.requestLocationUpdates(bestProvider, 5000, MIN_DISTANCE, this);
    }

    public boolean start(){
        Toast.makeText(context, "Start tracker", Toast.LENGTH_SHORT).show();
        context.registerReceiver(this.wifiReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        return wifiManager.startScan();
    }

    private void addMarker(List<ScanResult> results){
        GoogleMap map = mapExtractor.value();
        if (map != null && location != null){
            StringBuilder snipped = new StringBuilder();
            for (ScanResult result: results){
                snipped.append(result.SSID).append("(").append(result.level).append(") ");
                snipped.append(result.capabilities).append("\n");
            }
            MarkerOptions marker = new MarkerOptions();
            marker.position(location);
            marker.snippet(snipped.toString());
            marker.title("" + results.size());

            map.addMarker(marker);
            Toast.makeText(context, "Add", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onLocationChanged(Location newLocation) {
        location = new LatLng(newLocation.getLatitude(), newLocation.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(context, "Provider enabled: " + provider, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void writeLog(List<ScanResult> results){
        if(location == null){
            return;
        }

        double lat = location.latitude;
        double lng = location.longitude;

        String title = String.format("> marker %s %s %s", results.size(), lat, lng);
        StringBuilder sb = new StringBuilder(title).append("\n");
        for (ScanResult result: results) {
            sb.append("# ").append(result).append("\n");
        }

        try {
            File file = new File ("/storage/sdcard0/wifi.log");
            FileWriter writer = new FileWriter(file, true);
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    };
}
