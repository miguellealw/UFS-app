package com.example.ufs.ui.reviews;

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
import com.example.ufs.SP_LocalStorage;
import com.example.ufs.data.model.OrderModel;
import com.example.ufs.data.model.ReviewModel;
import com.example.ufs.ui.orders.OrdersAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReviewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReviewsFragment extends Fragment {
    private RecyclerView recyclerView;
    private ReviewsAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReviewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReviewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReviewsFragment newInstance(String param1, String param2) {
        ReviewsFragment fragment = new ReviewsFragment();
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
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);
        Context ctx = getActivity().getApplicationContext();

        SP_LocalStorage sp = new SP_LocalStorage(ctx);
        boolean isStudent = sp.getIsStudent();

        DatabaseHelper dbo = new DatabaseHelper(ctx);
        List<ReviewModel> reviewsList;
        TextView noReviewsMessage = view.findViewById(R.id.noReviewsMessage);

        int userId = sp.getLoggedInUserId();

        // TODO: if isStudent show the student's reviews
        //  if not isStudent then show the restaurants reviews

        if(isStudent) {
            reviewsList = dbo.getAllStudentReviews(userId);

            if(reviewsList != null) {
                // Set up recycler view and display
                recyclerView = view.findViewById(R.id.rv_reviews);
                recyclerView.setHasFixedSize(true);

                // Show list and hide no restaurants message
                recyclerView.setVisibility(View.VISIBLE);
                noReviewsMessage.setVisibility(View.GONE);

                layoutManager = new LinearLayoutManager(ctx);
                recyclerView.setLayoutManager(layoutManager);

                // Add data to the recycler view
                mAdapter = new ReviewsAdapter(reviewsList, ctx);
                recyclerView.setAdapter(mAdapter);
            }
        } else {
            // show restaurant reviews
            int restaurantId = dbo.getRestaurantByUserId(userId).getId();
            reviewsList = dbo.getAllRestaurantReviews(restaurantId);

            if(reviewsList != null) {
                // Set up recycler view and display
                recyclerView = view.findViewById(R.id.rv_reviews);
                recyclerView.setHasFixedSize(true);

                // Show list and hide no restaurants message
                recyclerView.setVisibility(View.VISIBLE);
                noReviewsMessage.setVisibility(View.GONE);

                layoutManager = new LinearLayoutManager(ctx);
                recyclerView.setLayoutManager(layoutManager);

                // Add data to the recycler view
                mAdapter = new ReviewsAdapter(reviewsList, ctx);
                recyclerView.setAdapter(mAdapter);
            }
        }

        return view;
    }
}