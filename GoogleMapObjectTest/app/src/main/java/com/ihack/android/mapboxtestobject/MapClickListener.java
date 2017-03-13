package com.ihack.android.mapboxtestobject;

import android.view.View;

import com.google.android.gms.maps.GoogleMap;

import static com.ihack.android.mapboxtestobject.R.id.map;

/**
 * Created by iHack on 3/9/2017.
 * This the most imporper way to manipulate the GoogleMap object.
 * This MapClickListener class doesnt implement the OnMapCallBack
 * It also doesnt test to se if the mapReady is True. Could potentially fuck up everything
 * The pre-test might also be required before manipulating the GoogleMapObject.
 *YOu'd have to create a MapClickListener for each MapType becuase there isnt a way to pass which type you'd like to this class.
 */

public class MapClickListener implements View.OnClickListener{

    GoogleMap m_map;

    @Override
    public void onClick(View view){
        m_map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }
}
