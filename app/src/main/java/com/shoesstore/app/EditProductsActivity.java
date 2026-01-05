package com.shoesstore.app;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.shoesstore.app.adapters.ProductAdapter;
import com.shoesstore.app.database.DatabaseHelper;
import com.shoesstore.app.models.Product;
import java.util.List;

public class EditProductsActivity extends AppCompatActivity {

    private RecyclerView productsRecyclerView;
    private ProductAdapter productAdapter;
    private DatabaseHelper database;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Reuse main layout for product selection
        
        ImageView backButton = findViewById(R.id.menuIcon);
        backButton.setOnClickListener(v -> finish());
        
        database = DatabaseHelper.getInstance(this);
        
        setupRecyclerView();
        loadProducts();
    }
    
    private void setupRecyclerView() {
        productsRecyclerView = findViewById(R.id.productsRecyclerView);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        productsRecyclerView.setLayoutManager(layoutManager);
    }
    
    private void loadProducts() {
        List<Product> products = database.getAllProducts();
        
        productAdapter = new ProductAdapter(this, products, product -> {
            // Open edit form for selected product
            Toast.makeText(this, "Edit: " + product.getName(), Toast.LENGTH_SHORT).show();
            // TODO: Implement edit product activity
        });
        
        productsRecyclerView.setAdapter(productAdapter);
    }
}
