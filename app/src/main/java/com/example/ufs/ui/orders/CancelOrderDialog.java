package com.example.ufs.ui.orders;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.ufs.DatabaseHelper;
import com.example.ufs.ui.menu_items.MenuItemDialog;

public class CancelOrderDialog extends DialogFragment {
    DatabaseHelper dbo;
    private CancelOrderDialog.CancelOrderDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        dbo = new DatabaseHelper(getActivity());
        Bundle args = getArguments();

        boolean isStudent = false;
        boolean isDelivered = false;

        if(args != null) {
            isStudent = args.getBoolean("isStudent");
            isDelivered = args.getBoolean("isDelivered");
        }

        String title = isStudent ? "Cancel Order" : "Set order as delivered?";
        String message = isStudent ? "Are you sure you want to cancel your order?"
                : "Are you sure you want to set the order as delivered?";

        String positiveButton = isDelivered ? "Ok" : "Yes";

        if(isDelivered) {
           title = "Already delivered";
           message = "Order has already been delivered";
        }

        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(args != null) {
                            boolean isDelivered = args.getBoolean("isDelivered");

                            // Only allow cancelling and editing if order is not delivered
                            if(!isDelivered) {
                                int orderId = args.getInt("orderId");
                                boolean isStudent = args.getBoolean("isStudent");

                                if (isStudent) {
                                    // delete order
                                    dbo.removeOrder(orderId);
                                    Log.i("CancelOrder", "Yes. Cancel order #" + orderId);
                                    Toast.makeText(getActivity(), "Order cancelled", Toast.LENGTH_LONG).show();

                                } else {
                                    // TODO: update order status of order
                                    dbo.editRestaurantOrder(orderId, true);
                                    Log.i("ChangeOrderProgress", "Delivered Order #" + orderId);
                                    Toast.makeText(getActivity(), "Order delivered", Toast.LENGTH_LONG).show();
                                }

                            }

                            // Call event that will run in OrdersFragment
                            listener.updateOrderList();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (CancelOrderDialog.CancelOrderDialogListener) requireParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement CancelOrderDialogListener");
        }
    }


    public interface CancelOrderDialogListener {
        void updateOrderList();
    }
}
