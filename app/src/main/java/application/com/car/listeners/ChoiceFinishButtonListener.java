package application.com.car.listeners;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import application.com.car.AppDelegate;
import application.com.car.entity.ItemRoute;
import application.com.car.entity.Route;
import application.com.car.fragments.AddRouteCommitFragment;
import application.com.car.service.BuildRoute;

/**
 * Created by Zahit Talipov on 20.01.2016.
 */
public class ChoiceFinishButtonListener implements View.OnClickListener {
    Activity context;

    public ChoiceFinishButtonListener(Activity context) {
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        try {
            Route.isReadyForCreate();
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return;
        }
        AddRouteCommitFragment commitFragment= new AddRouteCommitFragment();
        commitFragment.show(context.getFragmentManager(),"commit");
        Log.d("route", Route.string());
    }
}
