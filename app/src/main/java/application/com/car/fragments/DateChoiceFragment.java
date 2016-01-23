package application.com.car.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import application.com.car.R;
import application.com.car.entity.Route;

/**
 * Created by Zahit Talipov on 18.01.2016.
 */
public class DateChoiceFragment extends DialogFragment implements DialogInterface.OnClickListener {
    Button button;
    DatePicker datePicker;
    String[] months = {"Января", "Февраля", "Марта", "Апреля", "Мая", "Июня", "Июля", "Августа", "Сентября", "Октября", "Ноября", "Декабря"};

    public DateChoiceFragment(Button button) {
        this.button = button;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        builder.setPositiveButton("ОК", this);
        View view = layoutInflater.inflate(R.layout.choice_date, null);
        datePicker = (DatePicker) view.findViewById(R.id.datePickerFinish);
        builder.setView(view);
        return builder.create();
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        Route.setDate(datePicker.getDayOfMonth(),datePicker.getMonth());
        button.setText(datePicker.getDayOfMonth() + " " + months[datePicker.getMonth()]);
    }
}
