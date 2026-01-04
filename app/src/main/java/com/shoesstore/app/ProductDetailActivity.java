package com.shoesstore.app;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.shoesstore.app.database.DatabaseHelper;
import com.shoesstore.app.models.Product;
import com.shoesstore.app.utils.CartManager;

public class ProductDetailActivity extends AppCompatActivity {

    private Product product;
    private int selectedSize = -1;
    
    private View productGradient;
    private TextView productDetailEmoji, detailCategoryIcon, detailCategoryText;
    private TextView detailProductName, detailMainCategoryBadge, detailSubCategoryBadge;
    private TextView detailDiscountPrice, detailOriginalPrice, detailBoxNumber;
    private TextView detailCode, detailLocation, detailDescription;
    private TextView closeButton;
    private LinearLayout thumbnailContainer;
    private GridLayout sizeGrid;
    private ChipGroup availableSizesChipGroup;
    private Button addToCartButton;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        
        initializeViews();
        loadProduct();
        setupListeners();
    }
    
    private void initializeViews() {
        productGradient = findViewById(R.id.productGradient);
        productDetailEmoji = findViewById(R.id.productDetailEmoji);
        detailCategoryIcon = findViewById(R.id.detailCategoryIcon);
        detailCategoryText = findViewById(R.id.detailCategoryText);
        detailProductName = findViewById(R.id.detailProductName);
        detailMainCategoryBadge = findViewById(R.id.detailMainCategoryBadge);
        detailSubCategoryBadge = findViewById(R.id.detailSubCategoryBadge);
        detailDiscountPrice = findViewById(R.id.detailDiscountPrice);
        detailOriginalPrice = findViewById(R.id.detailOriginalPrice);
        detailBoxNumber = findViewById(R.id.detailBoxNumber);
        detailCode = findViewById(R.id.detailCode);
        detailLocation = findViewById(R.id.detailLocation);
        detailDescription = findViewById(R.id.detailDescription);
        closeButton = findViewById(R.id.closeButton);
        thumbnailContainer = findViewById(R.id.thumbnailContainer);
        sizeGrid = findViewById(R.id.sizeGrid);
        availableSizesChipGroup = findViewById(R.id.availableSizesChipGroup);
        addToCartButton = findViewById(R.id.addToCartButton);
    }
    
    private void loadProduct() {
        int productId = getIntent().getIntExtra("PRODUCT_ID", -1);
        if (productId == -1) {
            finish();
            return;
        }
        
        DatabaseHelper database = DatabaseHelper.getInstance(this);
        product = database.getProductById(productId);
        
        if (product == null) {
            finish();
            return;
        }
        
        displayProductDetails();
    }
    
    private void displayProductDetails() {
        // Set gradient background
        setGradientBackground(productGradient, product.getColor());
        
        // Set product emoji
        productDetailEmoji.setText(product.getImage());
        
        // Set category badge
        detailCategoryIcon.setText(getCategoryIcon(product.getCategory()));
        detailCategoryText.setText(product.getCategory());
        
        // Set product name
        detailProductName.setText(product.getName());
        
        // Set category badges
        detailMainCategoryBadge.setText(product.getCategory());
        detailSubCategoryBadge.setText(product.getSubCategory());
        
        // Set prices
        detailDiscountPrice.setText(product.getDiscountPrice());
        detailOriginalPrice.setText(product.getPrice());
        detailOriginalPrice.setPaintFlags(detailOriginalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        
        // Set product info
        detailBoxNumber.setText("Box Number: #" + product.getBoxNumber());
        detailCode.setText("Code: " + product.getCode());
        detailLocation.setText(String.format("Location: Shelf %d, Row %d", 
            product.getShelfLocation(), product.getRowLocation()));
        detailDescription.setText(product.getDescription());
        
        // Setup thumbnails
        setupThumbnails();
        
        // Setup size selection
        setupSizeSelection();
        
        // Setup available sizes chips
        setupAvailableSizes();
    }
    
    private void setupThumbnails() {
        thumbnailContainer.removeAllViews();
        
        if (product.getImages() != null) {
            for (int i = 0; i < product.getImages().size(); i++) {
                final int index = i;
                TextView thumbnail = new TextView(this);
                
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    (int) (80 * getResources().getDisplayMetrics().density),
                    (int) (80 * getResources().getDisplayMetrics().density)
                );
                params.setMargins(0, 0, (int) (12 * getResources().getDisplayMetrics().density), 0);
                thumbnail.setLayoutParams(params);
                
                thumbnail.setText(product.getImages().get(i));
                thumbnail.setTextSize(36);
                thumbnail.setGravity(android.view.Gravity.CENTER);
                
                if (index == 0) {
                    setGradientBackground(thumbnail, product.getColor());
                    thumbnail.setBackground(getResources().getDrawable(R.drawable.orange_badge_bg));
                } else {
                    thumbnail.setBackgroundColor(Color.parseColor("#F3F4F6"));
                }
                
                thumbnail.setOnClickListener(v -> {
                    productDetailEmoji.setText(product.getImages().get(index));
                });
                
                thumbnailContainer.addView(thumbnail);
            }
        }
    }
    
    private void setupSizeSelection() {
        sizeGrid.removeAllViews();
        
        if (product.getSizes() != null) {
            for (Integer size : product.getSizes()) {
                Button sizeButton = new Button(this);
                
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 0;
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
                params.setMargins(4, 4, 4, 4);
                sizeButton.setLayoutParams(params);
                
                sizeButton.setText(String.valueOf(size));
                sizeButton.setTextSize(14);
                sizeButton.setPadding(0, 24, 0, 24);
                sizeButton.setBackgroundResource(R.drawable.input_border);
                
                sizeButton.setOnClickListener(v -> {
                    selectedSize = size;
                    updateSizeButtons();
                    updateAddToCartButton();
                });
                
                sizeGrid.addView(sizeButton);
            }
        }
    }
    
    private void setupAvailableSizes() {
        availableSizesChipGroup.removeAllViews();
        
        if (product.getSizes() != null) {
            for (Integer size : product.getSizes()) {
                Chip chip = new Chip(this);
                chip.setText(String.valueOf(size));
                chip.setClickable(false);
                chip.setCheckable(false);
                chip.setChipBackgroundColorResource(R.color.white);
                chip.setChipStrokeColorResource(R.color.gray_200);
                chip.setChipStrokeWidth(2);
                availableSizesChipGroup.addView(chip);
            }
        }
    }
    
    private void updateSizeButtons() {
        for (int i = 0; i < sizeGrid.getChildCount(); i++) {
            Button btn = (Button) sizeGrid.getChildAt(i);
            int buttonSize = Integer.parseInt(btn.getText().toString());
            
            if (buttonSize == selectedSize) {
                btn.setBackgroundResource(R.drawable.orange_badge_bg);
                btn.setTextColor(Color.parseColor("#EA580C"));
            } else {
                btn.setBackgroundResource(R.drawable.input_border);
                btn.setTextColor(Color.parseColor("#111827"));
            }
        }
    }
    
    private void updateAddToCartButton() {
        if (selectedSize != -1) {
            addToCartButton.setEnabled(true);
            addToCartButton.setBackgroundResource(R.drawable.orange_button_bg);
            addToCartButton.setTextColor(Color.WHITE);
            addToCartButton.setText("Add to Cart - Size " + selectedSize);
        } else {
            addToCartButton.setEnabled(false);
            addToCartButton.setBackgroundResource(R.drawable.gray_button_bg);
            addToCartButton.setTextColor(Color.parseColor("#6B7280"));
            addToCartButton.setText(R.string.select_a_size);
        }
    }
    
    private void setupListeners() {
        closeButton.setOnClickListener(v -> finish());
        
        addToCartButton.setOnClickListener(v -> {
            if (selectedSize != -1) {
                CartManager.getInstance().addToCart(product, selectedSize);
                Toast.makeText(this, "Added to cart!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        
        // Click on image area to open gallery
        productDetailEmoji.setOnClickListener(v -> {
            // TODO: Open image gallery activity
        });
    }
    
    private String getCategoryIcon(String category) {
        switch (category) {
            case "Male": return "👨";
            case "Female": return "👩";
            case "Kids": return "👶";
            default: return "👤";
        }
    }
    
    private void setGradientBackground(View view, String colorName) {
        GradientDrawable gradient = new GradientDrawable();
        gradient.setShape(GradientDrawable.RECTANGLE);
        
        int[] colors = getGradientColors(colorName);
        gradient.setColors(colors);
        gradient.setOrientation(GradientDrawable.Orientation.BR_TL);
        
        view.setBackground(gradient);
    }
    
    private int[] getGradientColors(String colorName) {
        switch (colorName) {
            case "blue_gradient":
                return new int[]{Color.parseColor("#BFDBFE"), Color.parseColor("#93C5FD"), Color.parseColor("#A5F3FC")};
            case "pink_gradient":
                return new int[]{Color.parseColor("#FBCFE8"), Color.parseColor("#F9A8D4"), Color.parseColor("#FECDD3")};
            case "yellow_gradient":
                return new int[]{Color.parseColor("#FEF08A"), Color.parseColor("#FDBA74"), Color.parseColor("#FEF08A")};
            case "green_gradient":
                return new int[]{Color.parseColor("#BBF7D0"), Color.parseColor("#86EFAC"), Color.parseColor("#A7F3D0")};
            case "purple_gradient":
                return new int[]{Color.parseColor("#E9D5FF"), Color.parseColor("#D8B4FE"), Color.parseColor("#FBCFE8")};
            case "red_gradient":
                return new int[]{Color.parseColor("#FECACA"), Color.parseColor("#FDBA74"), Color.parseColor("#FEF08A")};
            case "gray_gradient":
                return new int[]{Color.parseColor("#E5E7EB"), Color.parseColor("#D1D5DB"), Color.parseColor("#F3F4F6")};
            case "indigo_gradient":
                return new int[]{Color.parseColor("#C7D2FE"), Color.parseColor("#A5B4FC"), Color.parseColor("#93C5FD")};
            case "dark_gradient":
                return new int[]{Color.parseColor("#4B5563"), Color.parseColor("#374151"), Color.parseColor("#1F2937")};
            case "orange_gradient":
                return new int[]{Color.parseColor("#FCA5A5"), Color.parseColor("#FB923C"), Color.parseColor("#FBBF24")};
            default:
                return new int[]{Color.parseColor("#BFDBFE"), Color.parseColor("#93C5FD"), Color.parseColor("#A5F3FC")};
        }
    }
}
