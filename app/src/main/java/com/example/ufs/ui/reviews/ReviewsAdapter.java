package com.example.ufs.ui.reviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ufs.DatabaseHelper;
import com.example.ufs.R;
import com.example.ufs.data.model.OrderModel;
import com.example.ufs.data.model.ReviewModel;
import com.example.ufs.ui.orders.OrdersAdapter;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder> {
    List<ReviewModel> reviewList;
    Context context;
    private ReviewsAdapter.OnItemClickListener listener;
    DatabaseHelper dbo;

    String reviewUserName;
    String reviewRestaurantName;

    public ReviewsAdapter(List<ReviewModel> reviewList, Context context) {
        this.reviewList = reviewList;
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewsAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Get layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_line_review, parent, false);
        // Associate with menu item one line view holder
        ReviewsAdapter.ReviewViewHolder holder = new ReviewsAdapter.ReviewViewHolder(view);

        dbo = new DatabaseHelper(parent.getContext());

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsAdapter.ReviewViewHolder holder, int position) {
        reviewUserName = dbo.getUserById(reviewList.get(position).getUserId()).getFullName();
        reviewRestaurantName = dbo.getRestaurantById(reviewList.get(position).getRestaurantId()).getName();

        holder.tv_rating.setText(Integer.toString(reviewList.get(position).getRating()));
        holder.tv_review.setText(reviewList.get(position).getMessage());
        holder.tv_restaurantName.setText(reviewRestaurantName);
        holder.tv_userName.setText(reviewUserName);

        int id = reviewList.get(position).getId();

        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    // Reference to the one_line_menu_item view
    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView tv_rating;
        TextView tv_review;
        TextView tv_userName;
        TextView tv_restaurantName;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            tv_rating = itemView.findViewById(R.id.tv_list_review_rating);
            tv_review = itemView.findViewById(R.id.tv_list_review_review);
            tv_restaurantName = itemView.findViewById(R.id.tv_list_review_restaurantName);
            tv_userName = itemView.findViewById(R.id.tv_list_review_userName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(listener != null && pos != RecyclerView.NO_POSITION) {
                        listener.onItemClick(reviewList.get(pos));
                    }
                }
            });
        }
    }

    public void setOnItemClickListener(ReviewsAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(ReviewModel order);
    }
}
