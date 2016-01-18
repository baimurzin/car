package application.com.car.listeners;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import application.com.car.R;
import application.com.car.entity.MyLocation;

/**
 * Created by Zahit Talipov on 15.01.2016.
 */
public class MLocationListener implements LocationListener {
    public static GoogleMap map;
    public static boolean locationChanged = false;
    static Marker marker;
    Context context;

    public MLocationListener(Context context, GoogleMap map) {
        MLocationListener.map = map;
        this.context = context;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("wef", location.getProvider());
        MyLocation.latitude = location.getLatitude();
        MyLocation.longitude = location.getLongitude();
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        if (!locationChanged) {
            marker = map.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.my_location)).position(latLng));
            locationChanged = true;
        } else {
            marker.setPosition(latLng);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}
