package com.example.ufs.ui.favorites;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ufs.R;
import com.example.ufs.data.model.FavoriteRestaurantModel;
import com.example.ufs.data.model.RestaurantModel;
import com.example.ufs.ui.restaurants.RestaurantAdapter;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder> {
    List<FavoriteRestaurantModel> favoritesList;
    Context context;
    private FavoritesAdapter.OnItemClickListener listener;

    public FavoritesAdapter(List<FavoriteRestaurantModel> favoritesList, Context context) {
        this.favoritesList = favoritesList;
        this.context = context;
    }

    @NonNull
    @Override
    public FavoritesAdapter.FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Get layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_line_restaurant, parent, false);
        // Associate with restaurant one line view holder
        FavoritesAdapter.FavoriteViewHolder holder = new FavoritesAdapter.FavoriteViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesAdapter.FavoriteViewHolder holder, int position) {
        // Populate text views with list data
        holder.tv_restaurantName.setText(favoritesList.get(position).getRestaurantName());
        holder.tv_restaurantLocation.setText(favoritesList.get(position).getRestaurantLocation());
        //int id = favoritesList.get(position).getId();

        //holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return favoritesList.size();
    }

    // Reference to the one_line_restaurant view
    public class FavoriteViewHolder extends RecyclerView.ViewHolder {
        TextView tv_restaurantName;
        TextView tv_restaurantLocation;

        public FavoriteViewHolder(View itemView) {
            super(itemView);
            tv_restaurantName = itemView.findViewById(R.id.tv_list_restaurantName);
            tv_restaurantLocation = itemView.findViewById(R.id.tv_list_restaurantLocation);

            // When restaurant is clicked send the restaurant model to the event handler
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(listener != null && pos != RecyclerView.NO_POSITION) {
                        listener.onItemClick(favoritesList.get(pos));
                    }
                }
            });
        }
    }

    public void setOnItemClickListener(FavoritesAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(FavoriteRestaurantModel favorite);
    }

}
