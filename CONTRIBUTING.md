# Contributing to Shoe Store App

Thank you for considering contributing to the Shoe Store Management App! 

## How to Contribute

### Reporting Bugs

1. Check if the bug has already been reported in [Issues](https://github.com/yourusername/ShoeStoreApp/issues)
2. If not, create a new issue with:
   - Clear title and description
   - Steps to reproduce
   - Expected vs actual behavior
   - Screenshots if applicable
   - Device info (Android version, device model)

### Suggesting Features

1. Check existing issues and pull requests first
2. Create a new issue with:
   - Clear feature description
   - Use cases and benefits
   - Possible implementation approach

### Pull Requests

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/your-feature-name`
3. Make your changes
4. Test thoroughly on different Android versions
5. Commit with clear messages: `git commit -m "Add feature: description"`
6. Push to your fork: `git push origin feature/your-feature-name`
7. Create a Pull Request

### Code Style

- Follow Java naming conventions
- Use meaningful variable names
- Comment complex logic
- Keep methods focused and small
- Format code consistently

### Testing

- Test on Android 7.0+ devices
- Test search and filter functionality
- Test cart operations
- Test database operations
- Test on different screen sizes

### Commit Messages

Follow this format:
```
Type: Brief description

Detailed description if needed

Fixes #issue-number (if applicable)
```

Types:
- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation changes
- `style`: Code style changes (formatting)
- `refactor`: Code refactoring
- `test`: Adding tests
- `chore`: Maintenance tasks

### Building the Project

```bash
# Clone repository
git clone https://github.com/yourusername/ShoeStoreApp.git
cd ShoeStoreApp

# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Run tests
./gradlew test
```

## Need Help?

- Read the [README.md](README.md) for setup instructions
- Check [SETUP_GUIDE.md](SETUP_GUIDE.md) for detailed setup
- Ask questions in GitHub Discussions

Thank you for contributing! 🎉
