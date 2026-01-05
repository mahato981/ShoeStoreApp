#!/bin/bash

echo "🔧 Fixing Git Repository..."
echo ""

# Show current status
echo "📊 Current status:"
git status --short | wc -l
echo "files not tracked"
echo ""

# Add all untracked files
echo "➕ Adding all files..."
git add .

# Show what will be committed
echo ""
echo "📝 Files to be committed:"
git status --short | head -20
echo ""

# Commit
echo "💾 Committing changes..."
git commit -m "Fix all lint errors: WrongViewCast and WrongConstant issues in MainActivity and EditProductsActivity"

# Push
echo ""
echo "🚀 Pushing to GitHub..."
git push origin main

echo ""
echo "✅ Done! Check GitHub Actions tab now."
echo ""
echo "🌐 Go to: https://github.com/mahato981/ShoeStoreApp/actions"

