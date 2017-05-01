package com.yakub.wifitracker;

import android.content.Context;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Obsługa map
 */
public class MapsUtils {
    public final static LatLng LSM_POINT = new LatLng(51.243608, 22.535093);
    public final static float SMALL_ZOOM = 14f;

    public static void setOnMapParams(GoogleMap map) {
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setCompassEnabled(true);
        map.getUiSettings().setMapToolbarEnabled(false);
    }

    public static void setMapCameraParams(GoogleMap map, LatLng center, float zoom) {
        map.moveCamera(CameraUpdateFactory.newLatLng(center));
        map.animateCamera(CameraUpdateFactory.zoomTo(zoom));
    }

    public static void addMarker(GoogleMap map, Context context, double latitude, double longitude, String title, String snippet, int bikesCount)
    {
        float alfa = bikesCount / 6.0f + 0.2f;
        MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude));
        marker.title(title).snippet(snippet).alpha(alfa);
        marker.flat(bikesCount <= 0);
        //todo icon
//        marker.icon(BitmapDescriptorFactory.fromResource(resId));
        map.addMarker(marker);
    }
}
