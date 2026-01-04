# SQLite Database Guide

## Overview

This app uses **native Android SQLite** database through `SQLiteOpenHelper` for all data persistence. No external dependencies like Room are required.

## Database Structure

### Database Name
`shoe_store.db`

### Database Version
`1`

### Tables

#### Products Table
```sql
CREATE TABLE products (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    category TEXT NOT NULL,
    subCategory TEXT NOT NULL,
    price TEXT NOT NULL,
    discountPrice TEXT NOT NULL,
    minPrice TEXT,
    image TEXT NOT NULL,
    images TEXT,              -- JSON array of emojis
    color TEXT,               -- Gradient color name
    sizes TEXT,               -- JSON array of integers
    shelfLocation INTEGER,
    rowLocation INTEGER,
    boxNumber TEXT,
    code TEXT,
    description TEXT
);
```

## DatabaseHelper Class

### Singleton Pattern
```java
DatabaseHelper db = DatabaseHelper.getInstance(context);
```

Only one instance is created and shared throughout the app.

### Core Methods

#### Insert Product
```java
Product product = new Product();
// Set product properties
long id = db.insertProduct(product);
```

#### Get All Products
```java
List<Product> products = db.getAllProducts();
```

#### Get Product by ID
```java
Product product = db.getProductById(1);
```

#### Update Product
```java
product.setName("Updated Name");
int rowsAffected = db.updateProduct(product);
```

#### Delete Product
```java
db.deleteProduct(1);
```

#### Delete All Products
```java
db.deleteAllProducts();
```

#### Get Product Count
```java
int count = db.getProductCount();
```

#### Search Products
```java
List<Product> results = db.searchProducts("nike");
```
Searches in: name, category, subCategory, boxNumber, code

## JSON Serialization

### Why Gson?

SQLite doesn't support arrays/lists natively. We use **Gson** to convert Lists to JSON strings:

#### Sizes (List<Integer>)
```java
// Storing
List<Integer> sizes = Arrays.asList(8, 9, 10, 11);
String sizesJson = gson.toJson(sizes);  // "[8,9,10,11]"

// Retrieving
Type type = new TypeToken<List<Integer>>(){}.getType();
List<Integer> sizes = gson.fromJson(sizesJson, type);
```

#### Images (List<String>)
```java
// Storing
List<String> images = Arrays.asList("👟", "👟", "👟");
String imagesJson = gson.toJson(images);  // "[\"👟\",\"👟\",\"👟\"]"

// Retrieving
Type type = new TypeToken<List<String>>(){}.getType();
List<String> images = gson.fromJson(imagesJson, type);
```

## ContentValues

Used to insert/update data:

```java
ContentValues values = new ContentValues();
values.put("name", "Nike Air Max");
values.put("category", "Male");
values.put("price", "₹8,995");
values.put("sizes", gson.toJson(Arrays.asList(8, 9, 10)));

long id = db.insert("products", null, values);
```

## Cursor

Used to read data from database:

```java
Cursor cursor = db.rawQuery("SELECT * FROM products", null);

if (cursor.moveToFirst()) {
    do {
        int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
        String sizesJson = cursor.getString(cursor.getColumnIndexOrThrow("sizes"));
        
        // Convert JSON back to List
        List<Integer> sizes = gson.fromJson(sizesJson, 
            new TypeToken<List<Integer>>(){}.getType());
    } while (cursor.moveToNext());
}
cursor.close();
```

## Database Seeding

On first app launch, `DataSeeder.seedDatabase()` is called:

1. Checks if database is empty (`getProductCount() == 0`)
2. Creates 24 sample products
3. Inserts them into database
4. Generates random box numbers
5. Sets default descriptions

```java
// In MainActivity.onCreate()
DatabaseHelper db = DatabaseHelper.getInstance(this);
DataSeeder.seedDatabase(this);
```

## Sample Data Structure

```java
Product product = new Product();
product.setName("Nike Air Max");
product.setCategory("Male");
product.setSubCategory("Sports Shoes");
product.setPrice("₹8,995");
product.setDiscountPrice("₹7,499");
product.setMinPrice("₹6,000");
product.setImage("👟");
product.setImages(Arrays.asList("👟", "👟", "👟"));
product.setColor("blue_gradient");
product.setSizes(Arrays.asList(8, 9, 10, 11));
product.setShelfLocation(1);
product.setRowLocation(4);
product.setBoxNumber("543210");
product.setCode("9832");
product.setDescription("Premium quality shoes...");
```

## Database Queries

