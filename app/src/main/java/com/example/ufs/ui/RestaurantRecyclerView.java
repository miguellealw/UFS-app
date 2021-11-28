package com.example.ufs.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ufs.R;
import com.example.ufs.data.model.RestaurantModel;

import java.util.List;

public class RestaurantRecyclerView extends RecyclerView.Adapter<RestaurantRecyclerView.RestaurantViewHolder> {

    List<RestaurantModel> restaurantList;
    Context context;

    public RestaurantRecyclerView(List<RestaurantModel> restaurantList, Context context) {
        this.restaurantList = restaurantList;
        this.context = context;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Get layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_line_restaurant, parent, false);
        // Associate with restaurant one line view holder
        RestaurantViewHolder holder = new RestaurantViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        // Populate text views with list data
        holder.tv_restaurantName.setText(restaurantList.get(position).getName());
        holder.tv_restaurantLocation.setText(restaurantList.get(position).getLocation());
        //holder.iv_restaurantImage.setImage();

        // TODO: Use glide for images
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    // Reference to the one_line_restaurant view
    public class RestaurantViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_restaurantImage;
        TextView tv_restaurantName;
        TextView tv_restaurantLocation;

        public RestaurantViewHolder(View itemView) {
            super(itemView);
            iv_restaurantImage = itemView.findViewById(R.id.iv_list_restaurantImage);
            tv_restaurantName = itemView.findViewById(R.id.tv_list_restaurantName);
            tv_restaurantLocation = itemView.findViewById(R.id.tv_list_restaurantLocation);
        }
    }
}
