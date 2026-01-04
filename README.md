# Shoe Store Management App - Android

A complete offline Android shoe store management application built with Java, replicating the exact UI and functionality from the React implementation.

## Features

### Customer Features
- **Product Browsing**: Grid view of all products with decorative gradient backgrounds
- **Search**: Real-time search by product name, category, sub-category, size, location, box number, and code
- **Filters**: Filter by main category (Male/Female/Kids), sub-category, and sizes
- **Product Details**: Full product information with size selection
- **Shopping Cart**: Add products with specific sizes to cart
- **Checkout**: Process sales with minimum price validation

### Admin Features
- **Add Products**: Add new products with images (emojis), pricing, sizes, and location
- **Edit Products**: Modify existing product information
- **Offline Storage**: All data stored locally using Room database
- **Inventory Management**: Track products by shelf and row location

## Technical Architecture

### Core Technologies
- **Language**: Java
- **UI**: XML Layouts with Material Design Components
- **Database**: SQLite (Native Android SQLiteOpenHelper)
- **Architecture**: Activity-based with RecyclerView adapters

### Key Components

#### Data Layer
- `Product.java`: Product model (POJO)
- `CartItem.java`: Shopping cart item model
- `DatabaseHelper.java`: SQLite database helper with CRUD operations
- Uses Gson for List serialization/deserialization

#### UI Layer
- `MainActivity.java`: Main product grid with search and filters
- `ProductDetailActivity.java`: Product details with size selection
- `CheckoutActivity.java`: Checkout with payment validation
- `AddProductActivity.java`: Add new products
- `EditProductsActivity.java`: Edit existing products

#### Adapters
- `ProductAdapter.java`: RecyclerView adapter for product grid
- `CartAdapter.java`: RecyclerView adapter for cart items
- `CheckoutAdapter.java`: RecyclerView adapter for checkout items

#### Utilities
- `CartManager.java`: Singleton for cart management
- `DataSeeder.java`: Initial database seeding with sample products

### UI Features

#### Exact UI Match
- **Gradient Backgrounds**: Each product has a unique gradient background
- **Decorative Elements**: Yellow lights on sides, golden ornaments, bottom light string
- **Category Badges**: Icon + text badges for product categories
- **Color-coded States**: 
  - Green for valid inputs/prices
  - Red for invalid inputs
  - Orange for primary actions
  - Blue for information

#### Responsive Design
- 2-column grid layout for products
- Scrollable drawers for menu, filters, and cart
- Dynamic size selection grids
- Badge notifications for cart count and active filters

## Project Structure

```
ShoeStoreApp/
├── app/
│   ├── build.gradle
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml
│           ├── java/com/shoesstore/app/
│           │   ├── MainActivity.java
│           │   ├── ProductDetailActivity.java
│           │   ├── CheckoutActivity.java
│           │   ├── AddProductActivity.java
│           │   ├── EditProductsActivity.java
│           │   ├── adapters/
│           │   │   ├── ProductAdapter.java
│           │   │   ├── CartAdapter.java
│           │   │   └── CheckoutAdapter.java
│           │   ├── models/
│           │   │   ├── Product.java
│           │   │   ├── CartItem.java
│           │   │   └── Category.java
│           │   ├── database/
│           │   │   └── DatabaseHelper.java
│           │   └── utils/
│           │       ├── CartManager.java
│           │       └── DataSeeder.java
│           └── res/
│               ├── layout/
│               │   ├── activity_main.xml
│               │   ├── activity_product_detail.xml
│               │   ├── activity_checkout.xml
│               │   ├── activity_add_product.xml
│               │   ├── drawer_menu.xml
│               │   ├── drawer_filter.xml
│               │   ├── drawer_cart.xml
│               │   ├── item_product.xml
│               │   ├── item_cart.xml
│               │   └── item_checkout.xml
│               ├── drawable/
│               ├── values/
│               │   ├── colors.xml
│               │   ├── strings.xml
│               │   └── themes.xml
│               └── mipmap/
├── build.gradle
├── settings.gradle
└── gradle.properties
```

