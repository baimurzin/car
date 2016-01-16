package application.com.car.listeners;

import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;

/**
 * Created by Zahit Talipov on 15.01.2016.
 */
public class CameraListener implements GoogleMap.OnCameraChangeListener {
    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        Log.d("wed",cameraPosition.target.latitude+" "+cameraPosition.target.longitude);
    }
}
