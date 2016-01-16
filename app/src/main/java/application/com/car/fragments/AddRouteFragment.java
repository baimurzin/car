package application.com.car.fragments;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import application.com.car.R;
import application.com.car.ShowLocation;
import application.com.car.adapters.SearchAddressAdapter;
import application.com.car.adapters.SelectAdapter;
import application.com.car.entity.MyLocation;
import application.com.car.listeners.CameraListener;
import application.com.car.listeners.MLocationListener;
import application.com.car.listeners.TouchOnMapListener;

/**
 * Created by Zahit Talipov on 14.01.2016.
 */
public class AddRouteFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener, View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    public static AutoCompleteTextView editTextSearch;
    private GoogleMap mMap = null;
    private LocationManager manager;
    private LocationListener locationListener;
    private LinearLayout mapView;
    private GoogleApiClient googleApiClient;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choice_start_piont, null, false);
        ImageButton imageButton = (ImageButton) view.findViewById(R.id.imageButtonMyLocation);
        editTextSearch = (AutoCompleteTextView) view.findViewById(R.id.editTextSearch);
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Places.PLACE_DETECTION_API)
                .addApi(Places.GEO_DATA_API)
                .addOnConnectionFailedListener(this)
                .build();
        SearchAddressAdapter adapter=new SearchAddressAdapter(getActivity(), googleApiClient);

        editTextSearch.setAdapter(adapter);
        editTextSearch.setOnItemClickListener(new SelectAdapter(adapter,googleApiClient));
        imageButton.setOnClickListener(this);
        getActivity().setTitle("Создание нового маршрута");
        MapFragment mapFragment = MapFragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.map_start, mapFragment).commit();
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        googleApiClient.connect();
        manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            locationListener = new MLocationListener(getActivity());
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, locationListener);
            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10, locationListener);
        }
    }

    private int checkSelfPermission(String accessFineLocation) {
        return 1;
    }

    @Override
    public void onPause() {
        super.onPause();
        googleApiClient.disconnect();
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            manager.removeUpdates(locationListener);
            MyLocation.save(getActivity());
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setCompassEnabled(false);
        MyLocation.getLastSaved(getActivity());
        LatLng myLocation;
        if (MyLocation.latitude != 0) {
            myLocation = new LatLng(MyLocation.latitude, MyLocation.longitude);
            CameraPosition cameraPosition = CameraPosition.builder().target(myLocation).zoom(16).build();
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        } else {
            CameraPosition cameraPosition = CameraPosition.builder().target(new LatLng(0, 0)).zoom(1).build();
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(MyLocation.latitude, MyLocation.longitude))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_current)));
        ShowLocation.getInstance(getActivity(),mMap, marker);
        mMap.setOnCameraChangeListener(new CameraListener());
        mMap.setOnMapClickListener(new TouchOnMapListener(getActivity(), marker, mMap));
    }

    private void showLocation() {

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButtonMyLocation: {
                if (MyLocation.longitude != 0 && MyLocation.latitude != 0) {
                    LatLng myLocation = new LatLng(MyLocation.latitude, MyLocation.longitude);
                    CameraPosition cameraPosition = CameraPosition.builder().target(myLocation).zoom(17).build();
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
            }
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
