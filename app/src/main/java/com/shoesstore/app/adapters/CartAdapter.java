package com.shoesstore.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.shoesstore.app.R;
import com.shoesstore.app.models.CartItem;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    
    private Context context;
    private List<CartItem> cartItems;
    private OnCartItemActionListener listener;

    public interface OnCartItemActionListener {
        void onRemoveItem(CartItem item);
    }

    public CartAdapter(Context context, List<CartItem> cartItems, OnCartItemActionListener listener) {
        this.context = context;
        this.cartItems = cartItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        
        holder.cartItemEmoji.setText(item.getProduct().getImage());
        holder.cartItemName.setText(item.getProduct().getName());
        
        String categoryInfo = item.getProduct().getSubCategory() + " • Size: " + item.getSelectedSize();
        holder.cartItemCategory.setText(categoryInfo);
        
        holder.cartItemPrice.setText(item.getProduct().getDiscountPrice());
        
        holder.removeButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onRemoveItem(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public void updateCart(List<CartItem> newCartItems) {
        this.cartItems = newCartItems;
        notifyDataSetChanged();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView cartItemEmoji, cartItemName, cartItemCategory, cartItemPrice;
        Button removeButton;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            cartItemEmoji = itemView.findViewById(R.id.cartItemEmoji);
            cartItemName = itemView.findViewById(R.id.cartItemName);
            cartItemCategory = itemView.findViewById(R.id.cartItemCategory);
            cartItemPrice = itemView.findViewById(R.id.cartItemPrice);
            removeButton = itemView.findViewById(R.id.removeCartItemButton);
        }
    }
}
