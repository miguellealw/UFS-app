package com.example.ufs.ui.payment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ufs.DatabaseHelper;
import com.example.ufs.R;
import com.example.ufs.SP_LocalStorage;
import com.example.ufs.data.model.Cart;
import com.example.ufs.data.model.OrderModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MealPlanPayment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MealPlanPayment extends Fragment {
    Context ctx;
    Cart cart;
    DatabaseHelper dbo;

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
        dbo = new DatabaseHelper(ctx);
        SP_LocalStorage sp = new SP_LocalStorage(ctx);

        Button button_pay = view.findViewById(R.id.button_mealPlan_finalPayment);
        EditText et_uniID = view.findViewById(R.id.et_mealPlan_universityId);

        TextView orderTotal = view.findViewById(R.id.tv_mealPlan_total);
        TextView deliveryOption = view.findViewById(R.id.tv_mealPlan_deliveryOption);

        float cartTotal = cart.getIsPickup() ?  cart.getTotal() : cart.getTotal() + 5;

        orderTotal.setText(String.format("%.02f", cartTotal));
        deliveryOption.setText(cart.getIsPickup() ? "Pickup" : "Delivery (+ $5.00 delivery fee)");

        button_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if empty
                if(et_uniID.getText().toString().trim().equals("")) {
                    Toast.makeText(ctx, "Fill out all information to continue", Toast.LENGTH_LONG).show();
                } else {
                    float totalPrice = cart.getTotal();
                    boolean isDelivered = false;
                    boolean isPickup = cart.getIsPickup();
                    String address = cart.getAddress();
                    boolean isCreditCard = cart.getIsCreditCard();
                    // restaurantID is set in RestaurantInfoFragment
                    int restaurantID = cart.getRestaurantID();
                    int userID = sp.getLoggedInUserId();

                    // Credit card is valid. Place order
                    OrderModel newOrder = new OrderModel(
                            totalPrice,
                            isDelivered,
                            isPickup,
                            address,
                            isCreditCard,
                            restaurantID,
                            userID
                    );
                    dbo.addOrder(newOrder);

                    Toast.makeText(ctx, "Order has been placed", Toast.LENGTH_LONG).show();

                    // TODO: clear cart
                    cart.clearCart();
                }
            }
        });

        return view;
    }
}