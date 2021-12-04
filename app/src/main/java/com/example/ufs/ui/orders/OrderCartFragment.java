package com.example.ufs.ui.orders;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ufs.R;
import com.example.ufs.data.model.Cart;
import com.example.ufs.ui.menu_items.MenuItemsAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderCartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderCartFragment extends Fragment {
    private RecyclerView recyclerView;
    //private RecyclerView.Adapter mAdapter;
    private MenuItemsAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    Context ctx;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OrderCartFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderCartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderCartFragment newInstance(String param1, String param2) {
        OrderCartFragment fragment = new OrderCartFragment();
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
        View view = inflater.inflate(R.layout.fragment_order_cart, container, false);
        ctx = getActivity().getApplicationContext();

        TextView tv_emptyCartMessage = view.findViewById(R.id.tv_cart_emtpyCartMessage);
        Button button_checkout = view.findViewById(R.id.button_cart_checkout);
        TextView tv_totalPrice = view.findViewById(R.id.tv_cart_orderTotal);

        Cart cart = Cart.getInstance();
        boolean isCartEmpty = cart.getMenuItems().size() == 0;

        tv_emptyCartMessage.setVisibility(isCartEmpty ? View.VISIBLE : View.GONE);
        button_checkout.setEnabled(!isCartEmpty);
        //tv_totalPrice.setText(Float.toString(cart.getTotal()));
        tv_totalPrice.setText(String.format("%.02f", cart.getTotal()));

        if(!isCartEmpty) {
            recyclerView = view.findViewById(R.id.rv_cart_menuItems);
            recyclerView.setHasFixedSize(true);

            recyclerView.setVisibility(View.VISIBLE);

            layoutManager = new LinearLayoutManager(ctx);
            recyclerView.setLayoutManager(layoutManager);

            //mAdapter = new MyAdapter(restaurantList);
            mAdapter = new MenuItemsAdapter(cart.getMenuItems(), ctx);
            recyclerView.setAdapter(mAdapter);
        }

        button_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderCartFragmentDirections.ActionOrderCartFragmentToConfirmPaymentFragment action =
                        OrderCartFragmentDirections.actionOrderCartFragmentToConfirmPaymentFragment(cart.getTotal());

                NavController navController = Navigation.findNavController(view);
                navController.navigate(action);
            }
        });

        return view;
    }
}