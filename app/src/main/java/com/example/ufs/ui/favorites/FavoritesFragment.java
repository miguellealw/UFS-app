package com.example.ufs.ui.favorites;

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
import android.widget.TextView;

import com.example.ufs.DatabaseHelper;
import com.example.ufs.R;
import com.example.ufs.SP_LocalStorage;
import com.example.ufs.data.model.Cart;
import com.example.ufs.data.model.FavoriteRestaurantModel;
import com.example.ufs.data.model.RestaurantModel;
import com.example.ufs.ui.restaurants.AllRestaurantsFragmentDirections;
import com.example.ufs.ui.restaurants.RestaurantAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoritesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoritesFragment extends Fragment {
    private RecyclerView recyclerView;
    private FavoritesAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoritesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoritesFragment newInstance(String param1, String param2) {
        FavoritesFragment fragment = new FavoritesFragment();
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
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        Context ctx = getActivity().getApplicationContext();
        DatabaseHelper dbo = new DatabaseHelper(ctx);
        TextView noFavoritesMessage = view.findViewById(R.id.noFavoritesMessage);

        Cart cart = Cart.getInstance();
        cart.clearCart();

        SP_LocalStorage sp = new SP_LocalStorage(ctx);
        int userId = sp.getLoggedInUserId();
        List<FavoriteRestaurantModel> favoritesList = dbo.getAllUserFavoriteRestaurants(userId);

        if(favoritesList != null) {
            // Set up recycler view and display
            recyclerView = view.findViewById(R.id.rv_favorites);
            recyclerView.setHasFixedSize(true);

            // Show list and hide no restaurants message
            recyclerView.setVisibility(View.VISIBLE);
            noFavoritesMessage.setVisibility(View.GONE);

            layoutManager = new LinearLayoutManager(ctx);
            recyclerView.setLayoutManager(layoutManager);

            // Add data to the recycler view
            mAdapter = new FavoritesAdapter(favoritesList, ctx);
            recyclerView.setAdapter(mAdapter);


            mAdapter.setOnItemClickListener(new FavoritesAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(FavoriteRestaurantModel favRestaurant) {
                    // ACTION TO RESTAURANT INFO FRAGMENT
                    FavoritesFragmentDirections.ActionFavoritesFragmentToRestaurantInfoFragment
                            action = FavoritesFragmentDirections
                            .actionFavoritesFragmentToRestaurantInfoFragment(
                                    favRestaurant.getRestaurantId(),
                                    favRestaurant.getRestaurantName(),
                                    favRestaurant.getRestaurantLocation()
                            );

                    NavController navController = Navigation.findNavController(view);
                    navController.navigate(action);

                }
            });
        }

        return view;
    }
}