# UI Screenshot Guide - Product Detail Screen

## 📱 Product Detail Screen Layout (Exact UI)

```
┌─────────────────────────────────────────┐
│                                    [✕]  │ ← Close Button (top-right, white circle)
│   👨 Male                               │ ← Category Badge (top-left, semi-transparent white)
│                                         │
│                                         │
│              [Golden Ornament]          │ ← Top decorative ornaments
│                                         │
│                                         │
│                  👟                     │ ← Large Product Emoji (180sp)
│               (Emoji)                   │   On gradient background
│                                         │   (Blue/Pink/Yellow/etc based on product)
│                                         │
│              [Golden Ornament]          │
│                                         │
└─────────────────────────────────────────┘
│  [Thumbnail] [Thumbnail] [Thumbnail]   │ ← Horizontal scroll thumbnails (80x80dp)
│     👟          👟          👟         │   Active: gradient bg + orange border
│   (Active)   (Inactive)  (Inactive)    │   Click to change main image
└─────────────────────────────────────────┘
│                                         │
│  Nike Air Max | Running Shoes | Nike   │ ← Full Product Name (20sp, bold)
│  ┌─────────┐ ┌──────────────┐         │
│  │  Male   │ │ Sports Shoes │         │ ← Category Badges
│  │ Indigo  │ │   Orange     │         │   (Indigo for main, Orange for sub)
│  └─────────┘ └──────────────┘         │
│                                         │
│  ₹7,499         ₹8,995                 │ ← Discount Price (28sp, green, bold)
│  (Green)     (Strikethrough, gray)     │   Original Price (strikethrough)
│                                         │
│  Box Number: #543210                   │ ← Product Info (12sp, gray)
│  Code: 9832                            │
│  Location: Shelf 1, Row 4              │
│                                         │
│  Description                            │ ← Section Header (16sp, bold)
│  Premium quality sports shoes by Nike. │ ← Description text (12sp)
│  Designed for male customers with      │   Multiple lines, gray color
│  focus on comfort, durability, and...  │
│                                         │
│  ┌─────────────────────────────────┐  │
│  │  Select Size                     │  │ ← Size Selection Section
│  │                                  │  │   (Gray-50 background, rounded)
│  │  ┌───┐ ┌───┐ ┌───┐ ┌───┐ ┌───┐ │  │
│  │  │ 8 │ │ 9 │ │10 │ │11 │ │12 │ │  │ ← Size Grid (5 columns)
│  │  └───┘ └───┘ └───┘ └───┘ └───┘ │  │   Selected: Orange border + bg-50
│  │  (Selected)                      │  │   Unselected: Gray border
│  │                                  │  │
│  └─────────────────────────────────┘  │
│                                         │
│  ┌─────────────────────────────────┐  │
│  │  Available Sizes                 │  │ ← Available Sizes Display
│  │                                  │  │   (Gray-50 background, rounded)
│  │  ┌───┐ ┌───┐ ┌───┐ ┌───┐        │  │
│  │  │ 8 │ │ 9 │ │10 │ │11 │        │  │ ← Size Chips (read-only)
│  │  └───┘ └───┘ └───┘ └───┘        │  │   White bg, gray border
│  │                                  │  │
│  └─────────────────────────────────┘  │
│                                         │
│  ┌─────────────────────────────────┐  │
│  │   Add to Cart - Size 9           │  │ ← Add to Cart Button
│  │         (Orange Button)           │  │   Enabled: Orange bg, white text
│  └─────────────────────────────────┘  │   Disabled: Gray bg, gray text
│                                         │   Shows "Select a Size" when disabled
└─────────────────────────────────────────┘
```

## 🎨 Color Specifications

### Gradient Backgrounds (Product Image Area)
Each product has a unique gradient:

| Product Category | Gradient Colors |
|-----------------|-----------------|
| Nike Air Max | Blue → Light Blue → Cyan |
| Adidas Shoes | Pink → Light Pink → Rose |
| Puma Sneakers | Yellow → Orange → Yellow |
| Crocs | Green → Light Green → Emerald |
| Formal Shoes | Dark Gray → Gray → Dark Gray |
| Heels | Red → Pink → Rose |
| Boots | Brown → Dark Brown → Amber |

