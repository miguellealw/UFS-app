package com.example.ufs.ui.menu_items;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ufs.R;
import com.example.ufs.data.model.MenuItemModel;

import java.util.List;

public class MenuItemsAdapter extends RecyclerView.Adapter<MenuItemsAdapter.MenuItemViewHolder> {

    List<MenuItemModel> menuItemList;
    Context context;
    private OnItemClickListener listener;

    public MenuItemsAdapter(List<MenuItemModel> menuItemList, Context context) {
        this.menuItemList = menuItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public MenuItemsAdapter.MenuItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Get layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_line_menu_item, parent, false);
        // Associate with menu item one line view holder
        MenuItemsAdapter.MenuItemViewHolder holder = new MenuItemsAdapter.MenuItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MenuItemsAdapter.MenuItemViewHolder holder, int position) {
        // Populate text views with list data
        holder.tv_menuItemName.setText(menuItemList.get(position).getName());
        holder.tv_menuItemPrice.setText(Float.toString(menuItemList.get(position).getPrice()));
        //holder.tv_menuItemPrice.setText(String.format("%0.02f", menuItemList.get(position).getPrice()));
        int id = menuItemList.get(position).getId();
        holder.itemView.setTag(id);
        //holder.iv_restaurantImage.setImage();

        // TODO: Use glide for images
    }

    @Override
    public int getItemCount() {
        return menuItemList.size();
    }

    // Reference to the one_line_menu_item view
    public class MenuItemViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_menuItemImage;
        TextView tv_menuItemName;
        TextView tv_menuItemPrice;

        public MenuItemViewHolder(View itemView) {
            super(itemView);
            iv_menuItemImage = itemView.findViewById(R.id.iv_list_restaurantImage);
            tv_menuItemName = itemView.findViewById(R.id.tv_list_menuItemName);
            tv_menuItemPrice = itemView.findViewById(R.id.tv_list_menuItemPrice);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(listener != null && pos != RecyclerView.NO_POSITION) {
                        listener.onItemClick(menuItemList.get(pos));
                    }
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(MenuItemModel menuItem);
    }


}
