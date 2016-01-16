package application.com.car;

import android.content.Context;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.text.style.CharacterStyle;
import android.text.style.StyleSpan;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.io.IOException;
import java.util.List;

/**
 * Created by Zahit Talipov on 16.01.2016.
 */
public class ShowLocation {
    private static final CharacterStyle STYLE_BOLD = new StyleSpan(Typeface.BOLD);
    public static Marker marker;
    static GoogleMap map;
    static ShowLocation showLocation;
    static Context context;

    private ShowLocation() {
    }

    public static ShowLocation getInstance(Context context, GoogleMap map, Marker marker) {
        ShowLocation.map = map;
        ShowLocation.marker = marker;
        ShowLocation.context = context;
        if (showLocation == null) {
            showLocation = new ShowLocation();
        }
        return showLocation;
    }

    public static ShowLocation getInstance() {
        if (showLocation == null) {
            showLocation = new ShowLocation();
        }
        return showLocation;
    }

    public void show(LatLng lng) {
        marker.setPosition(lng);
        marker.setTitle("{Загрузка}");
        CameraPosition cameraPosition = CameraPosition.builder().target(lng).zoom(map.getCameraPosition().zoom).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        marker.showInfoWindow();
        new GeocoderAsyncTask().execute(lng);
    }

    public void show(GoogleApiClient apiClient, final AutocompletePrediction prediction) {
        Places.GeoDataApi.getPlaceById(apiClient, prediction.getPlaceId()).setResultCallback(new ResultCallback<PlaceBuffer>() {
            @Override
            public void onResult(PlaceBuffer places) {
                LatLng latLng = places.get(0).getLatLng();
                marker.setPosition(latLng);
                marker.setTitle(prediction.getPrimaryText(STYLE_BOLD) + "," + prediction.getSecondaryText(STYLE_BOLD));
                CameraPosition cameraPosition = CameraPosition.builder().target(latLng).zoom(15).build();
                map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                marker.showInfoWindow();
            }
        });

    }

    private String format(Address address) {
        return String.format("%s, %s, %s", address.getLocality(), address.getThoroughfare(), address.getFeatureName());
    }

    class GeocoderAsyncTask extends AsyncTask<LatLng, String, String> {

        @Override
        protected String doInBackground(LatLng[] params) {
            try {
                Log.d("address", "loading");
                Geocoder geocoder = new Geocoder(context);
                List<Address> addresses = geocoder.getFromLocation(params[0].latitude, params[0].longitude, 1);
                if (!addresses.isEmpty()) {
                    Address address = addresses.get(0);
                    return format(address);
                } else {
                    throw new IOException();
                }
            } catch (IOException e) {
                return "{Адрес не найден}";
            }
        }

        @Override
        protected void onPostExecute(String o) {
            Log.d("address", o);
            marker.setTitle(o);
            marker.showInfoWindow();
            super.onPostExecute(o);
        }
    }

}
