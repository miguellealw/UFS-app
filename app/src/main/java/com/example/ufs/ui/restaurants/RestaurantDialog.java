package com.example.ufs.ui.restaurants;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.ufs.R;

public class RestaurantDialog extends AppCompatDialogFragment {
    private EditText et_dialog_restaurantName;
    private EditText et_dialog_restaurantLocation;
    private RestaurantDialog.RestaurantDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.restuarant_dialog, null);

        builder.setView(view)
                .setTitle(("Edit Restaurant Info"))
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = et_dialog_restaurantName.getText().toString();
                        String location = et_dialog_restaurantLocation.getText().toString();

                        listener.applyRestaurantDialogTexts(name, location);
                    }
                });

        et_dialog_restaurantName = view.findViewById(R.id.et_dialog_restaurantName);
        et_dialog_restaurantLocation = view.findViewById(R.id.et_dialog_menuItemName);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (RestaurantDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement RestaurantDialogListener");
        }
    }


    public interface RestaurantDialogListener {
        void applyRestaurantDialogTexts(String restaurantName, String restaurantLocation);
    }
}
