package application.com.car.listeners;

import android.app.Activity;
import android.view.View;
import android.widget.Button;

import application.com.car.R;
import application.com.car.fragments.DateChoiceFragment;
import application.com.car.fragments.TimeChoiceFragment;

/**
 * Created by Zahit Talipov on 18.01.2016.
 */
public class ChoiceDatetimeListener implements View.OnClickListener {
    Activity context;

    public ChoiceDatetimeListener(Activity context) {
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSelectDate: {
                DateChoiceFragment dateChoiceFragment = new DateChoiceFragment((Button) v);
                dateChoiceFragment.show(context.getFragmentManager(), "selectDate");
                break;
            }
            case R.id.buttonSelectTime: {
                TimeChoiceFragment timeChoiceFragment = new TimeChoiceFragment((Button) v);
                timeChoiceFragment.show(context.getFragmentManager(), "selectTime");
                break;
            }
        }
    }
}
