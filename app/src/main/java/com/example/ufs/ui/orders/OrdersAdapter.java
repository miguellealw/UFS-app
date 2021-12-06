package com.example.ufs.ui.orders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ufs.DatabaseHelper;
import com.example.ufs.R;
import com.example.ufs.data.model.MenuItemModel;
import com.example.ufs.data.model.OrderModel;

import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrderViewHolder> {
    List<OrderModel> orderList;
    Context context;
    private OrdersAdapter.OnItemClickListener listener;
    DatabaseHelper dbo;

    String orderName;
    String orderRestaurantName;

    public OrdersAdapter(List<OrderModel> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrdersAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Get layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_line_order, parent, false);
        // Associate with menu item one line view holder
        OrdersAdapter.OrderViewHolder holder = new OrdersAdapter.OrderViewHolder(view);

        dbo = new DatabaseHelper(parent.getContext());


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersAdapter.OrderViewHolder holder, int position) {
        // Populate text views with list data
        // totalPrice
        // delivery status
        // pickup / delivery
        // Address
        // payment option
        // name of user
        // restaurant

        orderName = dbo.getUserById(orderList.get(position).getUserID()).getFullName();
        orderRestaurantName = dbo.getRestaurantById(orderList.get(position).getRestaurantID()).getName();

        holder.tv_order_name.setText(orderName);
        holder.tv_order_price.setText(String.format("%.02f", orderList.get(position).getTotalPrice()));
        holder.tv_order_deliveryStatus.setText(orderList.get(position).getIsDelivered() ? "Delivered" : "In Progress");
        holder.tv_order_restaurantName.setText(orderRestaurantName);
        holder.tv_order_time.setText(orderList.get(position).getTimestamp());
        int id = orderList.get(position).getId();

        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    // Reference to the one_line_menu_item view
    public class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tv_order_name;
        TextView tv_order_price;
        TextView tv_order_deliveryStatus;
        TextView tv_order_restaurantName;
        TextView tv_order_time;

        public OrderViewHolder(View itemView) {
            super(itemView);
            tv_order_name = itemView.findViewById(R.id.tv_order_name);
            tv_order_price = itemView.findViewById(R.id.tv_order_price);
            tv_order_deliveryStatus = itemView.findViewById(R.id.tv_order_deliveryStatus);
            tv_order_restaurantName = itemView.findViewById(R.id.tv_order_restaurantName);
            tv_order_time = itemView.findViewById(R.id.tv_order_time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(listener != null && pos != RecyclerView.NO_POSITION) {
                        listener.onItemClick(orderList.get(pos));
                    }
                }
            });
        }
    }

    public void setOnItemClickListener(OrdersAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(OrderModel order);
    }
}