### Raw SQL Query
```java
SQLiteDatabase db = this.getReadableDatabase();
String query = "SELECT * FROM products WHERE category = ?";
Cursor cursor = db.rawQuery(query, new String[]{"Male"});
```

### Using Query Method
```java
SQLiteDatabase db = this.getReadableDatabase();
Cursor cursor = db.query(
    "products",                     // Table name
    null,                          // Columns (null = all)
    "category = ?",                // WHERE clause
    new String[]{"Male"},          // WHERE args
    null,                          // GROUP BY
    null,                          // HAVING
    "id DESC"                      // ORDER BY
);
```

## Database Inspection

### Via ADB Shell
```bash
adb shell
cd /data/data/com.shoesstore.app/databases/
sqlite3 shoe_store.db

# List tables
.tables

# Show schema
.schema products

# Query data
SELECT * FROM products;
SELECT name, category, price FROM products WHERE category='Male';
SELECT COUNT(*) FROM products;

# Exit
.quit
```

### Via Android Studio
1. Run app on emulator/device
2. **View → Tool Windows → App Inspection**
3. Select **Database Inspector**
4. Expand **shoe_store.db**
5. Click **products** table
6. View/edit data in real-time

### Pull Database File
```bash
adb pull /data/data/com.shoesstore.app/databases/shoe_store.db
```

Open with SQLite browser on your computer.

## Database Backup/Restore

### Backup
```java
File currentDB = context.getDatabasePath("shoe_store.db");
File backupDB = new File(context.getExternalFilesDir(null), "backup_shoe_store.db");

FileInputStream in = new FileInputStream(currentDB);
FileOutputStream out = new FileOutputStream(backupDB);

byte[] buffer = new byte[1024];
int length;
while ((length = in.read(buffer)) > 0) {
    out.write(buffer, 0, length);
}

out.flush();
out.close();
in.close();
```

### Restore
```java
File backupDB = new File(context.getExternalFilesDir(null), "backup_shoe_store.db");
File currentDB = context.getDatabasePath("shoe_store.db");

FileInputStream in = new FileInputStream(backupDB);
FileOutputStream out = new FileOutputStream(currentDB);

byte[] buffer = new byte[1024];
int length;
while ((length = in.read(buffer)) > 0) {
    out.write(buffer, 0, length);
}

out.flush();
out.close();
in.close();
```

## Performance Tips

1. **Use Transactions** for bulk operations:
```java
SQLiteDatabase db = this.getWritableDatabase();
db.beginTransaction();
try {
    // Insert multiple products
    for (Product p : products) {
        db.insert("products", null, getContentValues(p));
    }
    db.setTransactionSuccessful();
} finally {
    db.endTransaction();
}
```

2. **Always close Cursor**:
```java
Cursor cursor = db.query(...);
try {
    // Use cursor
} finally {
    cursor.close();
}
```

3. **Use Indexes** for frequently searched columns:
```sql
CREATE INDEX idx_category ON products(category);
CREATE INDEX idx_subcategory ON products(subCategory);
```

4. **Avoid N+1 queries** - fetch all data in one query

## Common Issues

### Database Locked
**Cause**: Multiple threads accessing database simultaneously

**Solution**: DatabaseHelper uses singleton pattern

### Cursor Not Closed
**Cause**: Forgetting to close cursor

**Solution**: Always use try-finally or try-with-resources

### JSON Parse Error
**Cause**: Invalid JSON in database

**Solution**: Validate data before insertion

### Migration Issues
**Cause**: Database schema changed but version not updated

**Solution**: Increment `DATABASE_VERSION` and update `onUpgrade()`

## Testing Database

```java
// Test insert
DatabaseHelper db = DatabaseHelper.getInstance(context);
Product testProduct = new Product();
testProduct.setName("Test Product");
testProduct.setCategory("Male");
long id = db.insertProduct(testProduct);
assertTrue(id > 0);

// Test retrieve
Product retrieved = db.getProductById((int)id);
assertEquals("Test Product", retrieved.getName());

// Test update
retrieved.setName("Updated Product");
int rows = db.updateProduct(retrieved);
assertEquals(1, rows);

// Test delete
db.deleteProduct((int)id);
Product deleted = db.getProductById((int)id);
assertNull(deleted);
```

## Summary

✅ **Native SQLite** - No external database libraries
✅ **Singleton Pattern** - Single database instance
✅ **Gson for Lists** - Efficient JSON serialization
✅ **CRUD Operations** - All database operations covered
✅ **Auto-seeding** - 24 products on first launch
✅ **Type-safe** - Proper data type handling
✅ **Lightweight** - Minimal dependencies

The database is production-ready and handles all product management needs for the offline shoe store app!
