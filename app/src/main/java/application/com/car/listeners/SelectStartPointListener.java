package application.com.car.listeners;

import android.view.View;
import android.widget.AdapterView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.AutocompletePrediction;

import application.com.car.ShowLocation;
import application.com.car.adapters.SearchAddressAdapter;

/**
 * Created by Zahit Talipov on 17.01.2016.
 */
public class SelectStartPointListener implements AdapterView.OnItemClickListener {
    SearchAddressAdapter searchAddressAdapter;
    GoogleApiClient googleApiClient;

    public SelectStartPointListener(SearchAddressAdapter searchAddressAdapter, GoogleApiClient googleApiClient) {
        this.searchAddressAdapter = searchAddressAdapter;
        this.googleApiClient = googleApiClient;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AutocompletePrediction prediction = searchAddressAdapter.getItem(position);
        ShowLocation.getInstance().show(googleApiClient, prediction);
    }
}
