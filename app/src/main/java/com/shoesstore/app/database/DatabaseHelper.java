package com.shoesstore.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.shoesstore.app.models.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    
    private static final String DATABASE_NAME = "shoe_store.db";
    private static final int DATABASE_VERSION = 1;
    
    // Table name
    private static final String TABLE_PRODUCTS = "products";
    
    // Column names
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_CATEGORY = "category";
    private static final String COL_SUB_CATEGORY = "subCategory";
    private static final String COL_PRICE = "price";
    private static final String COL_DISCOUNT_PRICE = "discountPrice";
    private static final String COL_MIN_PRICE = "minPrice";
    private static final String COL_IMAGE = "image";
    private static final String COL_IMAGES = "images";
    private static final String COL_COLOR = "color";
    private static final String COL_SIZES = "sizes";
    private static final String COL_SHELF_LOCATION = "shelfLocation";
    private static final String COL_ROW_LOCATION = "rowLocation";
    private static final String COL_BOX_NUMBER = "boxNumber";
    private static final String COL_CODE = "code";
    private static final String COL_DESCRIPTION = "description";
    
    private Gson gson;
    private static DatabaseHelper instance;
    
    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }
    
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        gson = new Gson();
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAME + " TEXT NOT NULL, "
                + COL_CATEGORY + " TEXT NOT NULL, "
                + COL_SUB_CATEGORY + " TEXT NOT NULL, "
                + COL_PRICE + " TEXT NOT NULL, "
                + COL_DISCOUNT_PRICE + " TEXT NOT NULL, "
                + COL_MIN_PRICE + " TEXT, "
                + COL_IMAGE + " TEXT NOT NULL, "
                + COL_IMAGES + " TEXT, "
                + COL_COLOR + " TEXT, "
                + COL_SIZES + " TEXT, "
                + COL_SHELF_LOCATION + " INTEGER, "
                + COL_ROW_LOCATION + " INTEGER, "
                + COL_BOX_NUMBER + " TEXT, "
                + COL_CODE + " TEXT, "
                + COL_DESCRIPTION + " TEXT"
                + ")";
        
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        onCreate(db);
    }
    
    // Insert product
    public long insertProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = getContentValues(product);
        
        long id = db.insert(TABLE_PRODUCTS, null, values);
        db.close();
        return id;
    }
    
    // Get all products
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PRODUCTS + " ORDER BY " + COL_ID + " DESC";
        
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        if (cursor.moveToFirst()) {
            do {
                Product product = getProductFromCursor(cursor);
                productList.add(product);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        db.close();
        return productList;
    }
    
    // Get product by ID
    public Product getProductById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PRODUCTS, null, COL_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null);
        
        Product product = null;
        if (cursor != null && cursor.moveToFirst()) {
            product = getProductFromCursor(cursor);
            cursor.close();
        }
        
        db.close();
        return product;
    }
    
    // Update product
    public int updateProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = getContentValues(product);
        
        int rowsAffected = db.update(TABLE_PRODUCTS, values, COL_ID + "=?",
                new String[]{String.valueOf(product.getId())});
        
        db.close();
        return rowsAffected;
    }
    
    // Delete product
    public void deleteProduct(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTS, COL_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }
    
    // Delete all products
    public void deleteAllProducts() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTS, null, null);
        db.close();
    }
    
    // Get product count
    public int getProductCount() {
        String countQuery = "SELECT COUNT(*) FROM " + TABLE_PRODUCTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        
        cursor.close();
        db.close();
        return count;
    }
    
    // Search products
    public List<Product> searchProducts(String query) {
        List<Product> productList = new ArrayList<>();
        String searchQuery = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE "
                + COL_NAME + " LIKE ? OR "
                + COL_CATEGORY + " LIKE ? OR "
                + COL_SUB_CATEGORY + " LIKE ? OR "
                + COL_BOX_NUMBER + " LIKE ? OR "
                + COL_CODE + " LIKE ?";
        
        String searchPattern = "%" + query + "%";
        String[] selectionArgs = {searchPattern, searchPattern, searchPattern, searchPattern, searchPattern};
        
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(searchQuery, selectionArgs);
        
        if (cursor.moveToFirst()) {
            do {
                Product product = getProductFromCursor(cursor);
                productList.add(product);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        db.close();
        return productList;
    }
    
    // Helper method to convert Product to ContentValues
    private ContentValues getContentValues(Product product) {
        ContentValues values = new ContentValues();
        values.put(COL_NAME, product.getName());
        values.put(COL_CATEGORY, product.getCategory());
        values.put(COL_SUB_CATEGORY, product.getSubCategory());
        values.put(COL_PRICE, product.getPrice());
        values.put(COL_DISCOUNT_PRICE, product.getDiscountPrice());
        values.put(COL_MIN_PRICE, product.getMinPrice());
        values.put(COL_IMAGE, product.getImage());
        values.put(COL_IMAGES, gson.toJson(product.getImages()));
        values.put(COL_COLOR, product.getColor());
        values.put(COL_SIZES, gson.toJson(product.getSizes()));
        values.put(COL_SHELF_LOCATION, product.getShelfLocation());
        values.put(COL_ROW_LOCATION, product.getRowLocation());
        values.put(COL_BOX_NUMBER, product.getBoxNumber());
        values.put(COL_CODE, product.getCode());
        values.put(COL_DESCRIPTION, product.getDescription());
        
        return values;
    }
    
    // Helper method to convert Cursor to Product
    private Product getProductFromCursor(Cursor cursor) {
        Product product = new Product();
        
        product.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
        product.setName(cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME)));
        product.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(COL_CATEGORY)));
        product.setSubCategory(cursor.getString(cursor.getColumnIndexOrThrow(COL_SUB_CATEGORY)));
        product.setPrice(cursor.getString(cursor.getColumnIndexOrThrow(COL_PRICE)));
        product.setDiscountPrice(cursor.getString(cursor.getColumnIndexOrThrow(COL_DISCOUNT_PRICE)));
        product.setMinPrice(cursor.getString(cursor.getColumnIndexOrThrow(COL_MIN_PRICE)));
        product.setImage(cursor.getString(cursor.getColumnIndexOrThrow(COL_IMAGE)));
        
        // Convert JSON strings back to Lists
        String imagesJson = cursor.getString(cursor.getColumnIndexOrThrow(COL_IMAGES));
        Type imagesType = new TypeToken<List<String>>(){}.getType();
        product.setImages(gson.fromJson(imagesJson, imagesType));
        
        product.setColor(cursor.getString(cursor.getColumnIndexOrThrow(COL_COLOR)));
        
        String sizesJson = cursor.getString(cursor.getColumnIndexOrThrow(COL_SIZES));
        Type sizesType = new TypeToken<List<Integer>>(){}.getType();
        product.setSizes(gson.fromJson(sizesJson, sizesType));
        
        product.setShelfLocation(cursor.getInt(cursor.getColumnIndexOrThrow(COL_SHELF_LOCATION)));
        product.setRowLocation(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ROW_LOCATION)));
        product.setBoxNumber(cursor.getString(cursor.getColumnIndexOrThrow(COL_BOX_NUMBER)));
        product.setCode(cursor.getString(cursor.getColumnIndexOrThrow(COL_CODE)));
        product.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(COL_DESCRIPTION)));
        
        return product;
    }
}
