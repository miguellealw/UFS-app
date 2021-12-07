package com.example.ufs.ui.orders;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ufs.R;
import com.example.ufs.SP_LocalStorage;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderRestaurantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderRestaurantFragment extends Fragment implements CancelOrderDialog.CancelOrderDialogListener {
    private String orderName;
    private boolean orderIsDelivered;
    private String orderTime;
    private boolean isCreditCard;
    private String orderAddress;
    private float orderPrice;
    private int orderId;

    Button markDeliveredButton;
    TextView tv_deliveryStatus;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String ORDER_NAME = "orderName";
    private static final String ORDER_IS_DELIVERED = "orderIsDelivered";
    private static final String ORDER_TIME = "orderTime";
    private static final String ORDER_IS_CREDIT_CARD = "orderIsCreditCard";
    private static final String ORDER_ADDRESS = "orderAddress";
    private static final String ORDER_PRICE = "orderPrice";
    private static final String ORDER_ID = "orderId";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OrderRestaurantFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderRestaurantFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderRestaurantFragment newInstance(String param1, String param2) {
        OrderRestaurantFragment fragment = new OrderRestaurantFragment();
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
            orderName = getArguments().getString(ORDER_NAME);
            orderIsDelivered = getArguments().getBoolean(ORDER_IS_DELIVERED);
            orderTime = getArguments().getString(ORDER_TIME);
            isCreditCard = getArguments().getBoolean(ORDER_IS_CREDIT_CARD);
            orderAddress = getArguments().getString(ORDER_ADDRESS);
            orderPrice = getArguments().getFloat(ORDER_PRICE);
            orderId = getArguments().getInt(ORDER_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order_restaurant, container, false);
        Context ctx = getActivity().getApplicationContext();
        SP_LocalStorage sp = new SP_LocalStorage(ctx);
        boolean isStudent = sp.getIsStudent();

        markDeliveredButton = view.findViewById(R.id.btn_restaurant_order_markDelivered);
        if(orderIsDelivered) {
            markDeliveredButton.setText("Order Delivered");
            markDeliveredButton.setEnabled(false);
        }


        TextView tv_orderName = view.findViewById(R.id.tv_restaurant_order_name);
        tv_orderName.setText(orderName);

        tv_deliveryStatus = view.findViewById(R.id.tv_restaurant_order_delStatus);
        tv_deliveryStatus.setText(orderIsDelivered ? "Delivered" : "In Progress");

        TextView tv_time = view.findViewById(R.id.tv_restaurant_order_time);
        tv_time.setText(orderTime);

        TextView tv_paymentOp = view.findViewById(R.id.tv_restaurant_order_paymentOp);
        tv_paymentOp.setText(isCreditCard ? "Credit Card" : "Meal Plan");

        TextView tv_address = view.findViewById(R.id.tv_restaurant_order_address);
        tv_address.setText(orderAddress.isEmpty() ? "N/A" : orderAddress);
        Log.i("OrderREst", "addres" + orderAddress);

        TextView tv_total_price = view.findViewById(R.id.tv_restaurant_order_price);
        tv_total_price.setText(String.format("%.02f", orderPrice));

        markDeliveredButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelOrderDialog dialog = new CancelOrderDialog();

                // Pass the order ID to the dialog
                Bundle args = new Bundle();
                args.putInt("orderId", orderId);
                args.putBoolean("isStudent", isStudent);
                args.putBoolean("isDelivered", orderIsDelivered);
                dialog.setArguments(args);

                dialog.show(getChildFragmentManager(), "CancelOrderDialog");
            }
        });
        return view;
    }

    @Override
    public void updateOrderList() {
        tv_deliveryStatus.setText("Delivered");
        markDeliveredButton.setText("Order Delivered");
        markDeliveredButton.setEnabled(false);
    }
}