package application.com.car.listeners;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import application.com.car.entity.MyLocation;

/**
 * Created by Zahit Talipov on 15.01.2016.
 */
public class MLocationListener implements LocationListener {
    Context context;

    public MLocationListener(Context context) {
        this.context = context;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("wef", location.getProvider());
        MyLocation.latitude = location.getLatitude();
        MyLocation.longitude = location.getLongitude();
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
