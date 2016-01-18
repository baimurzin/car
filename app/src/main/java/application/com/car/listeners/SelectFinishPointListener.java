package application.com.car.listeners;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import java.util.List;

import application.com.car.R;
import application.com.car.adapters.SearchAddressAdapter;
import application.com.car.entity.Route;
import application.com.car.entity.RouteResponse;
import application.com.car.service.ApiClientGoogleFactory;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Zahit Talipov on 17.01.2016.
 */
public class SelectFinishPointListener implements AdapterView.OnItemClickListener {
    public static GoogleMap map;
    public static Marker finishMarker;
    SearchAddressAdapter searchAddressAdapter;
    GoogleApiClient apiClient;
    Context context;

    public SelectFinishPointListener(Context context, SearchAddressAdapter searchAddressAdapter, GoogleMap map, GoogleApiClient apiClient) {
        this.searchAddressAdapter = searchAddressAdapter;
        this.map = map;
        this.context = context;
        this.apiClient = apiClient;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        AutocompletePrediction prediction = searchAddressAdapter.getItem(position);
        Places.GeoDataApi.getPlaceById(apiClient, prediction.getPlaceId()).setResultCallback(new ResultCallback<PlaceBuffer>() {
            @Override
            public void onResult(PlaceBuffer places) {
                Route.setFinishPoint(places.get(0).getLatLng());
                showRoute();
            }
        });

    }

    private void showRoute() {
        ApiClientGoogleFactory.getApiGoogle().getRoute(Route.getStartPointToString(),
                Route.getFinishPointToString(), true,
                context.getResources().getString(R.string.api_key)).enqueue(new Callback<RouteResponse>() {
            @Override
            public void onResponse(Response<RouteResponse> response, Retrofit retrofit) {
                getRoute(response.body());
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(context, "Проверьте подключение к интернету", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getRoute(RouteResponse routeResponse) {
        map.clear();
        List<LatLng> mPoints = PolyUtil.decode(routeResponse.getPoints());
        PolylineOptions line = new PolylineOptions();
        line.width(5f).color(R.color.colorAccent);
        LatLngBounds.Builder latLngBuilder = new LatLngBounds.Builder();
        for (int i = 0; i < mPoints.size(); i++) {
            if (i == 0) {
                MarkerOptions startMarkerOptions = new MarkerOptions()
                        .position(mPoints.get(i))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_current));
                map.addMarker(startMarkerOptions);
            } else if (i == mPoints.size() - 1) {
                MarkerOptions endMarkerOptions = new MarkerOptions()
                        .position(mPoints.get(i))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_current));
                map.addMarker(endMarkerOptions);
            }
            line.add(mPoints.get(i));
            latLngBuilder.include(mPoints.get(i));
        }
        map.addPolyline(line);
        int size = context.getResources().getDisplayMetrics().widthPixels;
        LatLngBounds latLngBounds = latLngBuilder.build();
        CameraUpdate track = CameraUpdateFactory.newLatLngBounds(latLngBounds, size, size, 70);
        map.animateCamera(track);
    }
}
