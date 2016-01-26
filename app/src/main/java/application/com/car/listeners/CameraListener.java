package application.com.car.listeners;

import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Marker;

import application.com.car.ShowLocation;

/**
 * Created by Zahit Talipov on 15.01.2016.
 */
public class CameraListener implements GoogleMap.OnCameraChangeListener {
    public static boolean mapIsDown=false;

    public CameraListener(Marker marker, GoogleMap map) {
        CameraListener.marker = marker;
        CameraListener.map = map;
    }

    private static Marker marker;
    private static GoogleMap map;


    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        if(!mapIsDown)
        {
            marker.setVisible(true);
            ShowLocation.getInstance().show(cameraPosition.target);
            mapIsDown=true;
        }
    }

    public static void markerClean(){
        if(marker!=null) {
            marker.setVisible(false);
            marker.setTitle("");
            marker.showInfoWindow();
        }
    }


}
