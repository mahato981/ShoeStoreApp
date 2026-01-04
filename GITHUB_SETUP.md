# GitHub Setup Guide

## 📦 Automatic APK Build & Release System

This repository is configured with GitHub Actions to automatically build APK files on every push and create releases with downloadable APKs.

## 🚀 Quick Setup

### 1. Create GitHub Repository

```bash
# Initialize git in your project
cd ShoeStoreApp
git init

# Add all files
git add .

# Commit
git commit -m "Initial commit: Shoe Store Management App"

# Create repository on GitHub, then:
git remote add origin https://github.com/yourusername/ShoeStoreApp.git
git branch -M main
git push -u origin main
```

### 2. Automatic Workflows

Two GitHub Actions workflows are configured:

#### **android-build.yml** - Continuous Integration
- **Triggers**: Push to main/master, Pull requests
- **Actions**: 
  - Builds debug APK
  - Builds release APK (unsigned)
  - Uploads as artifacts
- **Artifacts available for 90 days**

#### **release-apk.yml** - Release Creation
- **Triggers**: When you push a version tag (e.g., v1.0.0)
- **Actions**:
  - Builds APK
  - Creates GitHub Release
  - Attaches APK to release
  - Auto-generates release notes

### 3. Creating a Release

```bash
# Tag your version
git tag -a v1.0.0 -m "Release version 1.0.0"

# Push tag to GitHub
git push origin v1.0.0
```

This automatically:
1. Triggers the release workflow
2. Builds the APK
3. Creates a GitHub Release
4. Uploads APK as release asset
5. Makes it available for download

### 4. Download APK

After the workflow completes:

**From Actions (Every Commit)**:
1. Go to **Actions** tab
2. Click on latest workflow run
3. Scroll to **Artifacts**
4. Download `app-debug` or `app-release`

**From Releases (Tagged Versions)**:
1. Go to **Releases** tab
2. Click on latest release
3. Download APK from **Assets** section
4. Install on Android device

## 📋 Workflow Details

### Build Workflow (android-build.yml)

```yaml
Triggers:
- Push to main/master
- Pull requests
- Manual dispatch

Jobs:
1. Checkout code (actions/checkout@v4)
2. Setup JDK 17 (actions/setup-java@v4)
3. Grant execute permission to gradlew
4. Build with Gradle
5. Build Debug APK
6. Upload Debug APK artifact
7. Build Release APK (unsigned)
8. Upload Release APK artifact

Artifacts:
- app-debug.apk
- app-release-unsigned.apk
```

### Release Workflow (release-apk.yml)

```yaml
Triggers:
- Push tag starting with 'v' (v1.0.0, v2.0.1, etc.)
- Manual dispatch

Jobs:
1. Checkout code
2. Setup JDK 17
3. Extract version from tag
4. Build Debug APK
5. Rename APK with version
6. Create GitHub Release with notes
7. Upload APK to release

Release Name: ShoeStore-v1.0.0-debug.apk
```

## 🔧 Configuration

### Gradle Wrapper

Included files:
- `gradlew` (Linux/Mac executable)
- `gradlew.bat` (Windows executable)
- `gradle/wrapper/gradle-wrapper.jar`
- `gradle/wrapper/gradle-wrapper.properties`

These allow GitHub Actions to build without pre-installed Gradle.

### .gitignore

Excludes:
- Build outputs
- IDE files (.idea, *.iml)
- Local configuration
- Compiled files
- Log files

### .gitattributes

Handles:
- Line endings (CRLF/LF)
- Binary files
- Text file normalization

## 📱 Using the APK

### Installation Steps

1. **Download APK** from GitHub Releases or Actions artifacts
2. **Transfer to Android device** (via USB, email, or cloud)
3. **Enable Unknown Sources**:
   - Settings → Security → Unknown Sources
   - Or: Settings → Apps → Special Access → Install Unknown Apps
4. **Install APK**: Tap the file and follow prompts
5. **Launch app** from app drawer

### APK Types

**Debug APK**:
- Easier to build and test
- Larger file size
- Not optimized
- Good for testing

**Release APK** (Unsigned):
- Smaller, optimized
- Requires signing for Play Store
- Better performance

## 🔐 Signing APK (Optional)

For production/Play Store release:

### Create Keystore

```bash
keytool -genkey -v -keystore release-key.keystore \
  -alias app-key -keyalg RSA -keysize 2048 -validity 10000
```

### Add to GitHub Secrets

1. Go to **Settings → Secrets and variables → Actions**
2. Add secrets:
   - `KEYSTORE_FILE` (base64 encoded keystore)
   - `KEYSTORE_PASSWORD`
   - `KEY_ALIAS`
   - `KEY_PASSWORD`

### Update Workflow

Add signing step to `release-apk.yml`:

```yaml
- name: Sign APK
  uses: r0adkll/sign-android-release@v1
  with:
    releaseDirectory: app/build/outputs/apk/release
    signingKeyBase64: ${{ secrets.KEYSTORE_FILE }}
    alias: ${{ secrets.KEY_ALIAS }}
    keyStorePassword: ${{ secrets.KEYSTORE_PASSWORD }}
    keyPassword: ${{ secrets.KEY_PASSWORD }}
```

## 📊 Workflow Status

Add badge to README.md:

```markdown
![Android CI](https://github.com/yourusername/ShoeStoreApp/workflows/Android%20CI%20-%20Build%20APK/badge.svg)
```

## 🐛 Troubleshooting

### Build Fails

**Issue**: Gradle build fails
**Solution**: 
- Check Java version (requires JDK 17)
- Verify gradle files are committed
- Check build.gradle syntax

### Permission Denied

**Issue**: `./gradlew: Permission denied`
**Solution**: Already handled by workflow with `chmod +x gradlew`

### APK Not Found

**Issue**: Upload artifact fails
**Solution**: 
- Check APK path in workflow
- Verify build completed successfully
- Check gradle build outputs

### Release Not Created

**Issue**: Tag pushed but no release
**Solution**:
- Tag must start with 'v' (v1.0.0)
- Check workflow logs in Actions tab
- Verify GITHUB_TOKEN permissions

## 📈 Version Management

### Semantic Versioning

Follow [SemVer](https://semver.org/):
- **v1.0.0**: Major.Minor.Patch
- **v1.1.0**: New features (backwards compatible)
- **v1.0.1**: Bug fixes
- **v2.0.0**: Breaking changes

### Example Release Process

```bash
# Make changes
git add .
git commit -m "feat: Add barcode scanning"

# Push to main
git push origin main

# Create release
git tag -a v1.1.0 -m "Add barcode scanning feature"
git push origin v1.1.0

# GitHub Actions automatically:
# 1. Builds APK
# 2. Creates release
# 3. Uploads APK
```

## 🎯 Best Practices

1. **Test locally** before pushing
2. **Use meaningful commit messages**
3. **Tag releases** with semantic versions
4. **Update CHANGELOG.md** with each release
5. **Test APK** on real device after building
6. **Keep dependencies updated**
7. **Review workflow logs** regularly

## 📚 Additional Resources

- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [Android Build Setup](https://developer.android.com/studio/build)
- [Semantic Versioning](https://semver.org/)
- [Keep a Changelog](https://keepachangelog.com/)

---

Your app is now configured for automatic APK generation! 🎉
