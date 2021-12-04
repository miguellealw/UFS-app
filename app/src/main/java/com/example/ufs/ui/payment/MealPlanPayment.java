package com.example.ufs.ui.payment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ufs.R;
import com.example.ufs.data.model.Cart;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MealPlanPayment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MealPlanPayment extends Fragment {
    Context ctx;
    Cart cart;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MealPlanPayment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MealPlanPayment.
     */
    // TODO: Rename and change types and number of parameters
    public static MealPlanPayment newInstance(String param1, String param2) {
        MealPlanPayment fragment = new MealPlanPayment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meal_plan_payment, container, false);

        ctx = getActivity().getApplicationContext();
        cart = Cart.getInstance();

        TextView orderTotal = view.findViewById(R.id.tv_mealPlan_total);
        TextView deliveryOption = view.findViewById(R.id.tv_mealPlan_deliveryOption);

        float cartTotal = cart.getIsPickup() ?  cart.getTotal() : cart.getTotal() + 5;

        orderTotal.setText(String.format("%.02f", cartTotal));
        deliveryOption.setText(cart.getIsPickup() ? "Pickup" : "Delivery (+ $5.00 delivery fee)");

        return view;
    }
}