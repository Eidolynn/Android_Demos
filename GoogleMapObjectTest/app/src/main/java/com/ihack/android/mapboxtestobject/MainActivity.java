package com.ihack.android.mapboxtestobject;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    //global declarations
    GoogleMap m_map;        //initialize GoogleMap object
    boolean mapReady = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set up the buttons to manipulate the GMap
        //BEGIN BEGIN BEGIN BEGIN BEGIN BEGIN BEGIN BEGIN BEGIN BEGIN BEGIN BEGIN
        //BEGIN BEGIN BEGIN BEGIN BEGIN BEGIN BEGIN BEGIN BEGIN BEGIN BEGIN BEGIN

//        //Create object instance of the MapClickListener class
//        //Must first create the MapClickListener.java class
//        MapClickListener clickListener = new MapClickListener();
//
//        //point to the View that is the button...initialize them to the following variable names
//        TextView satelliteButton = (TextView) findViewById(R.id.satellite_button);
//        TextView hybridButton =  (TextView) findViewById(R.id.hybrid_button);
//        TextView earthButton = (TextView) findViewById(R.id.earth_button);
//
//        //set the onclick listener on the view variables
//        if(mapReady) satelliteButton.setOnClickListener(clickListener);
//        if(mapReady) hybridButton.setOnClickListener(clickListener);
//        if(mapReady) earthButton.setOnClickListener(clickListener);

        //ALTERNATIVELY: you can combine the above three lines into one in-line code
        // EXAMPLE: findViewById(R.id.satellite_button).setOnClickListener(new MapClickListener());
        //
        //To Elaborate, 1) Find the view ID (findViewByID(R.id.idNme)
        //              2) set the onClick listener ( .setOnClickListener( arg) )
        //              3) the arg you pass to onClickListener is a new object instance of the custom ClickListner Class
        //                  (new MapClickListner()
        //
        //Simply put... Look at the view...start listening to it...call your custom java class when you hear what you were expecting
        //END END END END END END END END END END END END END END END END END END END END
        //END END END END END END END END END END END END END END END END END END END END

        //Combining the Previous ALTERNATIVELY:
        //ALTERNATIVELY:
        //You instead of using a new and separate class 'MapClickListener' you can define the onClick method within the setOnClickListener( arg )
        //Just sit back and observe...
        Button satelliteButton = (Button) findViewById(R.id.satellite_button);
        Button hybridButton = (Button) findViewById(R.id.hybrid_button);
        Button terrainButton = (Button) findViewById(R.id.terrain_button);
        Button noneButton = (Button) findViewById(R.id.none_button);
        Button normalButton = (Button) findViewById(R.id.normal_button);
        satelliteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (mapReady)
                    m_map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            }
        });
        hybridButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (mapReady)
                    m_map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            }
        });
        terrainButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (mapReady)
                    m_map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            }
        });
        normalButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (mapReady)
                    m_map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
        });
        noneButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (mapReady)
                    m_map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            }
        });

        //Not really sure what this does yet but it is essential
        //TODO: Explain this code
        //Get the mapfragment and register for the callback when the map is ready for use
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }//end onCreate

    //Creates a GoogleMap Object
    //The following code will change the camera of Google Map
    @Override
    public void onMapReady(GoogleMap map){
        mapReady=true;
        m_map = map;            //local instance of google map...see global declaration
        LatLng uTDallas = new LatLng(32.986284,-96.748361);
        CameraPosition target = CameraPosition.builder().target(uTDallas).zoom(17).build();
        m_map.moveCamera(CameraUpdateFactory.newCameraPosition(target));
    }//end onMapReady

}//end MainActivity
