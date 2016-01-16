package application.com.car.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import application.com.car.R;

/**
 * Created by Zahit Talipov on 14.01.2016.
 */
public class AllEntriesFragment extends Fragment implements View.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_entries_layout, null, true);
        getActivity().setTitle("Car");
        FloatingActionButton actionButton = (FloatingActionButton) view.findViewById(R.id.addEntry);
        actionButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        getFragmentManager().beginTransaction().replace(R.id.frameLayoutRoot, new AddRouteFragment())
                .addToBackStack(null).commit();
    }
}
