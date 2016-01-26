package application.com.car.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import application.com.car.R;

/**
 * Created by Zahit Talipov on 25.01.2016.
 */
public class ChoiceNumberFragment extends DialogFragment implements DialogInterface.OnClickListener, View.OnClickListener {
    private String number;

    public ChoiceNumberFragment(String number) {
        this.number = number;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setNegativeButton("ОТМЕНА", this);
        View view = getActivity().getLayoutInflater().inflate(R.layout.choice_telephon_nnumber, null);
        Button buttonCall = (Button) view.findViewById(R.id.buttonCall);
        buttonCall.setOnClickListener(this);
        Button buttonSend = (Button) view.findViewById(R.id.buttonSend);
        buttonSend.setOnClickListener(this);
        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonCall: {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number)));
            }
            case R.id.buttonSend: {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("sms:"
                        + number)));
            }
        }
    }
}
