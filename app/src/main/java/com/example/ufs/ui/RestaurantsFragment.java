package com.example.ufs.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.NavHost;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ufs.DatabaseHelper;
import com.example.ufs.R;
import com.example.ufs.SP_LocalStorage;
import com.example.ufs.data.model.RestaurantModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RestaurantsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private final String TAG = "== RestaurantsFragment ";

    public RestaurantsFragment() {
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
    public static RestaurantsFragment newInstance(String param1, String param2) {
        RestaurantsFragment fragment = new RestaurantsFragment();
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
        View view = inflater.inflate(R.layout.fragment_restaurants, container, false);
        Context ctx = getActivity().getApplicationContext();

        // Get logged in user id
//        SharedPreferences sp = ctx.getSharedPreferences("sharedPrefs", ctx.MODE_PRIVATE);
        SP_LocalStorage sp = new SP_LocalStorage(ctx);
        int user_id = sp.getLoggedInUserId();

        DatabaseHelper dbo = new DatabaseHelper(ctx);
        RestaurantModel restaurant = dbo.getRestaurantByUserId(user_id);

        // Get views
        FloatingActionButton addButton = (FloatingActionButton) view.findViewById(R.id.createRestaurantButton);
        TextView noRestaurantsMessage = (TextView) view.findViewById(R.id.noRestaurantsMessage);
        TextView nameLabel = (TextView) view.findViewById(R.id.restaurantNameLabel);
        TextView locationLabel = (TextView) view.findViewById(R.id.restaurantLocationLabel);
        TextView name = (TextView) view.findViewById(R.id.restaurantName);
        TextView location = (TextView) view.findViewById(R.id.restaurantLocation);

        if (restaurant != null) {

            // TODO: Show restaurant title and location
            nameLabel.setVisibility(View.VISIBLE);
            locationLabel.setVisibility(View.VISIBLE);
            name.setVisibility(View.VISIBLE);
            location.setVisibility(View.VISIBLE);

            // Set name and location text
            name.setText(restaurant.getName());
            location.setText(restaurant.getLocation());


            // TODO: Show restaurant menu items

            // Hide add button and no restaurant message
            addButton.setVisibility(View.GONE);
            noRestaurantsMessage.setVisibility(View.GONE);
            //Log.i(TAG, restaurant.getName());
        } else {
            nameLabel.setVisibility(View.GONE);
            locationLabel.setVisibility(View.GONE);
            name.setVisibility(View.GONE);
            location.setVisibility(View.GONE);

            noRestaurantsMessage.setVisibility(View.VISIBLE);
            addButton.setVisibility(View.VISIBLE);
        }

        // Clicking the add restaurant action button
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform action of going to createRestaurant Fragment
                @NonNull NavDirections action = RestaurantsFragmentDirections.actionRestaurantsFragmentToCreateRestaurantFragment();
                NavController navController = Navigation.findNavController(view);
                navController.navigate(action);

                //NavController navController = NavHostFragment.findNavController(RestaurantsFragment.this);
                //Navigation.findNavController(v).navigate(action);
            }
        });

        return view;
    }
}