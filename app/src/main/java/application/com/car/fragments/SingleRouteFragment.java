package application.com.car.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

import application.com.car.AppDelegate;
import application.com.car.R;
import application.com.car.entity.ItemRoute;
import application.com.car.service.BuildRoute;

/**
 * Created by Zahit Talipov on 20.01.2016.
 */
public class SingleRouteFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener {
    View view;
    ItemRoute itemRoute;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null)
            view = inflater.inflate(R.layout.show_single_route, null, false);
        int pos = this.getArguments().getInt("position");
        itemRoute = AppDelegate.itemRoutes.get(pos);
        TextView textViewDate = (TextView) view.findViewById(R.id.textViewsShowSingleDate);
        TextView textViewTime = (TextView) view.findViewById(R.id.textViewShowSingleTime);
        TextView addressStart = (TextView) view.findViewById(R.id.textViewShowSingleStartAddress);
        TextView addressEnd = (TextView) view.findViewById(R.id.textViewShowSingleEndAddress);
        Button button = (Button) view.findViewById(R.id.buttonNumber);
        button.setOnClickListener(this);
        textViewDate.setText(itemRoute.getDate());
        textViewTime.setText(itemRoute.getTime());
        addressStart.setText(itemRoute.getAddressStart());
        addressEnd.setText(itemRoute.getAddressEnd());
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        BuildRoute buildRoute = new BuildRoute(getActivity(), googleMap);
        buildRoute.buildRoute(itemRoute.getRouteList(), false);
    }


    @Override
    public void onResume() {
        super.onResume();
        LinearLayout infoLayout = (LinearLayout) view.findViewById(R.id.info_layout);
        LinearLayout mapLayout = (LinearLayout) view.findViewById(R.id.map_layout);
        LinearLayout root = (LinearLayout) view.findViewById(R.id.root_layout);
        mapLayout.setMinimumHeight(root.getHeight() - infoLayout.getHeight());
        MapFragment mapFragment = MyMapFragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.map_route_single, mapFragment, "singleItem").commit();
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onClick(View v) {
        ChoiceNumberFragment choiceNumberFragment = new ChoiceNumberFragment(itemRoute.getNumberAuthor());
        choiceNumberFragment.show(getFragmentManager(), "number");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        menu.setGroupVisible(R.id.menuJoinGroup, true);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
