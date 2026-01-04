package com.shoesstore.app;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;
import com.shoesstore.app.database.DatabaseHelper;
import com.shoesstore.app.models.Product;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddProductActivity extends AppCompatActivity {

    private EditText image1Input, image2Input, image3Input;
    private EditText productNameInput, subCategoryInput;
    private Spinner mainCategorySpinner;
    private EditText originalPriceInput, discountPriceInput, minPriceInput;
    private EditText shelfNumberInput, rowNumberInput;
    private EditText boxNumberInput, codeInput, descriptionInput;
    private GridLayout availableSizesGrid;
    private TextView selectedSizesText;
    private Button addProductButton, cancelProductButton;
    
    private List<Integer> selectedSizes = new ArrayList<>();
    private List<Button> sizeButtons = new ArrayList<>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        
        initializeViews();
        setupCategorySpinner();
        setupSizeGrid();
        setupListeners();
    }
    
    private void initializeViews() {
        TextView backButton = findViewById(R.id.addProductBackButton);
        backButton.setOnClickListener(v -> finish());
        
        image1Input = findViewById(R.id.image1Input);
        image2Input = findViewById(R.id.image2Input);
        image3Input = findViewById(R.id.image3Input);
        productNameInput = findViewById(R.id.productNameInput);
        subCategoryInput = findViewById(R.id.subCategoryInput);
        mainCategorySpinner = findViewById(R.id.mainCategorySpinner);
        originalPriceInput = findViewById(R.id.originalPriceInput);
        discountPriceInput = findViewById(R.id.discountPriceInput);
        minPriceInput = findViewById(R.id.minPriceInput);
        shelfNumberInput = findViewById(R.id.shelfNumberInput);
        rowNumberInput = findViewById(R.id.rowNumberInput);
        boxNumberInput = findViewById(R.id.boxNumberInput);
        codeInput = findViewById(R.id.codeInput);
        descriptionInput = findViewById(R.id.descriptionInput);
        availableSizesGrid = findViewById(R.id.availableSizesGrid);
        selectedSizesText = findViewById(R.id.selectedSizesText);
        addProductButton = findViewById(R.id.addProductButton);
        cancelProductButton = findViewById(R.id.cancelProductButton);
    }
    
    private void setupCategorySpinner() {
        String[] categories = {"Select Category", "Male", "Female", "Kids"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, 
            android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mainCategorySpinner.setAdapter(adapter);
    }
    
    private void setupSizeGrid() {
        availableSizesGrid.setColumnCount(13);
        availableSizesGrid.setRowCount(1);
        
        for (int size = 1; size <= 13; size++) {
            Button sizeButton = createSizeButton(size);
            availableSizesGrid.addView(sizeButton);
            sizeButtons.add(sizeButton);
        }
    }
    
    private Button createSizeButton(int size) {
        Button button = new Button(this);
        
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = 0;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
        params.setMargins(4, 4, 4, 4);
        button.setLayoutParams(params);
        
        button.setText(String.valueOf(size));
        button.setTextSize(14);
        button.setPadding(0, 16, 0, 16);
        button.setBackgroundResource(R.drawable.input_border);
        
        button.setOnClickListener(v -> toggleSize(size, button));
        
        return button;
    }
    
    private void toggleSize(int size, Button button) {
        if (selectedSizes.contains(size)) {
            selectedSizes.remove(Integer.valueOf(size));
            button.setBackgroundResource(R.drawable.input_border);
        } else {
            selectedSizes.add(size);
            button.setBackgroundResource(R.drawable.orange_badge_bg);
        }
        
        updateSelectedSizesText();
    }
    
    private void updateSelectedSizesText() {
        if (selectedSizes.isEmpty()) {
            selectedSizesText.setText("Selected: None");
        } else {
            StringBuilder sb = new StringBuilder("Selected: ");
            for (int i = 0; i < selectedSizes.size(); i++) {
                sb.append(selectedSizes.get(i));
                if (i < selectedSizes.size() - 1) {
                    sb.append(", ");
                }
            }
            selectedSizesText.setText(sb.toString());
        }
    }
    
    private void setupListeners() {
        addProductButton.setOnClickListener(v -> saveProduct());
        cancelProductButton.setOnClickListener(v -> finish());
    }
    
    private void saveProduct() {
        // Validate inputs
        if (!validateInputs()) {
            return;
        }
        
        // Create product
        Product product = new Product();
        
        // Set images
        List<String> images = Arrays.asList(
            image1Input.getText().toString().trim(),
            image2Input.getText().toString().trim(),
            image3Input.getText().toString().trim()
        );
        product.setImages(images);
        product.setImage(images.get(0));
        
        // Set basic info
        product.setName(productNameInput.getText().toString().trim());
        product.setCategory(mainCategorySpinner.getSelectedItem().toString());
        product.setSubCategory(subCategoryInput.getText().toString().trim());
        
        // Set prices
        String originalPrice = originalPriceInput.getText().toString().trim();
        String discountPrice = discountPriceInput.getText().toString().trim();
        String minPrice = minPriceInput.getText().toString().trim();
        
        product.setPrice("₹" + formatPrice(Integer.parseInt(originalPrice)));
        product.setDiscountPrice("₹" + formatPrice(Integer.parseInt(discountPrice)));
        product.setMinPrice("₹" + formatPrice(Integer.parseInt(minPrice)));
        
        // Set sizes
        product.setSizes(selectedSizes);
        
        // Set location
        product.setShelfLocation(Integer.parseInt(shelfNumberInput.getText().toString().trim()));
        product.setRowLocation(Integer.parseInt(rowNumberInput.getText().toString().trim()));
        
        // Set box number and code
        product.setBoxNumber(boxNumberInput.getText().toString().trim());
        product.setCode(codeInput.getText().toString().trim());
        
        // Set description
        product.setDescription(descriptionInput.getText().toString().trim());
        
        // Set default color gradient
        product.setColor("blue_gradient");
        
        // Save to database
        DatabaseHelper database = DatabaseHelper.getInstance(this);
        long id = database.insertProduct(product);
        
        if (id > 0) {
            Toast.makeText(this, "Product added successfully!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to add product", Toast.LENGTH_SHORT).show();
        }
    }
    
    private boolean validateInputs() {
        if (image1Input.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please add at least first image", Toast.LENGTH_SHORT).show();
            return false;
        }
        
        if (productNameInput.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter product name", Toast.LENGTH_SHORT).show();
            return false;
        }
        
        if (mainCategorySpinner.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select a category", Toast.LENGTH_SHORT).show();
            return false;
        }
        
        if (subCategoryInput.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter sub category", Toast.LENGTH_SHORT).show();
            return false;
        }
        
        if (originalPriceInput.getText().toString().trim().isEmpty() ||
            discountPriceInput.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter all prices", Toast.LENGTH_SHORT).show();
            return false;
        }
        
        if (selectedSizes.isEmpty()) {
            Toast.makeText(this, "Please select at least one size", Toast.LENGTH_SHORT).show();
            return false;
        }
        
        if (shelfNumberInput.getText().toString().trim().isEmpty() ||
            rowNumberInput.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter location", Toast.LENGTH_SHORT).show();
            return false;
        }
        
        return true;
    }
    
    private String formatPrice(int price) {
        return String.format("%,d", price);
    }
}
