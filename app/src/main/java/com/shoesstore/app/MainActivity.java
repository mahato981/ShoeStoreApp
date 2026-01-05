package com.shoesstore.app;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import androidx.core.view.GravityCompat;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.shoesstore.app.adapters.CartAdapter;
import com.shoesstore.app.adapters.ProductAdapter;
import com.shoesstore.app.database.DatabaseHelper;
import com.shoesstore.app.models.CartItem;
import com.shoesstore.app.models.Product;
import com.shoesstore.app.utils.CartManager;
import com.shoesstore.app.utils.DataSeeder;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private RecyclerView productsRecyclerView;
    private ProductAdapter productAdapter;
    private EditText searchInput;
    private TextView resultsText, cartBadge, filterBadge;
    private ImageView menuIcon;
    private FrameLayout cartIcon, filterButton;
    
    private DatabaseHelper database;
    private List<Product> allProducts;
    private List<Product> filteredProducts;
    
    // Filter drawers
    private View menuDrawer, filterDrawer, cartDrawer;
    
    // Filter state
    private List<String> selectedCategories = new ArrayList<>();
    private List<String> selectedSubCategories = new ArrayList<>();
    private List<Integer> selectedSizes = new ArrayList<>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Initialize database and seed data
        database = DatabaseHelper.getInstance(this);
        DataSeeder.seedDatabase(this);
        
        initializeViews();
        setupProductsGrid();
        setupDrawers();
        setupListeners();
        loadProducts();
    }
    
    private void initializeViews() {
        drawerLayout = findViewById(R.id.drawerLayout);
        productsRecyclerView = findViewById(R.id.productsRecyclerView);
        searchInput = findViewById(R.id.searchInput);
        resultsText = findViewById(R.id.resultsText);
        cartBadge = findViewById(R.id.cartBadge);
        filterBadge = findViewById(R.id.filterBadge);
        menuIcon = findViewById(R.id.menuIcon);
        cartIcon = findViewById(R.id.cartContainer);
        filterButton = findViewById(R.id.filterButton);
    }
    
    private void setupProductsGrid() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        productsRecyclerView.setLayoutManager(layoutManager);
        
        filteredProducts = new ArrayList<>();
        productAdapter = new ProductAdapter(this, filteredProducts, product -> {
            Intent intent = new Intent(MainActivity.this, ProductDetailActivity.class);
            intent.putExtra("PRODUCT_ID", product.getId());
            startActivity(intent);
        });
        productsRecyclerView.setAdapter(productAdapter);
    }
    
    private void setupDrawers() {
        // Inflate drawer layouts programmatically
        menuDrawer = getLayoutInflater().inflate(R.layout.drawer_menu, null);
        filterDrawer = getLayoutInflater().inflate(R.layout.drawer_filter, null);
        cartDrawer = getLayoutInflater().inflate(R.layout.drawer_cart, null);
        
        setupMenuDrawer();
        setupFilterDrawer();
        setupCartDrawer();
    }
    
    private void setupMenuDrawer() {
        Button addProducts = menuDrawer.findViewById(R.id.menuAddProducts);
        Button editProducts = menuDrawer.findViewById(R.id.menuEditProducts);
        
        addProducts.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddProductActivity.class));
            closeAllDrawers();
        });
        
        editProducts.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, EditProductsActivity.class));
            closeAllDrawers();
        });
        
        // Other menu items can be implemented similarly
    }
    
    private void setupFilterDrawer() {
        TextView filterClose = filterDrawer.findViewById(R.id.filterCloseButton);
        Button applyFilters = filterDrawer.findViewById(R.id.applyFiltersButton);
        
        filterClose.setOnClickListener(v -> closeDrawer(filterDrawer));
        applyFilters.setOnClickListener(v -> {
            applyFilters();
            closeDrawer(filterDrawer);
        });
        
        // Setup filter options dynamically
        setupFilterCategories();
        setupFilterSizes();
    }
    
    private void setupFilterCategories() {
        LinearLayout mainCategoryContainer = filterDrawer.findViewById(R.id.mainCategoryContainer);
        String[] categories = {"Male", "Female", "Kids"};
        String[] categoryIcons = {"👨", "👩", "👶"};
        
        for (int i = 0; i < categories.length; i++) {
            final String category = categories[i];
            View categoryView = createCategoryCheckbox(category, categoryIcons[i]);
            mainCategoryContainer.addView(categoryView);
        }
    }
    
    private View createCategoryCheckbox(String category, String icon) {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setPadding(32, 32, 32, 32);
        layout.setGravity(Gravity.CENTER_VERTICAL);
        
        // Add checkbox, icon, and label
        // This is simplified - in production you'd use proper CheckBox views
        
        layout.setOnClickListener(v -> {
            if (selectedCategories.contains(category)) {
                selectedCategories.remove(category);
            } else {
                selectedCategories.add(category);
            }
            updateFilterBadge();
        });
        
        return layout;
    }
    
    private void setupFilterSizes() {
        // Similar implementation for size filter grid
    }
    
    private void setupCartDrawer() {
        TextView cartClose = cartDrawer.findViewById(R.id.cartCloseButton);
        Button proceedCheckout = cartDrawer.findViewById(R.id.proceedCheckoutButton);
        RecyclerView cartRecyclerView = cartDrawer.findViewById(R.id.cartItemsRecyclerView);
        
        cartClose.setOnClickListener(v -> closeDrawer(cartDrawer));
        
        proceedCheckout.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CheckoutActivity.class));
            closeDrawer(cartDrawer);
        });
        
        // Setup cart RecyclerView
        cartRecyclerView.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(this));
        CartAdapter cartAdapter = new CartAdapter(this, CartManager.getInstance().getCartItems(), 
            item -> {
                CartManager.getInstance().removeFromCart(item.getCartKey());
                updateCartUI();
            });
        cartRecyclerView.setAdapter(cartAdapter);
    }
    
    private void setupListeners() {
        menuIcon.setOnClickListener(v -> openDrawer(menuDrawer));
        cartIcon.setOnClickListener(v -> openDrawer(cartDrawer));
        filterButton.setOnClickListener(v -> openDrawer(filterDrawer));
        
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterProducts();
            }
            
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
    
    private void loadProducts() {
        allProducts = database.getAllProducts();
        filteredProducts.clear();
        filteredProducts.addAll(allProducts);
        productAdapter.notifyDataSetChanged();
        updateResultsText();
    }
    
    private void filterProducts() {
        String searchQuery = searchInput.getText().toString().toLowerCase().trim();
        filteredProducts.clear();
        
        for (Product product : allProducts) {
            boolean matchesSearch = searchQuery.isEmpty() || matchesSearchQuery(product, searchQuery);
            boolean matchesCategory = selectedCategories.isEmpty() || selectedCategories.contains(product.getCategory());
            boolean matchesSubCategory = selectedSubCategories.isEmpty() || selectedSubCategories.contains(product.getSubCategory());
            boolean matchesSize = selectedSizes.isEmpty() || containsAnySize(product.getSizes(), selectedSizes);
            
            if (matchesSearch && matchesCategory && matchesSubCategory && matchesSize) {
                filteredProducts.add(product);
            }
        }
        
        productAdapter.notifyDataSetChanged();
        updateResultsText();
    }
    
    private boolean matchesSearchQuery(Product product, String query) {
        return product.getName().toLowerCase().contains(query) ||
               product.getCategory().toLowerCase().contains(query) ||
               product.getSubCategory().toLowerCase().contains(query) ||
               String.valueOf(product.getShelfLocation()).contains(query) ||
               String.valueOf(product.getRowLocation()).contains(query);
    }
    
    private boolean containsAnySize(List<Integer> productSizes, List<Integer> filterSizes) {
        for (Integer size : filterSizes) {
            if (productSizes.contains(size)) {
                return true;
            }
        }
        return false;
    }
    
    private void applyFilters() {
        filterProducts();
    }
    
    private void updateResultsText() {
        resultsText.setText(filteredProducts.size() + " Results");
    }
    
    private void updateFilterBadge() {
        int filterCount = selectedCategories.size() + selectedSubCategories.size() + selectedSizes.size();
        if (filterCount > 0) {
            filterBadge.setVisibility(View.VISIBLE);
            filterBadge.setText(String.valueOf(filterCount));
        } else {
            filterBadge.setVisibility(View.GONE);
        }
    }
    
    private void updateCartUI() {
        int cartCount = CartManager.getInstance().getCartCount();
        if (cartCount > 0) {
            cartBadge.setVisibility(View.VISIBLE);
            cartBadge.setText(String.valueOf(cartCount));
        } else {
            cartBadge.setVisibility(View.GONE);
        }
    }
    
    private void openDrawer(View drawer) {
        closeAllDrawers();
        
        DrawerLayout.LayoutParams params = new DrawerLayout.LayoutParams(
            DrawerLayout.LayoutParams.WRAP_CONTENT,
            DrawerLayout.LayoutParams.MATCH_PARENT
        );
        
        if (drawer == menuDrawer) {
            params.gravity = GravityCompat.START;
        } else {
            params.gravity = GravityCompat.END;
        }
        
        drawer.setLayoutParams(params);
        
        if (drawer.getParent() != null) {
            ((DrawerLayout)drawer.getParent()).removeView(drawer);
        }
        
        drawerLayout.addView(drawer);
        
        if (drawer == menuDrawer) {
            drawerLayout.openDrawer(GravityCompat.START);
        } else {
            drawerLayout.openDrawer(GravityCompat.END);
        }
    }
    
    private void closeDrawer(View drawer) {
        if (drawer.getParent() != null) {
            drawerLayout.closeDrawers();
        }
    }
    
    private void closeAllDrawers() {
        drawerLayout.closeDrawers();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        updateCartUI();
        loadProducts();
    }
    
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START) || drawerLayout.isDrawerOpen(GravityCompat.END)) {
            closeAllDrawers();
        } else {
            super.onBackPressed();
        }
    }
}
