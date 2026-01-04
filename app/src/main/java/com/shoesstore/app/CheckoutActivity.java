package com.shoesstore.app;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.shoesstore.app.adapters.CheckoutAdapter;
import com.shoesstore.app.utils.CartManager;

public class CheckoutActivity extends AppCompatActivity {

    private TextView checkoutBackButton, checkoutTotalPrice, minPriceText;
    private RecyclerView checkoutItemsRecyclerView;
    private EditText paymentAmountInput;
    private CheckBox showMinPriceCheckbox;
    private LinearLayout minPriceContainer;
    private Button soldButton;
    
    private CheckoutAdapter checkoutAdapter;
    private int totalAmount;
    private int minPrice;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        
        initializeViews();
        setupRecyclerView();
        calculateTotals();
        setupListeners();
        displayOrderSummary();
    }
    
    private void initializeViews() {
        checkoutBackButton = findViewById(R.id.checkoutBackButton);
        checkoutTotalPrice = findViewById(R.id.checkoutTotalPrice);
        minPriceText = findViewById(R.id.minPriceText);
        checkoutItemsRecyclerView = findViewById(R.id.checkoutItemsRecyclerView);
        paymentAmountInput = findViewById(R.id.paymentAmountInput);
        showMinPriceCheckbox = findViewById(R.id.showMinPriceCheckbox);
        minPriceContainer = findViewById(R.id.minPriceContainer);
        soldButton = findViewById(R.id.soldButton);
    }
    
    private void setupRecyclerView() {
        checkoutItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        checkoutAdapter = new CheckoutAdapter(this, CartManager.getInstance().getCartItems());
        checkoutItemsRecyclerView.setAdapter(checkoutAdapter);
    }
    
    private void calculateTotals() {
        totalAmount = CartManager.getInstance().getCartTotal();
        minPrice = CartManager.getInstance().getMinPrice();
        
        checkoutTotalPrice.setText("₹" + formatPrice(totalAmount));
        minPriceText.setText("₹" + formatPrice(minPrice));
    }
    
    private void displayOrderSummary() {
        if (CartManager.getInstance().getCartItems().isEmpty()) {
            Toast.makeText(this, "Cart is empty!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    
    private void setupListeners() {
        checkoutBackButton.setOnClickListener(v -> finish());
        
        showMinPriceCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            minPriceContainer.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        });
        
        paymentAmountInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validatePaymentAmount();
            }
            
            @Override
            public void afterTextChanged(Editable s) {}
        });
        
        soldButton.setOnClickListener(v -> processSale());
    }
    
    private void validatePaymentAmount() {
        String amountStr = paymentAmountInput.getText().toString();
        
        if (amountStr.isEmpty()) {
            // Default state
            paymentAmountInput.setBackgroundResource(R.drawable.input_default_bg);
            paymentAmountInput.setTextColor(Color.parseColor("#111827"));
            soldButton.setEnabled(false);
            soldButton.setBackgroundResource(R.drawable.gray_button_bg);
            soldButton.setTextColor(Color.parseColor("#6B7280"));
            return;
        }
        
        try {
            int enteredAmount = Integer.parseInt(amountStr);
            
            if (enteredAmount >= minPrice && enteredAmount > 0) {
                // Valid amount
                paymentAmountInput.setBackgroundResource(R.drawable.input_valid_bg);
                paymentAmountInput.setTextColor(Color.parseColor("#16A34A"));
                soldButton.setEnabled(true);
                soldButton.setBackgroundResource(R.drawable.orange_button_bg);
                soldButton.setTextColor(Color.WHITE);
            } else {
                // Invalid amount
                paymentAmountInput.setBackgroundResource(R.drawable.input_invalid_bg);
                paymentAmountInput.setTextColor(Color.parseColor("#DC2626"));
                soldButton.setEnabled(false);
                soldButton.setBackgroundResource(R.drawable.gray_button_bg);
                soldButton.setTextColor(Color.parseColor("#6B7280"));
            }
        } catch (NumberFormatException e) {
            paymentAmountInput.setBackgroundResource(R.drawable.input_invalid_bg);
            paymentAmountInput.setTextColor(Color.parseColor("#DC2626"));
            soldButton.setEnabled(false);
        }
    }
    
    private void processSale() {
        String amountStr = paymentAmountInput.getText().toString();
        
        if (amountStr.isEmpty()) {
            Toast.makeText(this, "Please enter payment amount!", Toast.LENGTH_SHORT).show();
            return;
        }
        
        try {
            int enteredAmount = Integer.parseInt(amountStr);
            
            if (enteredAmount >= minPrice) {
                // Process successful sale
                Toast.makeText(this, 
                    "Order placed successfully! Payment amount: ₹" + formatPrice(enteredAmount), 
                    Toast.LENGTH_LONG).show();
                
                // Clear cart
                CartManager.getInstance().clearCart();
                
                // Close checkout and return to main
                finish();
            } else {
                Toast.makeText(this, 
                    "Amount must be at least ₹" + formatPrice(minPrice), 
                    Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid amount!", Toast.LENGTH_SHORT).show();
        }
    }
    
    private String formatPrice(int price) {
        return String.format("%,d", price).replace(",", ",");
    }
}
