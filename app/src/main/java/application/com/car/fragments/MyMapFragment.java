package application.com.car.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.MapFragment;

import application.com.car.listeners.TouchableWrapper;

/**
 * Created by Zahit Talipov on 18.01.2016.
 */
public class MyMapFragment extends MapFragment {
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);
        TouchableWrapper touchableWrapper = new TouchableWrapper(getActivity());
        touchableWrapper.addView(view);
        return touchableWrapper;
    }

    @Nullable
    @Override
    public View getView() {
        return view;
    }
}
