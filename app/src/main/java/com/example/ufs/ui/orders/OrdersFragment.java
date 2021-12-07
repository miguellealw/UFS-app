package com.example.ufs.ui.orders;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ufs.DatabaseHelper;
import com.example.ufs.R;
import com.example.ufs.SP_LocalStorage;
import com.example.ufs.data.model.OrderModel;
import com.example.ufs.data.model.RestaurantModel;
import com.example.ufs.data.model.UserModel;
import com.example.ufs.ui.restaurants.RestaurantAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrdersFragment extends Fragment implements CancelOrderDialog.CancelOrderDialogListener {
    private RecyclerView recyclerView;
    private OrdersAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    List<OrderModel> ordersList;
    int userId;

    DatabaseHelper dbo;

    View view;
    Context ctx;

    TextView noOrdersMessage;
    boolean isStudent;

    int restaurantId;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OrdersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrdersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrdersFragment newInstance(String param1, String param2) {
        OrdersFragment fragment = new OrdersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private void updateAdapter(List<OrderModel> ordersList) {
        // Add data to the recycler view
        mAdapter = new OrdersAdapter(ordersList, ctx);
        recyclerView.setAdapter(mAdapter);

        // Add click listener on order
        mAdapter.setOnItemClickListener(new OrdersAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(OrderModel order) {
                if(isStudent) {
                    CancelOrderDialog dialog = new CancelOrderDialog();

                    // Pass the order ID to the dialog
                    Bundle args = new Bundle();
                    args.putInt("orderId", order.getId());
                    args.putBoolean("isStudent", isStudent);
                    args.putBoolean("isDelivered", order.getIsDelivered());
                    dialog.setArguments(args);

                    dialog.show(getChildFragmentManager(), "CancelOrderDialog");
                } else {
                    UserModel user = dbo.getUserById(order.getUserID());
                    String address = order.getAddress();
                    NavDirections action =
                            OrdersFragmentDirections.actionOrdersFragmentToOrderRestaurantFragment(
                                    user.getFullName(),
                                    order.getIsDelivered(),
                                    order.getTimestamp(),
                                    order.getIsCreditCard(),
                                    //address == null ? "N/A" : address,
                                    order.getAddress(),
                                    order.getTotalPrice(),
                                    order.getId()
                            );

                    NavController navController = Navigation.findNavController(view);
                    navController.navigate(action);
                }

            }
        });
    }

    private void renderRecyclerView(List<OrderModel> ordersList) {
        if(ordersList != null) {
            // Set up recycler view and display
            recyclerView = view.findViewById(R.id.rv_orders);
            recyclerView.setHasFixedSize(true);

            // Show list
            recyclerView.setVisibility(View.VISIBLE);
            noOrdersMessage.setVisibility(View.GONE);

            layoutManager = new LinearLayoutManager(ctx);
            recyclerView.setLayoutManager(layoutManager);

            updateAdapter(ordersList);

            // Add data to the recycler view
            //mAdapter = new OrdersAdapter(ordersList, ctx);
            //recyclerView.setAdapter(mAdapter);


            // Add click listener on order
            //mAdapter.setOnItemClickListener(new OrdersAdapter.OnItemClickListener() {
            //    @Override
            //    public void onItemClick(OrderModel order) {
            //        CancelOrderDialog dialog = new CancelOrderDialog();

            //        // Pass the order ID to the dialog
            //        Bundle args = new Bundle();
            //        args.putInt("orderId", order.getId());
            //        args.putBoolean("isStudent", isStudent);
            //        args.putBoolean("isDelivered", order.getIsDelivered());
            //        dialog.setArguments(args);

            //        dialog.show(getChildFragmentManager(), "CancelOrderDialog");
            //    }
            //});
        }

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
        view = inflater.inflate(R.layout.fragment_orders, container, false);
        ctx = getActivity().getApplicationContext();

        SP_LocalStorage sp = new SP_LocalStorage(ctx);
        isStudent = sp.getIsStudent();

        dbo = new DatabaseHelper(ctx);
        noOrdersMessage = view.findViewById(R.id.noOrdersMessage);


        userId = sp.getLoggedInUserId();

        // If student get student orders
        if(isStudent) {
            ordersList = dbo.getAllStudentOrders(userId);
            renderRecyclerView(ordersList);
        } else {
            // If restaurant, get orders placed for that restaurant
            RestaurantModel restaurant = dbo.getRestaurantById(userId);

            // check if restaurant exists before getting id
            if(restaurant != null) {
                restaurantId = restaurant.getId();
                ordersList = dbo.getAllRestaurantOrders(restaurantId);
                renderRecyclerView(ordersList);
            }
        }



        return view;
    }

    // Event that fires from dialog
    @Override
    public void updateOrderList() {
        ordersList = isStudent ? dbo.getAllStudentOrders(userId)
                : dbo.getAllRestaurantOrders(restaurantId);

        if(ordersList != null) {
            updateAdapter(ordersList);
            //mAdapter = new OrdersAdapter(ordersList, ctx);
            //recyclerView.setAdapter(mAdapter);
        } else {
            // Pass empty array to avoid null pointer exception
            updateAdapter(new ArrayList<>());
            //mAdapter = new OrdersAdapter(new ArrayList<>(), ctx);
            //recyclerView.setAdapter(mAdapter);

            noOrdersMessage.setVisibility(View.VISIBLE);
        }
    }
}