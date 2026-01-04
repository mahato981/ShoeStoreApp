package com.shoesstore.app.models;

public class CartItem {
    private Product product;
    private int selectedSize;
    private int quantity;
    private String cartKey;

    public CartItem(Product product, int selectedSize) {
        this.product = product;
        this.selectedSize = selectedSize;
        this.quantity = 1;
        this.cartKey = product.getId() + "-" + selectedSize;
    }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public int getSelectedSize() { return selectedSize; }
    public void setSelectedSize(int selectedSize) { this.selectedSize = selectedSize; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getCartKey() { return cartKey; }
    public void setCartKey(String cartKey) { this.cartKey = cartKey; }
}
