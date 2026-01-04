# Changelog

All notable changes to the Shoe Store Management App will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added
- Initial release

## [1.0.0] - 2026-01-04

### Added
- Complete offline shoe store management system
- 36 pre-loaded sample products
- Native SQLite database with Gson serialization
- Product browsing with 2-column grid layout
- Real-time search across multiple fields (name, category, size, location, box number, code)
- Advanced filter system (main category, sub-category, sizes)
- Shopping cart with size-specific items
- Checkout system with minimum price validation
- Add new products functionality
- Edit products functionality (selection UI)
- Product detail screen with:
  - Large emoji display on gradient backgrounds
  - Decorative elements (lights, golden ornaments)
  - Size selection grid
  - Available sizes display
  - Full product information
- Beautiful gradient backgrounds (23 unique gradients)
- Category badges with emoji icons
- Left menu drawer for admin functions
- Right filter drawer with checkboxes
- Right cart drawer with item management
- Color-coded input validation (green/red)
- Badge notifications for cart count and filter count

### Features
- **Male Products**: 15 items (Sports Shoes, Running Shoes, Casual Slippers, Formal Shoes, Boots)
- **Female Products**: 12 items (Running Shoes, Casual Shoes, Sandals, Heels)
- **Kids Products**: 9 items (Sneakers, Sandals, School Shoes)
- **Search**: Product name, category, sub-category, sizes, location, box number, code
- **Filters**: Main category, sub-category, sizes (1-13)
- **Cart**: Multiple items, size-specific tracking, real-time total
- **Checkout**: Payment validation, minimum price display, sale processing
- **Database**: SQLite with JSON serialization for Lists
- **UI**: Material Design with custom gradients and decorations

### Technical
- Language: Java
- Min SDK: Android 7.0 (API 24)
- Target SDK: Android 14 (API 34)
- Database: SQLite with SQLiteOpenHelper
- JSON: Gson 2.10.1
- Architecture: Activity-based with RecyclerView adapters
- Singleton pattern for database and cart management

### Known Issues
- Edit product functionality shows selection UI only (form pending)
- Image gallery fullscreen view not implemented
- Quantity adjustment in cart not implemented (fixed at 1)
- Database backup/restore menu items are placeholders

## Version History

### v1.0.0 (2026-01-04)
- Initial release with full functionality
- 36 products across all categories
- Complete UI matching React version
- Offline-first with SQLite database

---

**Note**: For detailed feature documentation, see [FEATURES.md](FEATURES.md)
