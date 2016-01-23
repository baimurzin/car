package application.com.car.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import application.com.car.AppDelegate;
import application.com.car.R;
import application.com.car.entity.ItemRoute;
import application.com.car.entity.Route;

/**
 * Created by Zahit Talipov on 23.01.2016.
 */
public class AddRouteCommitFragment extends DialogFragment implements DialogInterface.OnClickListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton("ОК", this);
        builder.setNegativeButton("ОТМЕНА", this);
        View view = getActivity().getLayoutInflater().inflate(R.layout.add_commit, null);
        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case DialogInterface.BUTTON_NEGATIVE: {

                break;
            }
            case DialogInterface.BUTTON_POSITIVE: {
                AppDelegate.itemRoutes.add(new ItemRoute(
                        Route.getRoute(),
                        Route.getStartAddress(),
                        Route.getEndAddress(),
                        Route.getMinute(),
                        Route.getHour(),
                        Route.getMonth(),
                        Route.getDayOfMonth()));
                Route.clean();
                getFragmentManager().beginTransaction()
                        .replace(R.id.frameLayoutRoot, new AllEntriesFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            }
        }


    }
}
