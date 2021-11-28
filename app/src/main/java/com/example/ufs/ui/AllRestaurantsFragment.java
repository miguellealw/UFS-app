package com.example.ufs.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ufs.DatabaseHelper;
import com.example.ufs.R;
import com.example.ufs.data.model.RestaurantModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllRestaurantsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllRestaurantsFragment extends Fragment {
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

    public AllRestaurantsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllRestaurantsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllRestaurantsFragment newInstance(String param1, String param2) {
        AllRestaurantsFragment fragment = new AllRestaurantsFragment();
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
        View view =  inflater.inflate(R.layout.fragment_all_restaurants, container, false);
        // Inflate the layout for this fragment
        Context ctx = getActivity().getApplicationContext();

        DatabaseHelper dbo = new DatabaseHelper(ctx);
        List<RestaurantModel> restaurantList = dbo.getAllRestaurants();
        TextView noRestaurantsMessage = view.findViewById(R.id.noRestaurantsStudentMessage);

        // If there are restaurants
        if(restaurantList != null) {
            // Set up recycler view and display
            recyclerView = view.findViewById(R.id.lv_restaurantList);
            recyclerView.setVisibility(View.VISIBLE);
            noRestaurantsMessage.setVisibility(View.GONE);
            recyclerView.setHasFixedSize(true);

            layoutManager = new LinearLayoutManager(ctx);
            recyclerView.setLayoutManager(layoutManager);

            //mAdapter = new MyAdapter(restaurantList);
            mAdapter = new RestaurantRecyclerView(restaurantList, ctx);
            recyclerView.setAdapter(mAdapter);
        }



        return view;
    }
}