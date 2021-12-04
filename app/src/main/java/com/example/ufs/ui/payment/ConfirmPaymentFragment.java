package com.example.ufs.ui.payment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ufs.R;
import com.example.ufs.data.model.Cart;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfirmPaymentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfirmPaymentFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ORDER_TOTAL = "orderTotal";
    private float orderTotal;

    int paymentOptionSelected;
    int deliveryOptionSelected;

    Context ctx;
    Cart cart;

    public ConfirmPaymentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConfirmPaymentScreen.
     */
    // TODO: Rename and change types and number of parameters
    public static ConfirmPaymentFragment newInstance(String param1, String param2) {
        ConfirmPaymentFragment fragment = new ConfirmPaymentFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            orderTotal = getArguments().getFloat(ORDER_TOTAL);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirm_payment, container, false);
        ctx = getActivity().getApplicationContext();
        cart = Cart.getInstance();

        Button button_pay = view.findViewById(R.id.button_pay);
        TextView tv_totalCost = view.findViewById(R.id.tv_orderSummaryTotal);
        tv_totalCost.setText(String.format("%.02f", orderTotal));

        // Payment Option
        Spinner sp_paymentOption = view.findViewById(R.id.sp_paymentOptions);
        ArrayAdapter<CharSequence> paymentOptionsAdapter = ArrayAdapter.createFromResource(
                ctx,
                R.array.paymentOptions,
                android.R.layout.simple_spinner_item
        );
        paymentOptionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_paymentOption.setAdapter(paymentOptionsAdapter);
        sp_paymentOption.setOnItemSelectedListener(this);

        // Payment Option
        Spinner sp_deliveryOption = view.findViewById(R.id.sp_deliveryOptions);
        ArrayAdapter<CharSequence> deliveryOptionsAdapter = ArrayAdapter.createFromResource(
                ctx,
                R.array.deliveryOptions,
                android.R.layout.simple_spinner_item
        );
        deliveryOptionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_deliveryOption.setAdapter(deliveryOptionsAdapter);
        sp_deliveryOption.setOnItemSelectedListener(this);

        button_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action;
                // TODO: send to address fragment if delivery is chosen
                // add payment option in cart singleton

                // credit card
                if(paymentOptionSelected == 0) {
                    action = ConfirmPaymentFragmentDirections
                            .actionConfirmPaymentFragmentToCreditCardPayment();
                } else {
                    action = ConfirmPaymentFragmentDirections
                            .actionConfirmPaymentFragmentToMealPlanPayment();
                }

                NavController navController = Navigation.findNavController(view);
                navController.navigate(action);
            }
        });


        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.sp_paymentOptions:
                paymentOptionSelected = position;
                Log.i("onItemSelected", "Position: " + position + " and id: " + id);
                break;
            case R.id.sp_deliveryOptions:
                deliveryOptionSelected = position;
                cart.setIsPickup(position == 1);
                Log.i("deliveryOptionSelected", "Position: " + position + " and id: " + id);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}