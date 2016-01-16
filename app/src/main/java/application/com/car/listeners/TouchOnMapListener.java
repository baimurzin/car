package application.com.car.listeners;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.io.IOException;
import java.util.List;

import application.com.car.ShowLocation;

/**
 * Created by Zahit Talipov on 15.01.2016.
 */
public class TouchOnMapListener implements GoogleMap.OnMapClickListener {

    private Marker marker;
    private GoogleMap map;
    private Context context;

    public TouchOnMapListener(Context context, Marker marker, GoogleMap map) {
        this.marker = marker;
        this.map = map;
        this.context = context;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        ShowLocation.getInstance().show(latLng);
    }


}