### Text Colors
- **Product Name**: #111827 (Gray-900, Bold)
- **Category Badges**: 
  - Main: Indigo-100 bg, Indigo-700 text
  - Sub: Orange-100 bg, Orange-600 text
- **Discount Price**: #16A34A (Green-600, Bold)
- **Original Price**: #9CA3AF (Gray-400, Strikethrough)
- **Description**: #4B5563 (Gray-600)
- **Info Labels**: #6B7280 (Gray-500, 12sp)

### Button States
- **Add to Cart (Enabled)**: 
  - Background: #F97316 (Orange-500)
  - Text: #FFFFFF (White)
  - Text: "Add to Cart - Size X"
  
- **Add to Cart (Disabled)**:
  - Background: #D1D5DB (Gray-300)
  - Text: #6B7280 (Gray-500)
  - Text: "Select a Size"

### Size Selection
- **Selected Size**:
  - Border: 2dp, #F97316 (Orange-500)
  - Background: #FFF7ED (Orange-50)
  - Text: #EA580C (Orange-600)
  
- **Unselected Size**:
  - Border: 1dp, #D1D5DB (Gray-300)
  - Background: #FFFFFF (White)
  - Text: #111827 (Gray-900)

## 📏 Dimensions

| Element | Size | Notes |
|---------|------|-------|
| Product Emoji | 180sp | Large, centered |
| Thumbnail Emoji | 36sp | In 80x80dp container |
| Product Name | 20sp | Bold, 1-2 lines |
| Prices | 28sp / 16sp | Discount / Original |
| Category Badges | 12sp | Small, rounded pills |
| Size Buttons | 14sp | In 5-column grid |
| Description | 12sp | Multiple lines |
| Close Button | 56dp | Circle, white bg |
| Golden Ornaments | 32x32dp | Top corners |

## 🔄 Interactive Elements

### 1. Close Button (Top-Right)
- Circle: 56dp diameter
- Background: White
- Icon: ✕ (28sp)
- Click: Returns to main screen

### 2. Product Image Area
- Click: Opens fullscreen image gallery
- Gradient background based on product.color
- Golden ornaments at top corners

### 3. Thumbnail Navigation
- 3 thumbnails horizontally scrollable
- Active: Gradient bg + 2dp orange border
- Inactive: Gray-50 background
- Click: Updates main emoji display

### 4. Size Selection Grid
- 5 columns × N rows (based on available sizes)
- Toggle selection on click
- Only one size can be selected
- Updates "Add to Cart" button text

### 5. Add to Cart Button
- Disabled until size selected
- Click: Adds product with selected size to cart
- Shows toast: "Added to cart!"
- Closes activity and returns to main

## 📱 Sample Product Details

### Example 1: Nike Air Max
```
Name: Nike Air Max | Running Shoes | Nike
Category: Male
Sub-Category: Sports Shoes
Price: ₹8,995
Discount: ₹7,499
Emoji: 👟
Gradient: Blue (BFDBFE → 93C5FD → A5F3FC)
Sizes: 8, 9, 10, 11
Location: Shelf 1, Row 4
Box: #543210
Code: 9832
Description: Premium quality sports shoes by Nike. 
Designed for male customers with focus on comfort, 
durability, and style. Features high-quality materials, 
cushioned insoles, and modern design. Perfect for daily 
wear, sports activities, or special occasions. Available 
in multiple sizes with proper arch support and breathable 
construction.
```

### Example 2: Stiletto Heels
```
Name: Stiletto Heels | Party Wear | Metro
Category: Female
Sub-Category: Heels
Price: ₹4,299
Discount: ₹3,499
Emoji: 👠
Gradient: Red-Pink (FCA5A5 → F9A8D4 → FBBF24)
Sizes: 5, 6, 7, 8
Location: Shelf 1, Row 1
Box: #789456
Code: 9832
Description: Premium quality heels by Metro. Designed 
for female customers with focus on comfort, durability, 
and style. Features high-quality materials, cushioned 
insoles, and modern design. Perfect for daily wear, 
sports activities, or special occasions. Available in 
multiple sizes with proper arch support and breathable 
construction.
```

