package com.ihack.android.mapboxexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;

import com.mapbox.mapboxsdk.MapboxAccountManager;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapFragment;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

public class MainActivity extends AppCompatActivity {

    //global declarations
    private MapView mapView;    //assigned MapView to mapView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //uses the MapBox account manager. Basically reference the access token from your account
        MapboxAccountManager.start(this, getString(R.string.access_token));

        setContentView(R.layout.activity_main);


        //create a MapView class called mapView
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        mapView.setStyleUrl("mapbox://styles/eidolynn/cj007trzz008n2so2gzxs8bkn");

        //calls mapView.getMapAsync to create a MapBoxMap object
        // the MapBoxMap object allows you to change styles and interaction with map
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                //
            }
        });
    }//end onCreate

}//endMainActivity
