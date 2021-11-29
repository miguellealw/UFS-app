package com.example.ufs.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ufs.DatabaseHelper;
import com.example.ufs.R;
import com.example.ufs.SP_LocalStorage;
import com.example.ufs.data.model.RestaurantModel;
import com.example.ufs.data.model.UserModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateRestaurantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateRestaurantFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public CreateRestaurantFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateRestaurantFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateRestaurantFragment newInstance(String param1, String param2) {
        CreateRestaurantFragment fragment = new CreateRestaurantFragment();
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
        View view = inflater.inflate(R.layout.fragment_create_restaurant, container, false);
        Context ctx = getActivity().getApplicationContext();

        Button createButton = view.findViewById(R.id.createRestaurantButton);
        Button deleteButton = view.findViewById(R.id.deleteRestaurantButton);

        SP_LocalStorage sp = new SP_LocalStorage(ctx);
        boolean isEditingRestaurant = sp.getIsEditingRestaurant();
        if (isEditingRestaurant) deleteButton.setVisibility(View.VISIBLE);


        // Create button is clicked
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get data from input fields
                EditText et_name = (EditText) view.findViewById(R.id.et_restaurantName);
                EditText et_location = (EditText) view.findViewById(R.id.et_restaurantLocation);

                // TODO: data validation

                // Get logged in user id
                SP_LocalStorage sp = new SP_LocalStorage(ctx);
                int user_id = sp.getLoggedInUserId();

                // Create model to pass to Database Object (dbo)
                RestaurantModel newRestaurant = new RestaurantModel(
                        et_name.getText().toString(),
                        et_location.getText().toString(),
                        user_id
                );

                DatabaseHelper dbo = new DatabaseHelper(ctx);
                boolean u_success = dbo.addRestaurant(newRestaurant);

                if (u_success) {

                    // Go back to restaurant fragment
                    @NonNull NavDirections action = CreateRestaurantFragmentDirections.actionCreateRestaurantFragmentToAddMenuItemsFragment();
                    NavController navController = Navigation.findNavController(view);
                    navController.navigate(action);

                    Toast.makeText(ctx, "Add your menu items", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(ctx, "Error creating restaurant. Try again", Toast.LENGTH_LONG).show();
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getArguments() != null) {
                    CreateRestaurantFragmentArgs args = CreateRestaurantFragmentArgs.fromBundle(getArguments());

                    DatabaseHelper dbo = new DatabaseHelper(ctx);
                    boolean success = dbo.removeRestaurant(args.getRestaurantId());

                    Log.i("Create restaurant: ",
                            "restaurantId arg " + args.getRestaurantId());
                    if(success) {
                        @NonNull NavDirections action = CreateRestaurantFragmentDirections.actionCreateRestaurantFragmentToRestaurantsFragment();
                        NavController navController = Navigation.findNavController(view);
                        navController.navigate(action);

                        Toast.makeText(ctx, "Restaurant deleted successfully", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(ctx, "Error deleting restaurant", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        return view;
    }
}