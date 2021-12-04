package com.example.ufs.ui.restaurants;

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
import android.widget.Button;
import android.widget.TextView;

import com.example.ufs.DatabaseHelper;
import com.example.ufs.R;
import com.example.ufs.data.model.MenuItemModel;
import com.example.ufs.ui.menu_items.MenuItemsAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RestaurantInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantInfoFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_RESTAURANT_ID = "restaurantId";
    private static final String ARG_RESTAURANT_NAME = "restaurantName";
    private static final String ARG_RESTAURANT_LOCATION = "restaurantLocation";

    private RecyclerView rv_menuItemList;
    private MenuItemsAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private int restaurantId;
    private String restaurantName;
    private String restaurantLocation;

    DatabaseHelper dbo;

    Context ctx;

    public RestaurantInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RestaurantInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RestaurantInfoFragment newInstance(String param1, String param2) {
        RestaurantInfoFragment fragment = new RestaurantInfoFragment();
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
            restaurantId = getArguments().getInt(ARG_RESTAURANT_ID);
            restaurantName = getArguments().getString(ARG_RESTAURANT_NAME);
            restaurantLocation = getArguments().getString(ARG_RESTAURANT_LOCATION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_info, container, false);
        ctx = getActivity().getApplicationContext();
        dbo = new DatabaseHelper(ctx);

        Button favoriteButton = view.findViewById(R.id.favoriteRestaurantButton);
        Button reviewButton = view.findViewById(R.id.reviewRestaurantButton);
        FloatingActionButton goToCartButton = view.findViewById(R.id.goToCartButton);

        TextView et_name = view.findViewById(R.id.tv_restaurantInfoName);
        TextView et_location = view.findViewById(R.id.tv_restaurantInfoLocation);
        et_name.setText(restaurantName);
        et_location.setText(restaurantLocation);

        rv_menuItemList = view.findViewById(R.id.rv_restaurantInfoMenuItems);

        List<MenuItemModel> menuItemList = new ArrayList<>();

        // get menu items by restaurant id
        menuItemList = dbo.getAllRestaurantMenuItems(restaurantId);

        if(menuItemList != null && menuItemList.size() > 0) {
            layoutManager = new LinearLayoutManager(ctx);
            rv_menuItemList.setLayoutManager(layoutManager);

            //mAdapter = new MyAdapter(restaurantList);
            mAdapter = new MenuItemsAdapter(menuItemList, ctx);
            rv_menuItemList.setAdapter(mAdapter);
        }

        goToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = RestaurantInfoFragmentDirections
                        .actionRestaurantInfoFragmentToOrderCartFragment();

                NavController navController = Navigation.findNavController(view);
                navController.navigate(action);
            }
        });


        return view;
    }
}