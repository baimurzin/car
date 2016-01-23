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

import application.com.car.entity.Route;

/**
 * Created by Zahit Talipov on 16.01.2016.
 */
public class ShowLocation {
    private static final CharacterStyle STYLE_BOLD = new StyleSpan(Typeface.BOLD);
    public static Marker marker;
    static GoogleMap map;
    static ShowLocation showLocation;
    static Context context;

    GoogleMap.CancelableCallback callback = new GoogleMap.CancelableCallback() {
        @Override
        public void onFinish() {
            marker.setTitle("{Загрузка}");
            marker.showInfoWindow();
            new GeocoderAsyncTask().execute(marker.getPosition());
        }

        @Override
        public void onCancel() {

        }
    };

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
        Route.setStartPoint(lng);
        marker.setPosition(lng);
        marker.setTitle("{Загрузка}");
        CameraPosition cameraPosition = CameraPosition.builder().target(lng).zoom(map.getCameraPosition().zoom).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        marker.showInfoWindow();
        new GeocoderAsyncTask().execute(lng);
    }

    public void show(LatLng lng, float zoom) {
        Route.setStartPoint(lng);
        marker.setPosition(lng);
        CameraPosition cameraPosition = CameraPosition.builder().target(lng).zoom(zoom).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), callback);

    }

    public void show(GoogleApiClient apiClient, final AutocompletePrediction prediction) {
        Places.GeoDataApi.getPlaceById(apiClient, prediction.getPlaceId()).setResultCallback(new ResultCallback<PlaceBuffer>() {
            @Override
            public void onResult(PlaceBuffer places) {
                LatLng latLng = places.get(0).getLatLng();
                Route.setStartPoint(latLng);
                marker.setPosition(latLng);
                Route.isExistStartPoint = true;
                marker.setTitle(prediction.getPrimaryText(STYLE_BOLD) + "," + prediction.getSecondaryText(STYLE_BOLD));
                CameraPosition cameraPosition = CameraPosition.builder().target(latLng).zoom(15).build();
                map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                marker.showInfoWindow();
            }
        });

    }

    private String format(Address address) {
        StringBuffer buffer = new StringBuffer();
        String[] strings = {address.getLocality(), address.getThoroughfare(), address.getFeatureName()};
        boolean isFirst = false;
        for (String s : strings) {
            if (s != null && !s.contains("Unnamed Rd")) {
                if (!isFirst) {
                    buffer.append(s);
                    isFirst = true;
                } else {
                    buffer.append("," + s);
                }
            }
        }
        if (buffer.length() == 0) {
            return "{Адрес не найден}";
        }
        return buffer.toString();
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
                    Route.isExistStartPoint = true;
                    return format(address);
                } else {
                    return context.getResources().getString(R.string.addressNotFound);
                }
            } catch (IOException e) {
                Route.isExistStartPoint = false;
                return context.getResources().getString(R.string.errorInternetConnection);
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
