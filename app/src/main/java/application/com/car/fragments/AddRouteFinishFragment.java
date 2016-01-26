package application.com.car.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import application.com.car.R;
import application.com.car.adapters.SearchAddressAdapter;
import application.com.car.entity.Route;
import application.com.car.listeners.ChoiceDatetimeListener;
import application.com.car.listeners.ChoiceFinishButtonListener;
import application.com.car.listeners.SelectFinishPointListener;

/**
 * Created by Zahit Talipov on 17.01.2016.
 */
public class AddRouteFinishFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {
    static View view;
    GoogleMap map;
    AutoCompleteTextView autoCompleteTextView;
    GoogleApiClient googleApiClient;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.choice_finish_point, null, false);
        }
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Places.PLACE_DETECTION_API)
                .addApi(Places.GEO_DATA_API)
                .addOnConnectionFailedListener(this)
                .build();
        Button buttonSelectDate = (Button) view.findViewById(R.id.buttonSelectDate);
        Button buttonSelectTime = (Button) view.findViewById(R.id.buttonSelectTime);
        Button buttonFinish = (Button) view.findViewById(R.id.buttonFinish);
        buttonFinish.setOnClickListener(new ChoiceFinishButtonListener(getActivity()));
        ChoiceDatetimeListener choiceDatetimeListener = new ChoiceDatetimeListener(getActivity());
        buttonSelectDate.setOnClickListener(choiceDatetimeListener);
        buttonSelectTime.setOnClickListener(choiceDatetimeListener);
        autoCompleteTextView = (AutoCompleteTextView) view.findViewById(R.id.editTextSearchFinish);
        autoCompleteTextView.setText("");
        SearchAddressAdapter adapter = new SearchAddressAdapter(getActivity(), googleApiClient);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(new SelectFinishPointListener(getActivity(), adapter, map, googleApiClient));
        MapFragment mapFragment = MapFragment.newInstance();//инициализация карты
        getFragmentManager().beginTransaction().replace(R.id.map_start, mapFragment).commit();
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        SelectFinishPointListener.map = googleMap;
        CameraPosition cameraPosition = CameraPosition.builder().target(Route.getStartPoint()).zoom(14).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        // googleMap.getUiSettings().setAllGesturesEnabled(false);
        Marker marker = googleMap.addMarker(new MarkerOptions().position(Route.getStartPoint())
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_current)));
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onResume() {
        super.onResume();
        googleApiClient.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
        googleApiClient.disconnect();
    }
}
