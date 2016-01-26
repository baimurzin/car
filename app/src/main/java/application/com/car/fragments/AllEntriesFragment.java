package application.com.car.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.shamanland.fab.ShowHideOnScroll;

import application.com.car.R;
import application.com.car.adapters.AllEntriesAdapter;
import application.com.car.listeners.RoutesItemClickListener;

/**
 * Created by Zahit Talipov on 14.01.2016.
 */
public class AllEntriesFragment extends Fragment implements View.OnClickListener {
    ListView list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_entries_layout, null, true);
        getActivity().setTitle("Car");
        FloatingActionButton actionButton = (FloatingActionButton) view.findViewById(R.id.addEntry);
        actionButton.setOnClickListener(this);
        list = (ListView) view.findViewById(R.id.listRoutes);
        list.setOnTouchListener(new ShowHideOnScroll(actionButton));
        AllEntriesAdapter allEntriesAdapter = new AllEntriesAdapter(getActivity(), view);
        list.setAdapter(allEntriesAdapter);
        list.setOnItemClickListener(new RoutesItemClickListener(allEntriesAdapter, getActivity()));
        return view;
    }

    @Override
    public void onClick(View v) {
        getFragmentManager().beginTransaction().replace(R.id.frameLayoutRoot, new AddRouteStartFragment())
                .addToBackStack(null).commit();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main,menu);
        menu.setGroupVisible(R.id.menuSortGroup,true);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
