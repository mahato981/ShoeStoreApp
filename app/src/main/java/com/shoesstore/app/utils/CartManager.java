package com.shoesstore.app.utils;

import com.shoesstore.app.models.CartItem;
import com.shoesstore.app.models.Product;
import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private List<CartItem> cartItems;

    private CartManager() {
        cartItems = new ArrayList<>();
    }

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addToCart(Product product, int selectedSize) {
        String cartKey = product.getId() + "-" + selectedSize;
        
        // Check if item already exists
        for (CartItem item : cartItems) {
            if (item.getCartKey().equals(cartKey)) {
                item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }
        
        // Add new item
        cartItems.add(new CartItem(product, selectedSize));
    }

    public void removeFromCart(String cartKey) {
        cartItems.removeIf(item -> item.getCartKey().equals(cartKey));
    }

    public void updateQuantity(String cartKey, int change) {
        for (CartItem item : cartItems) {
            if (item.getCartKey().equals(cartKey)) {
                int newQuantity = item.getQuantity() + change;
                if (newQuantity > 0) {
                    item.setQuantity(newQuantity);
                } else {
                    removeFromCart(cartKey);
                }
                break;
            }
        }
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public int getCartCount() {
        int count = 0;
        for (CartItem item : cartItems) {
            count += item.getQuantity();
        }
        return count;
    }

    public int getCartTotal() {
        int total = 0;
        for (CartItem item : cartItems) {
            String priceStr = item.getProduct().getDiscountPrice()
                    .replace("₹", "").replace(",", "");
            total += Integer.parseInt(priceStr) * item.getQuantity();
        }
        return total;
    }

    public int getMinPrice() {
        if (cartItems.isEmpty()) return 0;
        
        int minPrice = Integer.MAX_VALUE;
        for (CartItem item : cartItems) {
            String priceStr = item.getProduct().getDiscountPrice()
                    .replace("₹", "").replace(",", "");
            int price = Integer.parseInt(priceStr);
            if (price < minPrice) {
                minPrice = price;
            }
        }
        return minPrice;
    }

    public void clearCart() {
        cartItems.clear();
    }
}
