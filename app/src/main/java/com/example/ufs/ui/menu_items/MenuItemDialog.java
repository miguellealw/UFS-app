package com.example.ufs.ui.menu_items;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.ufs.R;

public class MenuItemDialog extends DialogFragment {
    private EditText et_menuItemName;
    private EditText et_menuItemPrice;
    private MenuItemDialogListener listener;
    Bundle bundle;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        bundle = savedInstanceState;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Bundle args = getArguments();

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.menu_item_dialog, null);

        et_menuItemName = view.findViewById(R.id.et_dialog_menuItemName);
        et_menuItemPrice = view.findViewById(R.id.et_dialog_menuItemPrice);

        if(args != null) {
            et_menuItemName.setText(args.getString("menuItemName"));
            et_menuItemPrice.setText(Float.toString(args.getFloat("menuItemPrice")));
        }

        builder.setView(view)
                .setTitle(("Menu Item Information"))
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String menuItemName = et_menuItemName.getText().toString();
                        String menuItemPrice = et_menuItemPrice.getText().toString();

                        if(menuItemName.isEmpty() || menuItemPrice.isEmpty()){
                            Toast.makeText(getActivity(), "Fill out information to continue", Toast.LENGTH_SHORT).show();
                        } else {
                            listener.applyMenuItemTexts(menuItemName, menuItemPrice);
                        }

                    }
                });


        return builder.create();

        //return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            //listener = (MenuItemDialogListener) context;
            //listener = (MenuItemDialogListener) getTargetFragment();
            //listener = (MenuItemDialogListener) FragmentManager.setFragmentResult("", bundle);
            listener = (MenuItemDialogListener) requireParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement MenuItemDialogListener");
        }
    }


    public interface MenuItemDialogListener {
        void applyMenuItemTexts(String menuItemName, String menuItemPrice);
    }
}
