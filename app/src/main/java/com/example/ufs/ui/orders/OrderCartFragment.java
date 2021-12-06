package com.example.ufs.ui.orders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ufs.DatabaseHelper;
import com.example.ufs.R;
import com.example.ufs.data.model.Cart;
import com.example.ufs.data.model.MenuItemModel;
import com.example.ufs.ui.menu_items.MenuItemsAdapter;

import java.util.Iterator;
import java.util.List;

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
    DatabaseHelper dbo;
    Cart cart;
    List<MenuItemModel> cartMenuItems;

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

    void removeItemFromCart(int id) {
        for(Iterator<MenuItemModel> it = cartMenuItems.iterator(); it.hasNext(); )  {
            MenuItemModel item = it.next();

            if(item.getId() == id) {
                it.remove();

                // update singleton
                cart.setMenuItems(cartMenuItems);

                // Update total
                cart.setTotal(cart.getTotal() - item.getPrice());
            }
        }
    }

    void removeMenuItem(int id) {
        //dbo.removeMenuItem(id);
        // remove menu item from list
        removeItemFromCart(id);

        // TODO: figure out how to update recycler view
        cartMenuItems = cart.getMenuItems();
        mAdapter = new MenuItemsAdapter(cartMenuItems, ctx);
        recyclerView.setAdapter(mAdapter);
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
        dbo = new DatabaseHelper(ctx);

        TextView tv_emptyCartMessage = view.findViewById(R.id.tv_cart_emtpyCartMessage);
        Button button_checkout = view.findViewById(R.id.button_cart_checkout);
        TextView tv_totalPrice = view.findViewById(R.id.tv_cart_orderTotal);

        cart = Cart.getInstance();
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
            cartMenuItems = cart.getMenuItems();
            mAdapter = new MenuItemsAdapter(cartMenuItems, ctx);
            recyclerView.setAdapter(mAdapter);

            new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView,
                                      @NonNull RecyclerView.ViewHolder viewHolder,
                                      @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    removeMenuItem((int) viewHolder.itemView.getTag());
                    tv_totalPrice.setText(String.format("%.02f", cart.getTotal()));

                    Toast.makeText(ctx, "Item Removed From Cart", Toast.LENGTH_SHORT).show();

                    if(cart.getMenuItems().isEmpty()) {
                        //isCartEmpty = true;
                        tv_emptyCartMessage.setVisibility(View.VISIBLE);
                        button_checkout.setEnabled(false);
                    }
                }
            }).attachToRecyclerView(recyclerView);
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