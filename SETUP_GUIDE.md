# Complete Setup Guide for Shoe Store Android App

## Prerequisites

### Required Software
1. **Android Studio** (Latest version recommended - Hedgehog or newer)
   - Download from: https://developer.android.com/studio
   - Minimum version: Arctic Fox or later

2. **JDK (Java Development Kit)**
   - JDK 8 or higher
   - Usually bundled with Android Studio

3. **Android SDK**
   - API Level 24 (Android 7.0) minimum
   - API Level 34 (Android 14) target
   - Installed via Android Studio SDK Manager

## Step-by-Step Setup

### 1. Install Android Studio

1. Download Android Studio from the official website
2. Run the installer
3. Follow the setup wizard
4. Install Android SDK when prompted
5. Wait for all components to download

### 2. Open the Project

1. Launch Android Studio
2. Click **"Open"** or **File → Open**
3. Navigate to the `ShoeStoreApp` folder
4. Click **OK**
5. Wait for Android Studio to load the project

### 3. Sync Gradle

1. Android Studio will automatically detect the project
2. A banner will appear: **"Gradle files have changed"**
3. Click **"Sync Now"**
4. Wait for Gradle sync to complete (may take 5-10 minutes first time)
5. Check the **Build** tab at the bottom for any errors

### 4. Configure SDK

If you see SDK-related errors:

1. Go to **File → Project Structure**
2. Under **Project**, ensure:
   - **SDK**: Android API 34
   - **Language Level**: 8 - Lambdas, type annotations, etc.
3. Under **Modules → app**, ensure:
   - **Compile SDK Version**: 34
   - **Min SDK Version**: 24
4. Click **Apply** → **OK**

### 5. Setup Emulator (If you don't have a physical device)

1. Go to **Tools → Device Manager**
2. Click **Create Device**
3. Choose a device (recommended: Pixel 5)
4. Select a system image:
   - Recommended: **Android 13 (API 33)** or **Android 14 (API 34)**
   - Download if not already installed
5. Click **Next** → **Finish**
6. Wait for the emulator to download and configure

### 6. Run the App

#### Option A: Using Emulator
1. Start the emulator from Device Manager
2. Wait for it to fully boot (shows home screen)
3. Click the green **Run** button (▶) in the toolbar
4. Select your emulator from the list
5. Click **OK**

#### Option B: Using Physical Device
1. Enable **Developer Options** on your Android device:
   - Go to **Settings → About Phone**
   - Tap **Build Number** 7 times
2. Enable **USB Debugging**:
   - Go to **Settings → Developer Options**
   - Turn on **USB Debugging**
3. Connect device via USB
4. Allow USB debugging when prompted
5. Click the green **Run** button (▶)
6. Select your device from the list
7. Click **OK**

### 7. First Launch

On first launch, the app will:
1. Create the local database
2. Seed 24 sample products automatically
3. Display the main product grid

## Troubleshooting

### Common Issues and Solutions

#### 1. Gradle Sync Failed

**Error**: "Failed to sync Gradle"

**Solutions**:
- Check your internet connection
- Go to **File → Invalidate Caches → Invalidate and Restart**
- Delete `.gradle` folder in the project directory
- Sync again

#### 2. SDK Not Found

**Error**: "SDK location not found"

**Solution**:
1. Go to **File → Project Structure → SDK Location**
2. Set Android SDK location (usually `C:\Users\YourName\AppData\Local\Android\Sdk` on Windows)
3. Click **Apply**

#### 3. Build Failed

**Error**: Various build errors

**Solutions**:
- Clean project: **Build → Clean Project**
- Rebuild: **Build → Rebuild Project**
- Check **Build** tab for specific error messages

#### 4. App Crashes on Launch

**Possible causes**:
- Emulator/device API level too low (needs API 24+)
- Insufficient device storage
- Conflicting apps

**Solutions**:
- Check Logcat for error messages
- Try a different emulator/device
- Clear app data and reinstall

#### 5. Database Errors

**Error**: Database-related crashes

**Solutions**:
- Clear app data and reinstall
- Check Logcat for specific SQL errors
- Verify Gson dependency is included

## Project Structure Overview

```
ShoeStoreApp/
├── app/
│   ├── src/main/
│   │   ├── java/com/shoesstore/app/    # Java source files
│   │   │   ├── MainActivity.java
│   │   │   ├── ProductDetailActivity.java
│   │   │   ├── CheckoutActivity.java
│   │   │   ├── AddProductActivity.java
│   │   │   ├── adapters/               # RecyclerView adapters
│   │   │   ├── models/                 # Data models
│   │   │   ├── database/               # Room database
│   │   │   └── utils/                  # Utility classes
│   │   │
│   │   ├── res/
│   │   │   ├── layout/                 # XML layouts
│   │   │   ├── drawable/               # Icons and backgrounds
│   │   │   ├── values/                 # Colors, strings, themes
│   │   │   └── mipmap/                 # App icons
│   │   │
│   │   └── AndroidManifest.xml         # App configuration
│   │
│   └── build.gradle                    # App-level dependencies
│
├── build.gradle                        # Project-level configuration
├── settings.gradle                     # Project settings
└── gradle.properties                   # Gradle properties
```