## Setup Instructions

1. **Open in Android Studio**:
   - File → Open → Select `ShoeStoreApp` folder

2. **Sync Gradle**:
   - Android Studio will automatically sync Gradle files
   - Wait for dependencies to download

3. **Run the App**:
   - Connect an Android device or start an emulator
   - Click Run (green play button) or press Shift+F10
   - Select your device/emulator

## Minimum Requirements

- **Min SDK**: Android 7.0 (API 24)
- **Target SDK**: Android 14 (API 34)
- **Build Tools**: Gradle 8.2.0
- **Java Version**: Java 8

## Dependencies

- AndroidX AppCompat: 1.6.1
- Material Components: 1.11.0
- RecyclerView: 1.3.2
- Gson: 2.10.1 (for JSON serialization)

## Database Schema

### Products Table (SQLite)
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
    images TEXT,              -- JSON array
    color TEXT,
    sizes TEXT,               -- JSON array
    shelfLocation INTEGER,
    rowLocation INTEGER,
    boxNumber TEXT,
    code TEXT,
    description TEXT
);
```

### Database Operations
- `insertProduct(Product)`: Add new product
- `getAllProducts()`: Get all products
- `getProductById(id)`: Get single product
- `updateProduct(Product)`: Update existing product
- `deleteProduct(id)`: Delete product
- `deleteAllProducts()`: Clear all products
- `getProductCount()`: Get total product count
- `searchProducts(query)`: Search products by multiple fields

## Key Features Implementation

### Search Functionality
Searches across:
- Product name (any word)
- Main category
- Sub-category
- Box number
- Product code
- Price
- Location (shelf/row)
- Available sizes

### Filter System
- **Main Category**: Male, Female, Kids
- **Sub Category**: Dynamically filtered based on main category
- **Size**: 1-13 with multi-select
- Filter badge shows active filter count
- Filters combine with AND logic

### Cart Management
- Singleton pattern for global cart access
- Supports same product with different sizes as separate items
- Real-time total calculation
- Minimum price calculation from cheapest item

### Checkout Validation
- Input field changes color based on validity:
  - Gray: Empty/default
  - Green: Valid amount (≥ minimum)
  - Red: Invalid amount (< minimum)
- "Sold" button enabled only for valid amounts
- Optional minimum price display with checkbox

## Sample Data

The app comes pre-loaded with **36 sample products** including:

**Male Shoes (15 products)**:
- Sports Shoes: Nike Air Max, Under Armour, Converse, Puma
- Running Shoes: Reebok, Adidas Ultraboost
- Casual Slippers: Crocs, Birkenstock, Adidas Adilette
- Formal Shoes: Oxford, Clarks Derby, Bata Executive
- Boots: Timberland Pro, CAT Footwear, Red Tape

**Female Shoes (12 products)**:
- Running Shoes: Adidas Ultraboost, Asics Gel, Nike Pegasus
- Casual Shoes: New Balance 574, Bata Comfit, Skechers
- Sandals: Havaianas, Wedge Sandals, Crocs Bistro
- Heels: Stiletto, Block Heels, Kitten Heels

**Kids Shoes (9 products)**:
- Sneakers: Puma Kids, Vans, Nike Revolution
- Sandals: Disney Characters, Crocs Kids, Relaxo
- School Shoes: Bata School, Skechers Light-Up, Action Kids

Each product has:
- 3 emoji images
- Unique gradient background
- Multiple available sizes
- Shelf and row location
- Box number and code
- Detailed description

## Future Enhancements

- Image gallery fullscreen view
- Edit product functionality
- Backup/Restore database
- Sales history tracking
- Barcode scanning
- Cloud sync
- Multi-user support
- Receipt printing

## Notes

- All data is stored locally - no internet required
- Cart persists during app session (cleared on checkout)
- Database is seeded automatically on first launch
- Emojis are used instead of actual images for offline compatibility
- Gradient colors are predefined in code for consistent styling

## License

This project is provided as-is for educational and commercial use.
# ShoeStoreApp
