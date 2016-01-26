package application.com.car.listeners;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.PolyUtil;

import application.com.car.AppDelegate;
import application.com.car.R;
import application.com.car.adapters.SearchAddressAdapter;
import application.com.car.entity.ItemRoute;
import application.com.car.entity.Route;
import application.com.car.entity.RouteResponse;
import application.com.car.service.ApiClientGoogleFactory;
import application.com.car.service.BuildRoute;
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
    Callback<RouteResponse> responseCallback = new Callback<RouteResponse>() {
        @Override
        public void onResponse(Response<RouteResponse> response, Retrofit retrofit) {
            getRoute(response.body());
        }

        @Override
        public void onFailure(Throwable t) {
            t.printStackTrace();
            Toast.makeText(context, context.getResources().getString(R.string.errorInternetConnection), Toast.LENGTH_SHORT).show();
        }
    };

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
                context.getResources().getString(R.string.google_maps_key), "ru").enqueue(responseCallback);
    }


    private void getRoute(RouteResponse routeResponse) {
        map.clear();
        Route.setEndAddress(routeResponse.getEndAddress());
        Route.setStartAddress(routeResponse.getStartAddress());
        Route.setRoute(PolyUtil.decode(routeResponse.getPoints()));
        new BuildRoute(context, map).buildRoute(Route.getRoute(),true);

    }
}
