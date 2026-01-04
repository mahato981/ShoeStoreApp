package com.shoesstore.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.shoesstore.app.R;
import com.shoesstore.app.models.CartItem;
import java.util.List;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.CheckoutViewHolder> {
    
    private Context context;
    private List<CartItem> cartItems;

    public CheckoutAdapter(Context context, List<CartItem> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public CheckoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_checkout, parent, false);
        return new CheckoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        
        holder.checkoutItemEmoji.setText(item.getProduct().getImage());
        holder.checkoutItemName.setText(item.getProduct().getName());
        
        String details = item.getProduct().getSubCategory() + " • Size: " + item.getSelectedSize();
        holder.checkoutItemDetails.setText(details);
        
        holder.checkoutItemPrice.setText(item.getProduct().getDiscountPrice());
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    static class CheckoutViewHolder extends RecyclerView.ViewHolder {
        TextView checkoutItemEmoji, checkoutItemName, checkoutItemDetails, checkoutItemPrice;

        public CheckoutViewHolder(@NonNull View itemView) {
            super(itemView);
            checkoutItemEmoji = itemView.findViewById(R.id.checkoutItemEmoji);
            checkoutItemName = itemView.findViewById(R.id.checkoutItemName);
            checkoutItemDetails = itemView.findViewById(R.id.checkoutItemDetails);
            checkoutItemPrice = itemView.findViewById(R.id.checkoutItemPrice);
        }
    }
}