### Example 3: Puma Kids Sneakers
```
Name: Puma Kids | Junior Sneakers | Puma
Category: Kids
Sub-Category: Sneakers
Price: ₹3,499
Discount: ₹2,799
Emoji: 👟
Gradient: Yellow (FEF08A → FDBA74 → FEF08A)
Sizes: 1, 2, 3, 4, 5
Location: Shelf 3, Row 1
Box: #234567
Code: 9832
Description: Premium quality sneakers by Puma. Designed 
for kids customers with focus on comfort, durability, 
and style. Features high-quality materials, cushioned 
insoles, and modern design. Perfect for daily wear, 
sports activities, or special occasions. Available in 
multiple sizes with proper arch support and breathable 
construction.
```

## 🎯 Key UI Features

### 1. Name Display
- Shows FULL name (all 3 parts)
- Example: "Nike Air Max | Running Shoes | Nike"
- On product card: Shows only first part "Nike Air Max"
- On detail screen: Shows full name

### 2. Category Badges
- Two small pills side-by-side
- First badge: Main category (Male/Female/Kids) - Indigo
- Second badge: Sub-category (Sports Shoes/Heels/etc) - Orange

### 3. Price Display
- Discount price: Large (28sp), green, bold
- Original price: Smaller (16sp), gray, strikethrough
- Placed side-by-side

### 4. Product Info Section
- Box Number with # prefix
- Code: Always 9832
- Location: "Shelf X, Row Y" format
- All in gray, 12sp

### 5. Description
- Section header: "Description" (16sp, bold)
- Text: Detailed multi-line description
- Gray color (#4B5563)
- 12sp with line spacing

### 6. Size Selection UI
- Gray-50 rounded background container
- Header: "Select Size" (14sp, bold)
- Grid: 5 columns, equal width
- Buttons with borders and padding

### 7. Available Sizes Display
- Separate section below size selection
- Shows all available sizes as chips
- Read-only (not clickable)
- White background chips with gray borders

## 💡 Implementation Tips

1. **Product Name Parsing**:
   ```java
   String fullName = product.getName();
   String displayName = fullName.split("\\|")[0].trim(); // For card
   String brand = fullName.split("\\|")[2].trim(); // For description
   ```

2. **Gradient Application**:
   ```java
   GradientDrawable gradient = new GradientDrawable();
   gradient.setColors(getGradientColors(product.getColor()));
   gradient.setOrientation(GradientDrawable.Orientation.BR_TL);
   view.setBackground(gradient);
   ```

3. **Size Grid Layout**:
   ```java
   GridLayout sizeGrid = findViewById(R.id.sizeGrid);
   sizeGrid.setColumnCount(5);
   for (Integer size : product.getSizes()) {
       Button btn = createSizeButton(size);
       sizeGrid.addView(btn);
   }
   ```

4. **Button State Management**:
   ```java
   if (selectedSize != -1) {
       addToCartButton.setEnabled(true);
       addToCartButton.setBackgroundResource(R.drawable.orange_button_bg);
       addToCartButton.setText("Add to Cart - Size " + selectedSize);
   } else {
       addToCartButton.setEnabled(false);
       addToCartButton.setBackgroundResource(R.drawable.gray_button_bg);
       addToCartButton.setText("Select a Size");
   }
   ```

## ✅ Checklist for Perfect UI Match

- [ ] Large emoji (180sp) centered on gradient
- [ ] Golden ornaments at top corners
- [ ] Category badge with icon at top-left
- [ ] Close button at top-right (white circle)
- [ ] 3 thumbnails horizontally scrollable
- [ ] Full product name displayed
- [ ] Two category badges (indigo + orange)
- [ ] Green discount price, strikethrough original
- [ ] Box number, code, location info
- [ ] "Description" header with detailed text
- [ ] Size selection in 5-column grid
- [ ] Selected size: orange border + orange-50 bg
- [ ] Available sizes chips (read-only)
- [ ] Add to Cart button states (orange/gray)
- [ ] Button text changes with size selection

Your Android app now has the **exact same UI** as the React version! 🎉
