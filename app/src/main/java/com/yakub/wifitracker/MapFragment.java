package com.yakub.wifitracker;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;

/**
 * Fragment map
 */
public class MapFragment extends SupportMapFragment implements OnMapReadyCallback, Extractor<GoogleMap> {

    private GoogleMap mMap;
    private Tracker tracker;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getMapAsync(this);

        tracker = new Tracker(getContext(), this);
        Toast.makeText(this.getContext(), "Tracker init" , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        MapsUtils.setMapCameraParams(map, MapsUtils.LSM_POINT, MapsUtils.SMALL_ZOOM);
        MapsUtils.setOnMapParams(map);
        setMarkerAdapter(mMap);
        tracker.start();
    }

    @Override
    public GoogleMap value() {
        return mMap;
    }

    /**
     * Dodanie adaptera do markerów na mapie
     */
    private void setMarkerAdapter(GoogleMap map){
        map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                LinearLayout info = new LinearLayout(getContext());
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(getContext());
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(getContext());
                snippet.setTextColor(Color.GRAY);
                snippet.setSingleLine(false);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });
    }
}
