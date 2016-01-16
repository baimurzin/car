package application.com.car.adapters;

import android.view.View;
import android.widget.AdapterView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.AutocompletePrediction;

import application.com.car.ShowLocation;

/**
 * Created by Zahit Talipov on 17.01.2016.
 */
public class SelectAdapter implements AdapterView.OnItemClickListener {
    SearchAddressAdapter searchAddressAdapter;
    GoogleApiClient googleApiClient;

    public SelectAdapter(SearchAddressAdapter searchAddressAdapter, GoogleApiClient googleApiClient) {
        this.searchAddressAdapter = searchAddressAdapter;
        this.googleApiClient = googleApiClient;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AutocompletePrediction prediction = searchAddressAdapter.getItem(position);
        ShowLocation.getInstance().show(googleApiClient, prediction);
    }
}