## Building APK for Distribution

### Debug APK (For Testing)

1. Go to **Build → Build Bundle(s) / APK(s) → Build APK(s)**
2. Wait for build to complete
3. Click **locate** in the notification
4. APK will be in: `app/build/outputs/apk/debug/`
5. Transfer to device and install

### Release APK (For Production)

1. Create a keystore (first time only):
   ```
   keytool -genkey -v -keystore my-release-key.keystore 
   -alias my-key-alias -keyalg RSA -keysize 2048 -validity 10000
   ```

2. Add to `app/build.gradle`:
   ```gradle
   android {
       signingConfigs {
           release {
               storeFile file('my-release-key.keystore')
               storePassword 'your-password'
               keyAlias 'my-key-alias'
               keyPassword 'your-password'
           }
       }
       buildTypes {
           release {
               signingConfig signingConfigs.release
           }
       }
   }
   ```

3. Build: **Build → Build Bundle(s) / APK(s) → Build APK(s)**
4. APK in: `app/build/outputs/apk/release/`

## Testing the App

### Manual Testing Checklist

#### Main Screen
- [ ] Products display in 2-column grid
- [ ] Search bar works for product names
- [ ] Cart badge shows item count
- [ ] Filter button shows active filter count

#### Menu Drawer
- [ ] Opens from left
- [ ] "Add Products" navigates correctly
- [ ] "Edit Products" navigates correctly

#### Filter Drawer
- [ ] Opens from right
- [ ] Category checkboxes work
- [ ] Sub-categories update based on main category
- [ ] Size selection works
- [ ] Apply button filters products

#### Cart Drawer
- [ ] Opens from right
- [ ] Shows added items
- [ ] Remove button works
- [ ] Total calculates correctly
- [ ] Checkout button navigates

#### Product Detail
- [ ] Shows product information
- [ ] Size selection works
- [ ] Add to cart button enables after size selection
- [ ] Thumbnail navigation works
- [ ] Close button returns to main

#### Checkout
- [ ] Shows cart items
- [ ] Payment amount validation works
- [ ] Input changes color (green/red)
- [ ] Minimum price displays when checked
- [ ] Sold button processes sale

#### Add Product
- [ ] All fields accept input
- [ ] Size selection works
- [ ] Save creates new product
- [ ] Product appears in main grid

### Performance Testing

1. Scroll through product grid (should be smooth)
2. Open/close drawers rapidly (no lag)
3. Search with many results (instant filtering)
4. Add many items to cart (no slowdown)

## Customization Guide

### Changing Colors

Edit `app/src/main/res/values/colors.xml`:
```xml
<color name="primary_orange">#F97316</color>
<color name="slate_800">#1E293B</color>
```

### Changing Strings

Edit `app/src/main/res/values/strings.xml`:
```xml
<string name="app_name">Your Store Name</string>
```

### Adding More Sample Products

Edit `app/src/main/java/com/shoesstore/app/utils/DataSeeder.java`:
- Add more products to the `products` list
- Follow the same pattern

### Changing Gradient Colors

Edit `ProductAdapter.java` and `ProductDetailActivity.java`:
- Add new gradient color cases in `getGradientColors()` method

## Database Management

### View Database Contents

Using Android Studio Database Inspector:
1. Run app on emulator/device
2. Go to **View → Tool Windows → App Inspection**
3. Select **Database Inspector** tab
4. Expand **shoe_store.db**
5. Click **products** table to view data

Or using ADB:
```bash
adb shell
cd /data/data/com.shoesstore.app/databases/
sqlite3 shoe_store.db
.tables
SELECT * FROM products;
```

### Reset Database

To clear all data:
1. Go to device **Settings → Apps → Shoe Store**
2. Click **Storage**
3. Click **Clear Data**
4. Relaunch app (will reseed sample data)

Or via ADB:
```bash
adb shell pm clear com.shoesstore.app
```

## Performance Optimization Tips

1. **Images**: Currently using emojis (lightweight)
2. **Database**: Native SQLite with efficient queries
3. **RecyclerView**: Implements view recycling
4. **Filtering**: In-memory filtering (fast)
5. **JSON**: Gson for efficient List serialization

## Support & Resources

### Official Documentation
- Android Developers: https://developer.android.com
- SQLite Database: https://developer.android.com/training/data-storage/sqlite
- Material Design: https://material.io/develop/android
- Gson Library: https://github.com/google/gson

### Common ADB Commands
```bash
# Install APK
adb install app-debug.apk

# Uninstall app
adb uninstall com.shoesstore.app

# View logs
adb logcat

# Clear app data
adb shell pm clear com.shoesstore.app
```

## Next Steps

After successful setup:

1. **Explore the app** - Add products, search, filter, checkout
2. **Customize** - Change colors, strings, add features
3. **Test thoroughly** - Try all features on different devices
4. **Build release APK** - For distribution
5. **Deploy** - Share with users or publish to Play Store

## Need Help?

Check these in order:
1. **Build tab** - Shows specific error messages
2. **Logcat** - Runtime errors and crashes
3. **Stack Overflow** - Search for specific errors
4. **Android Documentation** - Official guides

---

**Congratulations!** You now have a fully functional offline Android shoe store management app! 🎉
