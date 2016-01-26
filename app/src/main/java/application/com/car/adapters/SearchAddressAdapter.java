package application.com.car.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.text.style.CharacterStyle;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataBufferUtils;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import application.com.car.AppDelegate;
import application.com.car.R;

/**
 * Created by Zahit Talipov on 16.01.2016.
 */
public class SearchAddressAdapter extends ArrayAdapter<AutocompletePrediction> implements Filterable {

    private static final CharacterStyle STYLE_BOLD = new StyleSpan(Typeface.BOLD);
    ArrayList<AutocompletePrediction> autocompletePredictions;
    GoogleApiClient googleApiClient;
    private boolean isError;

    public SearchAddressAdapter(Context context, GoogleApiClient apiClient) {
        super(context, android.R.layout.simple_expandable_list_item_2, android.R.id.text1);
        googleApiClient = apiClient;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                // Skip the autocomplete query if no constraints are given.
                if (constraint != null) {
                    // Query the autocomplete API for the (constraint) search string.
                    autocompletePredictions = getAutocomplete(constraint);
                    if (autocompletePredictions != null) {
                        // The API successfully returned results.
                        results.values = autocompletePredictions;
                        results.count = autocompletePredictions.size();
                    }
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    // The API returned at least one result, update the data.
                    notifyDataSetChanged();
                } else {
                    // The API did not return any results, invalidate the data set.
                    notifyDataSetInvalidated();
                }
            }

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                // Override this method to display as readable result in the AutocompleteTextView
                // when clicked.
                if (resultValue instanceof AutocompletePrediction) {
                    return ((AutocompletePrediction) resultValue).getFullText(null);
                } else {
                    return super.convertResultToString(resultValue);
                }
            }
        };
    }

    @Override
    public int getCount() {
        return autocompletePredictions.size();
    }

    @Override
    public AutocompletePrediction getItem(int position) {
        return autocompletePredictions.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        AutocompletePrediction item = getItem(position);

        TextView textView1 = (TextView) view.findViewById(android.R.id.text1);
        TextView textView2 = (TextView) view.findViewById(android.R.id.text2);
        textView1.setText(item.getPrimaryText(CharacterStyle.wrap(STYLE_BOLD)));
        textView2.setText(item.getSecondaryText(STYLE_BOLD));

        return view;
    }

    private ArrayList<AutocompletePrediction> getAutocomplete(CharSequence constraint) {
        if (googleApiClient.isConnected()) {
            Log.i("wef", "Starting autocomplete query for: " + constraint);
            String textForSearch = AppDelegate.getLocalCity() + " " + constraint.toString();
            PendingResult<AutocompletePredictionBuffer> results =
                    Places.GeoDataApi
                            .getAutocompletePredictions(googleApiClient, textForSearch, new LatLngBounds(new LatLng(-90, -180), new LatLng(90, 180)), null);


            AutocompletePredictionBuffer autocompletePredictions = results.await(60, TimeUnit.SECONDS);

            final Status status = autocompletePredictions.getStatus();
            if (!status.isSuccess()) {
                if (!isError) {
                    Toast.makeText(getContext(), getContext().getResources().getString(R.string.errorInternetConnection), Toast.LENGTH_SHORT).show();
                    isError = true;
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(2500);
                                isError = false;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread.start();
                }
                Log.e("wef", "Error getting autocomplete prediction API call: " + status.toString());
                autocompletePredictions.release();
                return null;
            }
            isError = false;
            Log.i("wef", "Query completed. Received " + autocompletePredictions.getCount()
                    + " predictions.");
            return DataBufferUtils.freezeAndClose(autocompletePredictions);
        }
        return null;
    }


}
