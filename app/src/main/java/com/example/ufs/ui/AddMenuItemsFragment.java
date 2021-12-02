package com.example.ufs.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ufs.DatabaseHelper;
import com.example.ufs.R;
import com.example.ufs.data.model.MenuItemModel;
import com.example.ufs.data.model.RestaurantModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddMenuItemsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddMenuItemsFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddMenuItemsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddMenuItemsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddMenuItemsFragment newInstance(String param1, String param2) {
        AddMenuItemsFragment fragment = new AddMenuItemsFragment();
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
        View view = inflater.inflate(R.layout.fragment_add_menu_items, container, false);
        Context ctx = getActivity().getApplicationContext();

        DatabaseHelper dbo = new DatabaseHelper(ctx);
        //List<MenuItemModel> menuItemList = dbo.getAllRestaurantMenuItems();
        List<MenuItemModel> menuItemList = new ArrayList<>();

        //List<MenuItemModel> menuItemList = null;
        TextView noMenuItemsMessage = view.findViewById(R.id.noMenuItemsMessage);
        EditText et_menuItemName = view.findViewById(R.id.et_menuItemName);
        EditText et_menuItemPrice = view.findViewById(R.id.et_menuItemPrice);
        Button button_addMenuItem = view.findViewById(R.id.button_addMenuItem);

        boolean isArgsAvailable = getArguments() != null;
        int restaurantId;

        if(isArgsAvailable) {
            AddMenuItemsFragmentArgs args = AddMenuItemsFragmentArgs
                    .fromBundle(getArguments());
            restaurantId =  (int) args.getRestaurantId();

            // TODO: if restaurant has menu items insert to menuItemList
            menuItemList = dbo.getAllRestaurantMenuItems(restaurantId);
        }


        button_addMenuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get restaurant id as argument
                if(isArgsAvailable) {
                    AddMenuItemsFragmentArgs args = AddMenuItemsFragmentArgs
                            .fromBundle(getArguments());
                    int restaurantId =  (int) args.getRestaurantId();
                    Log.i("AddMenuFrag", "restaurant ID from menu items " + restaurantId);

                    // Get data from edit texts
                    String menuItemName = et_menuItemName.getText().toString();
                    float menuItemPrice = Float.parseFloat(et_menuItemPrice.getText().toString());

                    // Create menu item in DB
                    MenuItemModel newMenuItem = new MenuItemModel(menuItemName, menuItemPrice, restaurantId);
                    int menuItemId = dbo.addMenuItem(newMenuItem);

                    // Add menu item to local array list to display in recycler view
                    if(menuItemId != -1) {
                        //menuItemList.add(newMenuItem);
                        // TODO: consider making menuItemList a member variable
                        List<MenuItemModel> menuItemList = dbo.getAllRestaurantMenuItems(restaurantId);

                        // TODO: find better way of updating recycler view
                        mAdapter = new MenuItemsRecyclerView(menuItemList, ctx);
                        recyclerView.setAdapter(mAdapter);

                        et_menuItemName.setText("");
                        et_menuItemPrice.setText("");

                        Toast.makeText(ctx, newMenuItem.getName() + " Created", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ctx, "Error creating menu item", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        //if(menuItemList != null) {
        // If there are menu items display in recycler view
        if(menuItemList.size() > 0) {
            // Set up recycler view and display
            recyclerView = view.findViewById(R.id.rv_menuItems);
            recyclerView.setHasFixedSize(true);

            recyclerView.setVisibility(View.VISIBLE);
            noMenuItemsMessage.setVisibility(View.GONE);

            layoutManager = new LinearLayoutManager(ctx);
            recyclerView.setLayoutManager(layoutManager);

            //mAdapter = new MyAdapter(restaurantList);
            mAdapter = new MenuItemsRecyclerView(menuItemList, ctx);
            recyclerView.setAdapter(mAdapter);
        }


        return view;
    }
}