package application.com.car.listeners;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import application.com.car.R;
import application.com.car.entity.Route;
import application.com.car.fragments.AddRouteFinishFragment;

/**
 * Created by Zahit Talipov on 17.01.2016.
 */
public class ChoiceStartButtonListener implements View.OnClickListener {
    Activity activity;

    public ChoiceStartButtonListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        if (Route.isExistStartPoint) {
            MLocationListener.locationChanged = false;
            activity.getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayoutRoot, new AddRouteFinishFragment(), "finishPoint")
                    .addToBackStack(null).commit();
        } else {
            Toast.makeText(activity, activity.getResources().getString(R.string.notSelectedStartPoint), Toast.LENGTH_LONG).show();
        }

    }
}
