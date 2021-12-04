package com.example.ufs.ui.restaurants;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ufs.DatabaseHelper;
import com.example.ufs.R;
import com.example.ufs.SP_LocalStorage;
import com.example.ufs.data.model.MenuItemModel;
import com.example.ufs.data.model.RestaurantModel;
import com.example.ufs.ui.menu_items.MenuItemDialog;
import com.example.ufs.ui.menu_items.MenuItemsAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyRestaurantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyRestaurantFragment extends Fragment implements MenuItemDialog.MenuItemDialogListener
//        RestaurantDialog.RestaurantDialogListener
{
    private RecyclerView recyclerView;
    //private RecyclerView.Adapter mAdapter;
    private MenuItemsAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    FloatingActionButton addButton;
    FloatingActionButton editRestaurantButton;
    Button editMenuItemsButton;

    TextView nameLabel;
    TextView locationLabel;

    TextView noRestaurantsMessage;
    TextView noMenuItemsMessage;

    TextView name;
    TextView location;

    int restaurantId;

    DatabaseHelper dbo;

    Context ctx;

    List<MenuItemModel> menuItemList;

    View view;

    int selectedMenuItemId;
    boolean isEditingMenuItem = false;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private final String TAG = "== RestaurantsFragment ";

    public MyRestaurantFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment restaurants.
     */
    // TODO: Rename and change types and number of parameters
    public static MyRestaurantFragment newInstance(String param1, String param2) {
        MyRestaurantFragment fragment = new MyRestaurantFragment();
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

    public void openMenuItemDialog(Bundle args) {
        MenuItemDialog menuItemDialog = new MenuItemDialog();
        if(args != null) menuItemDialog.setArguments(args);
        menuItemDialog.show(getChildFragmentManager(), "MenuItemDialog");

        //menuItemDialog.show(getActivity().getSupportFragmentManager(), "Edit Menu Item");
        //menuItemDialog.show(getActivity().getSupportFragmentManager(), "Edit Menu Item");
        //Context ctx = getActivity().getApplicationContext();

        // pass getChildFragmentManager so it can be cast to MenuItemDialogListener
        // in the MenuItemDialog.java file
    }

    public void openRestaurantDialog() {
        RestaurantDialog restaurantDialog = new RestaurantDialog();
        restaurantDialog.show(getActivity().getSupportFragmentManager(), "Edit Restaurant Info");
    }

    void removeMenuItem(int id) {
        dbo.removeMenuItem(id);
        Toast.makeText(ctx, "Menu Item Deleted", Toast.LENGTH_LONG).show();

        // TODO: figure out how to update recycler view
        //mAdapter = new MenuItemsRecyclerView(menuItemList, ctx);
        //recyclerView.setAdapter(mAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_restaurants, container, false);
        ctx = getActivity().getApplicationContext();

        // Get logged in user id
        SP_LocalStorage sp = new SP_LocalStorage(ctx);
        SharedPreferences.Editor editor = sp.getEditor();
        int user_id = sp.getLoggedInUserId();

        dbo = new DatabaseHelper(ctx);
        RestaurantModel restaurant = dbo.getRestaurantByUserId(user_id);
        List<MenuItemModel> menuItemList = restaurant != null ?
                dbo.getAllRestaurantMenuItems(restaurant.getId()) : new ArrayList<>();

        restaurantId = restaurant != null ? restaurant.getId() : null;

        boolean userHasRestaurant = restaurant != null;
        // set isEditingRestaurant so createRestaurant fragment knows what to show
        editor.putBoolean("isEditingRestaurant", userHasRestaurant);
        editor.apply();
        //Log.i(TAG, " isEditingRestaurant " + sp.getIsEditingRestaurant());

        // Get views
        addButton = (FloatingActionButton) view.findViewById(R.id.createRestaurantButton);
        editRestaurantButton = (FloatingActionButton) view.findViewById(R.id.editRestaurantButton);
        editMenuItemsButton = (Button) view.findViewById(R.id.button_addMenuItems);
        noRestaurantsMessage = (TextView) view.findViewById(R.id.noRestaurantsMessage);
        nameLabel = (TextView) view.findViewById(R.id.restaurantNameLabel);
        locationLabel = (TextView) view.findViewById(R.id.restaurantLocationLabel);
        noMenuItemsMessage = (TextView) view.findViewById(R.id.tv_restaurants_noMenuItemsMessage);

        name = (TextView) view.findViewById(R.id.restaurantName);
        location = (TextView) view.findViewById(R.id.restaurantLocation);


        // If user has restaurant show the information and edit button;
        nameLabel.setVisibility(userHasRestaurant ? View.VISIBLE : View.GONE);
        locationLabel.setVisibility(userHasRestaurant ? View.VISIBLE : View.GONE);
        name.setVisibility(userHasRestaurant ? View.VISIBLE : View.GONE);
        location.setVisibility(userHasRestaurant ? View.VISIBLE : View.GONE);
        editRestaurantButton.setVisibility(userHasRestaurant ? View.VISIBLE : View.GONE);

        // Hide add button and no restaurant message
        addButton.setVisibility(userHasRestaurant ? View.GONE : View.VISIBLE);
        noRestaurantsMessage.setVisibility(userHasRestaurant ? View.GONE : View.VISIBLE);

        // If user has restaurant show edit menu items button
        editMenuItemsButton.setVisibility(userHasRestaurant ? View.VISIBLE : View.GONE);

        if (userHasRestaurant) {
            // Set name and location text
            name.setText(restaurant.getName());
            location.setText(restaurant.getLocation());

            // Edit restaurant button
            editRestaurantButton.setOnClickListener(new View.OnClickListener() {
                final String TAG = "editRestaurantButton";
                @Override
                public void onClick(View v) {
                    //openRestaurantDialog();

                    // Perform action of going to createRestaurant Fragment

                    // Navigate to add AddMenuItemsFragment
                    MyRestaurantFragmentDirections.
                            ActionRestaurantsFragmentToCreateRestaurantFragment action =
                            MyRestaurantFragmentDirections.actionRestaurantsFragmentToCreateRestaurantFragment();
                    action.setRestaurantId(restaurant.getId());
                    action.setRestaurantName(restaurant.getName());
                    action.setRestaurantLocation(restaurant.getLocation());

                    NavController navController = Navigation.findNavController(view);
                    navController.navigate(action);
                }
            });

            // Edit menu item button
            editMenuItemsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openMenuItemDialog(null);

                    //MyRestaurantFragmentDirections.ActionRestaurantsFragmentToAddMenuItemsFragment action =
                    //        MyRestaurantFragmentDirections.actionRestaurantsFragmentToAddMenuItemsFragment();
                    //action.setRestaurantId(restaurant.getId());

                    //NavController navController = Navigation.findNavController(view);
                    //navController.navigate(action);
                }
            });


            // Show restaurant menu items
            if(menuItemList != null && menuItemList.size() > 0) {
                // Set up recycler view and display
                recyclerView = view.findViewById(R.id.rv_myRestaurant_menuItems);
                recyclerView.setHasFixedSize(true);

                recyclerView.setVisibility(View.VISIBLE);
                noMenuItemsMessage.setVisibility(View.GONE);

                layoutManager = new LinearLayoutManager(ctx);
                recyclerView.setLayoutManager(layoutManager);

                //mAdapter = new MyAdapter(restaurantList);
                mAdapter = new MenuItemsAdapter(menuItemList, ctx);
                recyclerView.setAdapter(mAdapter);

                // REMOVE MENU ITEM
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
                    }
                }).attachToRecyclerView(recyclerView);

                // EDIT MENU ITEM
                mAdapter.setOnItemClickListener(new MenuItemsAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(MenuItemModel menuItem) {
                        selectedMenuItemId = menuItem.getId();
                        isEditingMenuItem = true;

                        Bundle args = new Bundle();
                        args.putInt("menuItemId", menuItem.getId());
                        args.putString("menuItemName", menuItem.getName());
                        args.putFloat("menuItemPrice", menuItem.getPrice());


                        openMenuItemDialog(args);
//                        DialogFragment dialog = new MenuItemDialog();
//                        dialog.setArguments(args);
//                        dialog.show(getSupportFragmentManager(), "TAG");
//                        MyRestaurantFragmentDirections.
//                                ActionRestaurantsFragmentToMenuItemDialog action =
//                                MyRestaurantFragmentDirections.actionRestaurantsFragmentToMenuItemDialog(
//                                   menuItem.getId(),
//                                    menuItem.getName(),
//                                    menuItem.getPrice()
//                                );
                        //action.setMenuItemId(menuItem.getId());
                        //action.setMenuItemName(menuItem.getName());
                        //action.setMenuItemPrice(menuItem.getPrice());

                        Log.i(TAG, "Menu ITEM CLICK: " + menuItem.getName());
                    }
                });
            } else {
                //recyclerView.setVisibility(View.GONE);
                noMenuItemsMessage.setVisibility(View.VISIBLE);
            }

            //Log.i(TAG, restaurant.getName());
        } else {
            // Clicking the add restaurant action button
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Perform action of going to createRestaurant Fragment
                    // Since add default values in nav_graph.xml gives error then I have to
                    // pass them in here
                    @NonNull NavDirections action = MyRestaurantFragmentDirections
                            .actionRestaurantsFragmentToCreateRestaurantFragment();
                    NavController navController = Navigation.findNavController(view);
                    navController.navigate(action);

                    //NavController navController = NavHostFragment.findNavController(RestaurantsFragment.this);
                    //Navigation.findNavController(v).navigate(action);
                }
            });

        }



        return view;
    }

    // After ok is tapped this will run
    @Override
    public void applyMenuItemTexts(String menuItemName, String menuItemPrice) {
        if(isEditingMenuItem) {
            // EDITING MENU ITEM
            boolean success = dbo.editMenuItem(selectedMenuItemId, menuItemName, menuItemPrice);
            isEditingMenuItem = false;

            if(success) {
                //menuItemList.add(newMenuItem);
                menuItemList = dbo.getAllRestaurantMenuItems(restaurantId);

                // TODO: find better way of updating recycler view
                mAdapter = new MenuItemsAdapter(menuItemList, ctx);
                recyclerView.setAdapter(mAdapter);

                Toast.makeText(ctx, "Menu Item Updated", Toast.LENGTH_LONG).show();
            }
        } else {
            // ADD MENU ITEM
            // Create menu item in DB
            MenuItemModel newMenuItem = new MenuItemModel(menuItemName, Float.parseFloat(menuItemPrice), restaurantId);
            int menuItemId = dbo.addMenuItem(newMenuItem);

            // If there is no recycler view then create one
            if(recyclerView == null) {
                recyclerView = view.findViewById(R.id.rv_myRestaurant_menuItems);
                recyclerView.setHasFixedSize(true);
            }

            // Add menu item to local array list to display in recycler view
            if(menuItemId != -1) {
                //menuItemList.add(newMenuItem);
                menuItemList = dbo.getAllRestaurantMenuItems(restaurantId);

                // TODO: find better way of updating recycler view
                mAdapter = new MenuItemsAdapter(menuItemList, ctx);
                recyclerView.setAdapter(mAdapter);

                Toast.makeText(ctx, newMenuItem.getName() + " Created", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(ctx, "Error creating menu item", Toast.LENGTH_LONG).show();
            }

        }

    }

//    @Override
//    public void applyRestaurantDialogTexts(String restaurantName, String restaurantLocation) {
//        name.setText(restaurantName);
//        location.setText(restaurantLocation);
//    }
}