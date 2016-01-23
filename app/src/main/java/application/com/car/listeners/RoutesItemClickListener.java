package application.com.car.listeners;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import application.com.car.R;
import application.com.car.adapters.AllEntriesAdapter;
import application.com.car.fragments.SingleRouteFragment;

/**
 * Created by Zahit Talipov on 23.01.2016.
 */
public class RoutesItemClickListener implements AdapterView.OnItemClickListener {
    AllEntriesAdapter allEntriesAdapter;
    Activity activity;

    public RoutesItemClickListener(AllEntriesAdapter allEntriesAdapter, Activity activity) {
        this.allEntriesAdapter = allEntriesAdapter;
        this.activity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SingleRouteFragment routeFragment = new SingleRouteFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        routeFragment.setArguments(bundle);
        activity.getFragmentManager().beginTransaction()
                .replace(R.id.frameLayoutRoot, routeFragment)
                .addToBackStack(null)
                .commit();
    }
}
