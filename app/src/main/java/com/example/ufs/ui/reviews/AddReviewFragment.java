package com.example.ufs.ui.reviews;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ufs.DatabaseHelper;
import com.example.ufs.R;
import com.example.ufs.SP_LocalStorage;
import com.example.ufs.data.model.Cart;
import com.example.ufs.data.model.RestaurantModel;
import com.example.ufs.data.model.ReviewModel;
import com.example.ufs.ui.restaurants.RestaurantInfoFragmentDirections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddReviewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddReviewFragment extends Fragment {
    Cart cart;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddReviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddReviewFramgent.
     */
    // TODO: Rename and change types and number of parameters
    public static AddReviewFragment newInstance(String param1, String param2) {
        AddReviewFragment fragment = new AddReviewFragment();
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
        View view = inflater.inflate(R.layout.fragment_add_review, container, false);
        Context ctx = getActivity().getApplicationContext();
        DatabaseHelper dbo = new DatabaseHelper(ctx);

        Button button_addReview = view.findViewById(R.id.button_addReview);
        TextView tv_restaurantName = view.findViewById(R.id.tv_review_restaurantName);
        RatingBar rb_review_rating = view.findViewById(R.id.rb_review_rating);
        EditText et_review_review = view.findViewById(R.id.et_review_review);

        SP_LocalStorage sp = new SP_LocalStorage(ctx);
        int userId = sp.getLoggedInUserId();

        cart = Cart.getInstance();
        RestaurantModel restaurant = dbo.getRestaurantById(cart.getRestaurantID());
        tv_restaurantName.setText(restaurant.getName());


        button_addReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_review_review.getText().toString().trim().equals("")) {
                    ReviewModel newReview = new ReviewModel(
                            (int) rb_review_rating.getRating(),
                            et_review_review.getText().toString(),
                            userId,
                            cart.getRestaurantID()
                    );

                    dbo.addReview(newReview);

                    Toast.makeText(ctx, "Review posted", Toast.LENGTH_SHORT).show();

                    NavDirections action = AddReviewFragmentDirections
                            .actionAddReviewFragmentToAllRestaurantsFragment();

                    NavController navController = Navigation.findNavController(view);
                    navController.navigate(action);
                } else {
                    Toast.makeText(ctx, "Could not publish review. Try again.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }
}